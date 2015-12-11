package com.kuka.teamcenter.client;

import com.teamcenter.soa.client.model.ModelEventListener;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.exceptions.NotLoadedException;
public class KukaModelEventListener extends ModelEventListener {


    private Session.OnSessionInfoChangedListener mListener;
    public KukaModelEventListener(Session.OnSessionInfoChangedListener listener){
        mListener=listener;
    }
    private void Log(String sender,Object message){
        mListener.OnInfoChanged(sender,message);
    }
    @Override
    public void localObjectChange(ModelObject[] objects){

        if (objects.length == 0) return;


        Log("localObjectChange","");
        Log("localObjectChange","Modified Objects handled in com.teamcenter.clientx.AppXUpdateObjectListener.modelObjectChange");
       Log("localObjectChange","The following objects have been updated in the client data model:");
        for (int i = 0; i < objects.length; i++)
        {
            String uid = objects[i].getUid();
            String type = objects[i].getClass().getName();
            String name = "";
            if (objects[i].getClass().getName().equals("WorkspaceObject"))
            {
                ModelObject wo = objects[i];
                //noinspection EmptyCatchBlock
                try
                {
                    name =wo.getPropertyObject("object_string").getStringValue();
                }
                catch (NotLoadedException e /*e*/) { } // just ignore
            }
            Log("localObjectChange","    " + uid + " " + type + " " + name);

        }
    }
}
