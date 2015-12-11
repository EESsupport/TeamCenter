package com.kuka.teamcenter.model;

/**
 * Created by cberman on 12/3/2015.
 */
public class Version {
    private VersionId versionId;
    public VersionId getVersionId(){return versionId;}
    public void setVersionId(VersionId value){versionId=value;}

    private String versionName;
    public String getVersionName(){return versionName;}
    public void setVersionName(String value){versionName=value;}


}
