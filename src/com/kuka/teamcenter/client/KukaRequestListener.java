package com.kuka.teamcenter.client;

import com.teamcenter.soa.client.RequestListener;

/// <summary>
/// This implementation of the RequestListener, logs each service request
/// to the console.
/// </summary>
public class KukaRequestListener implements RequestListener
{


    private Session.OnSessionInfoChangedListener mListener;
    public KukaRequestListener(Session.OnSessionInfoChangedListener listener){
        mListener=listener;
    }
    private void Log(String sender,Object message){
        mListener.OnInfoChanged(sender,message);
    }

    public void serviceRequest(Info info) {
        // will log the service name when done
        Log("serviceRequest","Request: "+info.id + ": " + info.service + "." + info.operation);
    }



    public void serviceResponse(Info info) {

        Log("serviceResponse","Response: "+info.id + ": " + info.service + "." + info.operation);
    }
}
