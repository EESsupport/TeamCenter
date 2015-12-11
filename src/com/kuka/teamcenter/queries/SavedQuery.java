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
import com.teamcenter.soa.client.model.strong.ImanQuery;
public class SavedQuery extends BaseClass{
    private SavedQueryService queryService;
    private String queryName;
    private ImanQuery query;
    private static final String TAG= LogUtil.getLogTag(SavedQuery.class);
    public String[] setQuery(String queryName,String[] queryEntries, String[] queryValues){
        String[] result = null;
        this.queryName=queryName;


        // Get the service stub.


        try{
            DataManagementService.getService(Session.getConnection());
            queryService=SavedQueryService.getService(Session.getConnection());

        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            GetSavedQueriesResponse savedQueries=queryService.getSavedQueries();

            for(int i=0;i<savedQueries.queries.length;i++){
                String name = savedQueries.queries[i].name.trim();
                LogUtil.Log(TAG,"QueryName: "+name + " Name: "+queryName);
                if (name.equals(queryName)){
                    query=savedQueries.queries[i].query;
                    break;
                }
            }

            if (query!=null){
                result = queryData(queryEntries,queryValues);
            }else{
                Log(TAG,"Could not find query");

            }




        }catch(ServiceException e){
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    private String[] queryData(String[] queryEntries,String[] queryValues) throws ServiceException{
        String[] result=null;
        if(queryEntries==null||queryEntries.length==0||queryValues==null||queryValues.length==0)
            return result;

        QueryInput queryInput = new QueryInput();
        queryInput.entries=queryEntries;
        queryInput.values=queryValues;
        queryInput.query=query;
        queryInput.limitList= new ModelObject[0];

        SavedQueriesResponse queryResponse = queryService.executeSavedQueries(new QueryInput[]{queryInput});

        if (queryResponse.serviceData.sizeOfPartialErrors()>0)
            throw new ServiceException("Error in QueryService.ExeciteSavedQuerys for query :" + queryName);


        QueryResults[] foundItems=queryResponse.arrayOfResults;
        result = foundItems[0].objectUIDS;
        return result;
    }
}