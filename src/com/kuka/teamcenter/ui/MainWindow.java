package com.kuka.teamcenter.ui;

import com.kuka.teamcenter.actions.CreateRelations;
import com.kuka.teamcenter.client.ILoginInfo;
import com.kuka.teamcenter.client.LoginInfo;
import com.kuka.teamcenter.client.Session;
import com.kuka.teamcenter.model.ExportFactory;
import com.kuka.teamcenter.queries.Query;
import com.kuka.teamcenter.queries.SavedQuery;
import com.kuka.teamcenter.soa.DataManagement;
import com.teamcenter.soa.client.model.ModelObject;
import org.flexdock.docking.Dockable;
import org.flexdock.util.SwingUtility;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by cberman on 12/2/2015.
 */
public class MainWindow
    implements ActionListener,

    Session.OnSessionInfoChangedListener{
    private static Session session;
    private static LoginInfo loginInfo;
    private static final String DELETE="Delete";
    private static final String LOGIN="Log in";
    private static final String LOGOUT="Log out";
    private static final String LOAD="Load";
    public static final String TEST="Test";


    public static boolean RIGHT_TO_LEFT = false;
    private JButton deleteButton;
    private JButton loadButton;
    private JButton loginButton;
    private JButton logoutButton;
    private Button testButton;
    private ItemWindow itemWindow;
    private JFrame frame;


    MessageTable messageTable = new MessageTable();
    public MainWindow(){
        // Create Frame
        frame = new JFrame("Main Window");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = frame.getContentPane();

//        c.add(menu);

        addComponentsToPane(c);

//        frame.pack();

      ;
        SwingUtility.centerOnScreen(frame);
        frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

    }


    private  void setupButton(JButton button,int mnemonic){
        button.setMnemonic(mnemonic);
        button.addActionListener(this);
    }

     public  void addComponentsToPane(Container pane){

        MyDockingPort dockingPort = new MyDockingPort();


        if (!(pane.getLayout() instanceof BorderLayout)) {
            pane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }

        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(
                    java.awt.ComponentOrientation.RIGHT_TO_LEFT);
        }
        JPanel buttonPanel = new JPanel(new FlowLayout(),false);


        /**
         * Delete button
         */
        deleteButton = new JButton(DELETE);
        deleteButton.setEnabled(false);
        setupButton(deleteButton,KeyEvent.VK_D);
        buttonPanel.add(deleteButton);

        /**
         * Login Button
         */
        loginButton = new JButton(LOGIN);
        setupButton(loginButton,KeyEvent.VK_I);
        buttonPanel.add(loginButton);
        /**
         * Logout button
         */
        logoutButton = new JButton(LOGOUT);
        setupButton(logoutButton,KeyEvent.VK_O);
        logoutButton.setEnabled(false);
        buttonPanel.add(logoutButton);
        /**
         * Load Button
         */

        loadButton = new JButton(LOAD);
        setupButton(loadButton,KeyEvent.VK_L);
        loadButton.setEnabled(false);
        buttonPanel.add(loadButton);


        /**
         * Test button
         */
        testButton = new Button(TEST,this,KeyEvent.VK_T,false);

        buttonPanel.add(testButton);

        pane.add(buttonPanel, BorderLayout.PAGE_START);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.PAGE_AXIS));
        // Add Messages Window
