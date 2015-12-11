package com.kuka.teamcenter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cberman on 12/3/2015.
 */
public class Data {
    private List<ExternalIdImpl> mObjects = new ArrayList<>();
    public List<ExternalIdImpl> getObjects(){return mObjects;}
    public void setObjects(List<ExternalIdImpl> value){mObjects=value;}
    public Data(){

    }
}
