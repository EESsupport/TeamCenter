package com.kuka.teamcenter.util;

import com.teamcenter.soa.client.model.ModelObject;

public class ItemUtil {
    private static final String TAG = LogUtil.getLogTag(ItemUtil.class);
    private static final String[] DO_NOT_DELETE_ITEMS={
            "SavedSearch",
            "TasksToPerform",
            "TaskInbox",
            "TasksToTrack",
            "Folder",
            "Revision",
            "User_Inbox"
    };

    public static boolean canDelete(ModelObject obj){
        LogUtil.Log(TAG,obj.getClass().getName());
        String name=obj.getClass().getName();
        for (int i=0;i<DO_NOT_DELETE_ITEMS.length;i++){
            if (DO_NOT_DELETE_ITEMS[i]==name)
                return false;
        }
        return true;

    }
}
