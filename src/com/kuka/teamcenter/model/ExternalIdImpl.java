package com.kuka.teamcenter.model;


/**
 * Created by cberman on 12/3/2015.
 */
public class ExternalIdImpl extends BaseItem
implements IExternalId{
    private String externalId;
    @Override
    public String getExternalId() {
        return externalId;
    }

    @Override
    public void setExternalId(String value) {
externalId=value;
    }

    private NodeInfo nodeInfo;
    @Override
    public NodeInfo getNodeInfo() {
        return nodeInfo;
    }

    @Override
    public void setNodeInfo(NodeInfo value) {
        nodeInfo = value;
    }

    private String name;
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String value) {
        name=value;
    }
}
