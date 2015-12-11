package com.kuka.teamcenter.model.subitems;

import com.kuka.teamcenter.model.ExternalIdImpl;

public class BasicSystem extends ExternalIdImpl
        implements IBasicSystem{
    private String cadSystem;
    public String getcadSystem(){return cadSystem;}
    public void setcadSystem(String value){cadSystem = value;}
    private String boundedBoxMin;
    public String getboundedBoxMin(){return boundedBoxMin;}
    public void setboundedBoxMin(String value){boundedBoxMin = value;}
    private String boundedBoxMax;
    public String getboundedBoxMax(){return boundedBoxMax;}
    public void setboundedBoxMax(String value){boundedBoxMax = value;}
    private String operatingCostPerHour;
    public String getoperatingCostPerHour(){return operatingCostPerHour;}
    public void setoperatingCostPerHour(String value){operatingCostPerHour = value;}
    private String length;
    public String getlength(){return length;}
    public void setlength(String value){length = value;}
    private String width;
    public String getwidth(){return width;}
    public void setwidth(String value){width = value;}
    private String height;
    public String getheight(){return height;}
    public void setheight(String value){height = value;}
    private String weight;
    public String getweight(){return weight;}
    public void setweight(String value){weight = value;}
    private String image;
    public String getimage(){return image;}
    public void setimage(String value){image = value;}
    private String cadFile;
    public String getcadFile(){return cadFile;}
    public void setcadFile(String value){cadFile = value;}
    private String investmentCost;
    public String getinvestmentCost(){return investmentCost;}
    public void setinvestmentCost(String value){investmentCost = value;}
    private String active;
    public String getactive(){return active;}
    public void setactive(String value){active = value;}

}
