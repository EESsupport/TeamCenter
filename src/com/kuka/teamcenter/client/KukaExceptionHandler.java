package com.kuka.teamcenter.client;

import java.io.IOException;

import com.teamcenter.schemas.soa._2006_03.exceptions.ConnectionException;
import com.teamcenter.schemas.soa._2006_03.exceptions.InternalServerException;
import com.teamcenter.schemas.soa._2006_03.exceptions.ProtocolException;
import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.soa.client.ExceptionHandler;
import com.teamcenter.soa.exceptions.CanceledOperationException;
public class KukaExceptionHandler
        implements ExceptionHandler{

    private Session.OnSessionInfoChangedListener mListener;
    public KukaExceptionHandler(Session.OnSessionInfoChangedListener listener){
        mListener=listener;
    }


    public void handleException(InternalServerException ise){

        if (ise instanceof InternalServerException){
            // ConnectionException are typically due to a network error (server
            // down .etc) and can be recovered from (the last request can be sent again,
            // after the problem is corrected).
            Object msg="\nThe server returned an connection error.\n" + ise.getMessage()
                    + "\nDo you wish to retry the last service request?[y/n]";
            print(msg);
        }else if(ise instanceof ConnectionException){
            // ConnectionException are typically due to a network error (server
            // down .etc) and can be recovered from (the last request can be sent again,
            // after the problem is corrected).
            Object msg="\nThe server returned an connection error.\n" + ise.getMessage()
                    + "\nDo you wish to retry the last service request?[y/n]";
            print(msg);
        }else if (ise instanceof ProtocolException){
            // ProtocolException are typically due to programming errors
            // (content of HTTP
            // request is incorrect). These are generally can not be
            // recovered from.
            Object msg="\nThe server returned an protocol error.\n" + ise.getMessage()
                    + "\nThis is most likely the result of a programming error."
                    + "\nDo you wish to retry the last service request?[y/n]";
            print(msg);
        }else{
            Object msg="\nThe server returned an internal server error.\n"
                    + ise.getMessage()
                    + "\nThis is most likely the result of a programming error."
                    + "\nA RuntimeException will be thrown.";
            print(msg);
            throw new RuntimeException(ise.getMessage());
        }

        try{
            String retry="y";
            // If yes, return to the calling SOA client framework, where the
            // last service request will be resent.
            if (retry.toLowerCase().equals("y") || retry.toLowerCase().equals("yes"))
                return;
            throw new ServiceException("The user has opted not to retry the last request");
        }catch(IOException e){
            print("Failed to read user response.\nA RuntimeException will be thrown.");
            throw new RuntimeException(e.getMessage());
        }

    }

    private void print(Object message){
        mListener.OnInfoChanged("KukaExceptionHandler",message);
    }

    public void handleException(CanceledOperationException coe) {

        print("");
        print("*****");
        print("Exception caught in com.teamcenter.clientx.AppXExceptionHandler.handleException(CanceledOperationException).");

        // Expecting this from the login tests with bad credentials, and the
        // AnyUserCredentials class not
        // prompting for different credentials
        throw new RuntimeException(coe.getMessage());
    }

}