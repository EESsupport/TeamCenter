package com.kuka.teamcenter.queries;
import com.kuka.teamcenter.BaseClass;
import com.kuka.teamcenter.client.Session;
import com.kuka.teamcenter.util.LogUtil;
import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.services.strong.core.DataManagementService;
import com.teamcenter.services.strong.query.SavedQueryService;
import com.teamcenter.services.strong.query._2006_03.SavedQuery.GetSavedQueriesResponse;
import com.teamcenter.services.strong.query._2007_09.SavedQuery.QueryResults;
import com.teamcenter.services.strong.query._2007_09.SavedQuery.SavedQueriesResponse;
import com.teamcenter.services.strong.query._2008_06.SavedQuery.QueryInput;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.ServiceData;
import com.teamcenter.soa.client.model.strong.ImanQuery;

public class Query extends BaseClass{
    private static final String TAG = Query.class.getSimpleName();
    /**
     * Perform a simple query of the database
     *
     */
    public void queryItems()
    {

        ImanQuery query = null;

        // Get the service stub.
        SavedQueryService queryService = SavedQueryService.getService(Session.getConnection());
        DataManagementService dmService= DataManagementService.getService(Session.getConnection());
        try
        {

            // *****************************
            // Execute the service operation
            // *****************************
            GetSavedQueriesResponse savedQueries = queryService.getSavedQueries();


            if (savedQueries.queries.length == 0)
            {
                Log(TAG,"There are no saved queries in the system.");
                return;
            }

            // Find one called 'Item Name'
            for (int i = 0; i < savedQueries.queries.length; i++)
            {

                if (savedQueries.queries[i].name.equals("Item Name"))
                {
                    query = savedQueries.queries[i].query;
                    break;
                }
            }
        }
        catch (ServiceException e)
        {
            Log(TAG,"GetSavedQueries service request failed.\n"+e.getMessage());

            return;
        }

        if (query == null)
        {
            Log(TAG,"There is not an 'Item Name' query.");
            return;
        }

        try
        {
            //Search for all Items, returning a maximum of 25 objects
            QueryInput savedQueryInput[] = new QueryInput[1];
            savedQueryInput[0] = new QueryInput();
            savedQueryInput[0].query = query;
            savedQueryInput[0].maxNumToReturn = 25;
            savedQueryInput[0].limitList = new ModelObject[0];
            savedQueryInput[0].entries = new String[]{"Item Name" };
            savedQueryInput[0].values = new String[1];
            savedQueryInput[0].values[0] = "*";


            //*****************************
            //Execute the service operation
            //*****************************
            SavedQueriesResponse savedQueryResult = queryService.executeSavedQueries(savedQueryInput);
            QueryResults found = savedQueryResult.arrayOfResults[0];

            Log(TAG,"Found Items:");


            // Page through the results 10 at a time
            for(int i=0; i< found.objectUIDS.length; i+=10)
            {
                int pageSize = (i+10<found.objectUIDS.length)? 10:found.objectUIDS.length-i;

                String[] uids = new String[pageSize];
                System.arraycopy(found.objectUIDS, i + 0, uids, 0, pageSize);
                ServiceData sd = dmService.loadObjects( uids );
                ModelObject[] foundObjs = new ModelObject[ sd.sizeOfPlainObjects()];
                for( int k =0; k< sd.sizeOfPlainObjects(); k++)
                {
                    foundObjs[k] = sd.getPlainObject(k);
                }

                LogUtil.printObjects(TAG, foundObjs );
            }
        }
        catch (Exception e)
        {
            Log(TAG,"ExecuteSavedQuery service request failed.",e.getMessage());
        }

    }
}