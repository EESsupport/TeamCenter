package com.kuka.teamcenter.client;

import com.kuka.teamcenter.util.LogUtil;
import com.teamcenter.soa.client.model.ErrorStack;
import com.teamcenter.soa.client.model.ErrorValue;
import com.teamcenter.soa.client.model.PartialErrorListener;

public class KukaPartialErrorListener implements PartialErrorListener{
    private static final String TAG= LogUtil.getLogTag(KukaPartialErrorListener.class);
    private Session.OnSessionInfoChangedListener mListener;
    public KukaPartialErrorListener(Session.OnSessionInfoChangedListener listener){
        mListener=listener;
    }
    /// <summary>
    /// <see cref="Teamcenter.Soa.Client.Model.PartialErrorListener.HandlePartialError(ErrorStack[])"/>
    /// </summary>
    /// <param name="stacks"></param>

    public void handlePartialError(ErrorStack[] stacks) {
        {
            if (stacks.length == 0) return;




            for (ErrorStack es:stacks)
            {
                ErrorValue[] errors = es.getErrorValues();

                // The different service implementation may optionally associate
                // an ModelObject, client ID, or nothing, with each partial error
                if (es.hasAssociatedObject())
                {

                    mListener.OnInfoChanged(TAG,"object " +es.getAssociatedObject().getUid());
                }
                else if (es.hasClientId())
                {
                    mListener.OnInfoChanged(TAG,"client id " + es.getClientId());
                }
                else if (es.hasClientIndex())
                {
                    mListener.OnInfoChanged(TAG,"client index " + es.getClientIndex());
                }

                for(ErrorValue err:errors){
                    String msg = String.format("\tCode: %s\tSeverity: %s\t Message: %s",err.getCode(),err.getLevel(),err.getMessage());
                    mListener.OnInfoChanged(TAG,msg);
                }

            }
        }

    }
}
