package com.kuka.teamcenter.util;

import com.kuka.teamcenter.client.Session;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.strong.User;
import com.teamcenter.soa.client.model.strong.WorkspaceObject;
import com.teamcenter.soa.exceptions.NotLoadedException;

import java.util.Calendar;

/**
 * Created by cberman on 12/10/2015.
 */
public class LogUtil {
    public static String getLogTag(Class sender){
        return sender.getSimpleName();
    }

    public static void Log(String tag,Object msg){
        Session.writeLine(tag,msg);

    }
    public static void Log(String tag,Object msg,Object msg2){
        Session.writeLine(tag,msg);
        Session.writeLine(tag,msg2);
    }

    public static void printObjects(String tag,ModelObject[] objects){
        if (objects==null)
            return;


        // Ensure that the referenced user objects that we will use below are loaded;
        Log(tag,"Name\t\tOwner\t\tLast Modified");

        Log(tag,"====\t\t=====\t\t=============");

        for(int i=0;i<objects.length;i++){
            if(!(objects[i] instanceof WorkspaceObject))
                continue;

            WorkspaceObject wo=(WorkspaceObject)objects[i];
            try{
                String name = wo.get_object_string();
                User owner =(User)wo.get_owning_user();
                Calendar lastModified=wo.get_last_mod_date();
                Log(tag,name + "\t" + owner.get_user_name() + "\t" + lastModified.toString());
            }catch(NotLoadedException e){
                // Print out a message, and skip to the next item in the folder
                // Could do a DataManagementService.getProperties call at this point
                Log(tag,e.getMessage());
                Log(tag,"The Object Property Policy ($TC_DATA/soa/policies/Default.xml) is not configured with this property.");
            }
        }

    }
}
