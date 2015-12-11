package com.kuka.teamcenter.soa;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.kuka.teamcenter.client.Session;
import com.kuka.teamcenter.util.ItemUtil;

import com.kuka.teamcenter.util.LogUtil;
import com.teamcenter.services.strong.core.DataManagementService;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.ServiceData;
/**
 * Created by cberman on 12/2/2015.
 */
public class DataManagement {
    private static final String TAG = LogUtil.getLogTag(DataManagement.class);
    private Session.OnSessionInfoChangedListener mListener;
    public DataManagement(Session.OnSessionInfoChangedListener listener){
        mListener=listener;
    }
    private void log(Object message){
        mListener.OnInfoChanged("DataManagement",message);
    }

    public List<ModelObject> loadItems(String[] uids){
        List<ModelObject> result = new ArrayList<>();
        DataManagementService dmService = DataManagementService.getService(Session.getConnection());
        ServiceData datas= dmService.loadObjects(uids);
        for(int i=0;i<datas.sizeOfPlainObjects();i++){
            ModelObject obj=datas.getPlainObject(i);
            if(!ItemUtil.canDelete(obj))
                continue;
            result.add(obj);
        }
        mListener.OnInfoChanged("Loaded",result);
        return result;
    }
    public Hashtable<String,Integer> deleteItems(String[] uids){
        Hashtable<String,Integer> result = new Hashtable<String,Integer>();
        DataManagementService dmService = DataManagementService.getService(Session.getConnection());
        ServiceData datas= dmService.loadObjects(uids);
        List<ModelObject> objectsToDelete=new ArrayList<ModelObject>();

        for(int i=0;i<datas.sizeOfPlainObjects();i++){
            ModelObject obj=datas.getPlainObject(i);
            if(!ItemUtil.canDelete(obj))
                continue;
            objectsToDelete.add(obj);
        }

        if (objectsToDelete.size()==0)
            return result;

        ServiceData serviceData = dmService.deleteObjects(objectsToDelete.toArray(new ModelObject[objectsToDelete.size()]));
        String msg=String.format("Deleted %s items , There were %s errors deleting some objects", serviceData.sizeOfDeletedObjects(), serviceData.sizeOfPartialErrors());
        log(msg);

        result.put("Deleted",serviceData.sizeOfDeletedObjects());
        result.put("Errors",serviceData.sizeOfPartialErrors());
        return result;
    }
}