//        centerPanel.add(debugWindow);
        centerPanel.add(new JScrollPane(messageTable));
        itemWindow = new ItemWindow();
        centerPanel.add(itemWindow);
        pane.add(centerPanel,BorderLayout.CENTER);


        pane.add(new StatusBar(),BorderLayout.PAGE_END);



    }

    private void getItems(){




        SwingWorker worker = new SwingWorker<List<ModelObject>,Void>(){

            @Override
            protected List<ModelObject> doInBackground() throws Exception {
                SavedQuery query = new SavedQuery();
                String[] names = {"Owning User"};
                String[] values = {"Berman, Charles (cberman)"};
                String name = "General...";
                String[] uids = query.setQuery(name, names, values);

                Query q = new Query();
                q.queryItems();
                firePropertyChange("delete","","There are " + uids.length + " uids");

                DataManagement dm = new DataManagement((sender, message) -> firePropertyChange(sender,"",message));
                return dm.loadItems(uids);

            }

            @Override
            protected void done() {
                deleteButton.setEnabled(true);
            }
        };
        worker.addPropertyChangeListener(evt -> {
            String name=evt.getPropertyName();
            switch(name){
                case "Loaded":

                    itemWindow.addItems((ArrayList)evt.getNewValue());
                    break;
                default:
                    addMessage(name,evt.getNewValue());
                    break;
            }

        });
//        deleteButton.setEnabled(false);
        worker.execute();

    }


    private void delete(){
        SwingWorker worker = new SwingWorker<Hashtable<String,Integer>,Void>(){

            @Override
            protected Hashtable<String, Integer> doInBackground() throws Exception {
                SavedQuery query = new SavedQuery();
                String[] names = {"Owning User"};
                String[] values = {"Berman, Charles (cberman)"};
                String name = "General...";

                String[] uids = query.setQuery(name, names, values);

                Query q = new Query();
                q.queryItems();
                firePropertyChange("delete","","There are " + uids.length + " uids");
                DataManagement dm = new DataManagement((sender, message) -> firePropertyChange(sender,"",message));
                return dm.deleteItems(uids);
            }

            @Override
            protected void done() {
                deleteButton.setEnabled(true);
            }
        };
        worker.addPropertyChangeListener(evt -> addMessage(evt.getPropertyName(),evt.getNewValue()));
//        deleteButton.setEnabled(false);
        worker.execute();

    }

    private void login(){
        ProgressDialog pd = new ProgressDialog(frame,"Logging in","");
        pd.setIsCircular(true);
        pd.showDialog();


        loginButton.setEnabled(false);
        SwingWorker worker = new SwingWorker<Void,Void>(){
            @Override
            protected void done() {
                updateLogButtons(session.getIsConnected());
                pd.setVisible(false);
                pd.dispose();
            }

            @Override
            protected Void doInBackground() throws Exception {
                firePropertyChange("login","","Logging in.");

                if (loginInfo==null){
                    loginInfo = new LoginInfo();
                    ILoginInfo l = loginInfo;

                }
                session = new Session(loginInfo, (sender, message) -> {
                    firePropertyChange(sender,"",message);
                });

                session.login();

                return null;
            }
        };
        worker.addPropertyChangeListener(evt -> {
            addMessage(evt.getPropertyName(),evt.getNewValue());
        });
        worker.execute();


    }

    private void addMessage(String sender,Object message){
        messageTable.writeText(sender,message);

    }
    private void updateLogButtons(boolean connected){
        loginButton.setEnabled(!connected);
        logoutButton.setEnabled(connected);
        testButton.setEnabled(connected);
        loadButton.setEnabled(connected);
        deleteButton.setEnabled(connected);
    }
    private void logout(){
        addMessage("MainWindow","Logging out.");
        session.logout();
        updateLogButtons(session.getIsConnected());

        addMessage("MainWindow","Logged out.");

    }



    private void test(){
        TestDialog dlg = new TestDialog(frame,"Testing","Testing");
        dlg.setVisible(true);
        getItems();
        dlg.setVisible(false);
/*
        CreateRelations struc = new CreateRelations(Session.getUser());
        if(struc.createParts())
            struc.createStructure();*/
    }

    private void load(){
        String filename="D:\\Ford 2017 P558 KTP\\Process\\P558.xml";
//        String filename="D:\\cutdata.xml";
        ExportFactory factory = new ExportFactory(filename);
        try {
            factory.parseData(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        JButton obj=(JButton)e.getSource();
        String text=obj.getText();
        switch(text){
            case DELETE:
                delete();
                break;
            case LOGIN:
                login();
                break;
            case LOGOUT:
                logout();
                break;
            case LOAD:
                load();
                break;
            case TEST:
                test();
                break;
        }
    }

    @Override
    public void OnInfoChanged(String sender, Object message) {
        addMessage(sender,message);
    }

}
