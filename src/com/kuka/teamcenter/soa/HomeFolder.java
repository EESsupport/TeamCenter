package com.kuka.teamcenter.soa;
import com.kuka.teamcenter.client.Session;
import com.kuka.teamcenter.util.LogUtil;
import com.teamcenter.services.strong.core.DataManagementService;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.strong.Folder;
import com.teamcenter.soa.client.model.strong.User;
import com.teamcenter.soa.client.model.strong.WorkspaceObject;
import com.teamcenter.soa.exceptions.NotLoadedException;

public class HomeFolder {
    private static final String TAG= LogUtil.getLogTag(HomeFolder.class);
    public void listHomeFolder(User user){
        Folder home = null;
        WorkspaceObject[] contents=null;

        // Get the service stub
        DataManagementService dmService = DataManagementService.getService(Session.getConnection());
        try{
            // User was a primary object returned from the login command
            // the Object Property Policy should be configured to include the
            // 'home_folder' property. However the actuall 'home_folder' object
            // was a secondary object returned from the login request and
            // therefore does not have any properties associated with it. We will need to
            // get those properties explicitly with a 'getProperties' service request.
            home = user.get_home_folder();
        }catch(NotLoadedException e){
            LogUtil.Log(TAG,e.getMessage(),"The Object Property Policy ($TC_DATA/soa/policies/Default.xml) is not configured with this property.");

        }

        //noinspection EmptyCatchBlock
        try{
            ModelObject[] objects={home};
            String[] attributes ={"contents"};
            // *****************************
            // Execute the service operation
            // *****************************
            dmService.getProperties(objects, attributes);
            // The above getProperties call returns a ServiceData object, but it
            // just has pointers to the same object we passed into the method, so the
            // input object have been updated with new property values
            contents = home.get_contents();
        }
        // This should never be thrown, since we just explicitly asked for this
        // property
        catch(NotLoadedException e){
        }

        LogUtil.printObjects(TAG,contents);

    }
}