package com.kuka.teamcenter.model;


/**
 * Created by cberman on 12/3/2015.
 */
public interface IExternalId extends IBaseItem {
    String getExternalId();
    void setExternalId(String value);

    NodeInfo getNodeInfo();
    void setNodeInfo(NodeInfo value);

    String getName();
    void setName(String value);
}
