package com.kuka.teamcenter.client;

import com.kuka.teamcenter.soa.HomeFolder;
import com.kuka.teamcenter.util.ConnectionUtil;
import com.teamcenter.schemas.soa._2006_03.exceptions.InvalidCredentialsException;
import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.services.strong.core.DataManagementService;
import com.teamcenter.services.strong.core.SessionService;
import com.teamcenter.services.strong.core._2006_03.Session.LoginResponse;
import com.teamcenter.soa.SoaConstants;
import com.teamcenter.soa.client.Connection;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.ServiceData;
import com.teamcenter.soa.client.model.strong.User;
import com.teamcenter.soa.client.model.strong.WorkspaceObject;
import com.teamcenter.soa.exceptions.CanceledOperationException;
import com.teamcenter.soa.exceptions.NotLoadedException;

import java.util.Calendar;
import java.util.List;

public class Session {
    private static User user;

    /**
     * Single instance of the Connection object that is shared throughtout
     * the application. This Connection object is needed whenever a Service
     * stub is instantiated.
     */
    private static Connection connection;

    /**
     * Get the single Connection object for the application
     *
     * @return  connection
     */
    public static Connection getConnection(){
        return connection;
    }

    /**
     * The credentialManager is used both by the Session class and the Teamcenter
     * Services Framework to get user credentials.
     *
     */
    private static KukaCredentialManager credentialManager;


    public void getHomeFolder(){
        HomeFolder home = new HomeFolder();
        home.listHomeFolder(user);
    }

    private static Session instance;

    /**
     * Create an instance of the Session with a connection to the specified
     * server.
     *
     * Add implementations of the ExceptionHandler, PartialErrorListener,
     * ChangeListener, and DeleteListeners.
     * @param host Address of the host to connect to, http://serverName:port/tc
     */
    public Session(String host,Session.OnSessionInfoChangedListener listener){
        instance=this;
        mListener=listener;
        // Create an instance of the CredentialManager, this is used
        // by the SOA Framework to get the user's credentials when
        // challenged by the server (session timeout on the web tier).
        credentialManager = new KukaCredentialManager();
        initializeHost(host);
    }

    private void initializeHost(String host){
        String protocol = null;
        String envNameTccs = null;


        if ( host.startsWith("http") )
        {
            protocol   = SoaConstants.HTTP;
        }
        else if ( host.startsWith("tccs") )
        {
            protocol   = SoaConstants.TCCS;
            host = host.trim();
            int envNameStart = host.indexOf('/') + 2;
            envNameTccs = host.substring( envNameStart, host.length() );
            host = "";
        }
        else
        {
            protocol   = SoaConstants.IIOP;
        }


        // Create the Connection object, no contact is made with the server
        // until a service request is made
        connection = new Connection(host, credentialManager, SoaConstants.REST, protocol);




        if (protocol == SoaConstants.TCCS)
            connection.setOption(Connection.TCCS_ENV_NAME, envNameTccs);


        // Add an ExceptionHandler to the Connection, this will handle any
        // InternalServerException, communication errors, XML marshaling errors
        // .etc
        connection.setExceptionHandler(new KukaExceptionHandler(mListener));

        // While the above ExceptionHandler is required, all of the following
        // Listeners are optional. Client application can add as many or as few Listeners
        // of each type that they want.

        // Add a Partial Error Listener, this will be notified when ever a
        // a service returns partial errors.
        connection.getModelManager().addPartialErrorListener(new KukaPartialErrorListener(mListener));

        // Add a Change and Delete Listener, this will be notified when ever a
        // a service returns model objects that have been updated or deleted.
        connection.getModelManager().addModelEventListener(new KukaModelEventListener(mListener));

        // Add a Request Listener, this will be notified before and after each
        // service request is sent to the server.
        Connection.addRequestListener(new KukaRequestListener(mListener));
    }


    public boolean getIsConnected(){
        return user!=null;
    }



    public interface OnSessionInfoChangedListener{
        void OnInfoChanged(String sender,Object message);
    }

    private OnSessionInfoChangedListener mListener;



    public Session(ILoginInfo loginInfo,Session.OnSessionInfoChangedListener listener){

        mListener=listener;
        ILoginInfo loginInfo1 = loginInfo;
        // Create an instance of the CredentialManager, this is used
        // by the SOA Framework to get the user's credentials when
        // challanged by the server (sesioin timeout on the web tier).
        credentialManager = new KukaCredentialManager(loginInfo);
        initializeHost(loginInfo.getHost());
    }


    public Session(String host,String user,String password,String role,String group)
    {
        // Create an instance of the CredentialManager, this is used
        // by the SOA Framework to get the user's credentials when
        // challanged by the server (sesioin timeout on the web tier).
        credentialManager = new KukaCredentialManager(user,password,group,role);
        initializeHost(host);
    }


    public void login(){
        loginUser();
    }

    public void logout(){
        // Get the service stub
        SessionService sessionService=SessionService.getService(connection);
        try {
            ServiceData result=sessionService.logout();
            user=null;
            if (result.sizeOfPartialErrors()>0){
                fireListener("Session","There are problems");
            }
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static User getUser(){return user;}

    public void loginUser(){

        // Get the service stub
        SessionService sessionService=SessionService.getService(connection);
        try{
            /**
             * Prompt for credentials until they are right, or until user
             * cancels
             */
            ILoginInfo info = credentialManager.getLoginInfo();


            while(true){
                try{

                    // *****************************
                    // Execute the service operation
                    // *****************************
                    String name = info.getName();
                    String password=info.getPassword();
                    String group = info.getGroup();
                    String role = info.getRole();
                    String discriminator=info.getDiscriminator();
                    LoginResponse resp = sessionService.login(name,password,group,role,"",discriminator);

                    user = resp.user;
                    return;
                }catch(InvalidCredentialsException e){
                    // credentials = credentialManager.getCredentials();
                    credentialManager.getCredentials();
                }
            }
            // User cancelled the operation, don't need to tell him again
        }catch(Exception e){
            e.printStackTrace();
            fireListener("loginUser",e.getMessage());
        }

    }
    private void fireListener(String sender,Object message){
        if (mListener!=null){
            mListener.OnInfoChanged(sender,message);
        }
    }

    public static void writeLine(String sender,Object message){
        instance.fireListener(sender,message);
    }
    public static void writeLine(String line){
        instance.fireListener("Session",line);
    }

    /**
     * Print some basic information for a list of objects
     * @param objects
     */



}
