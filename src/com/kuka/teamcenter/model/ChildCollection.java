package com.kuka.teamcenter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cberman on 12/3/2015.
 */
public class ChildCollection extends ExternalIdImpl
        implements IChildCollection{

    private String[] children;
    @Override
    public String[] getchildren() {
        return  children;
    }

    @Override
    public void setchildren(String[] value) {
        children=value;
    }

    private List<ExternalIdImpl> objects = new ArrayList<>();
    public List<ExternalIdImpl> getObjects(){return objects;}
    public void setObjects(List<ExternalIdImpl> value){objects = value;}
}
