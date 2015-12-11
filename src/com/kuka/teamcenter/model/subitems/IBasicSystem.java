package com.kuka.teamcenter.model.subitems;

public interface IBasicSystem{
    String getcadSystem();
    void setcadSystem(String value);
    String getboundedBoxMin();
    void setboundedBoxMin(String value);
    String getboundedBoxMax();
    void setboundedBoxMax(String value);
    String getoperatingCostPerHour();
    void setoperatingCostPerHour(String value);
    String getlength();
    void setlength(String value);
    String getwidth();
    void setwidth(String value);
    String getheight();
    void setheight(String value);
    String getweight();
    void setweight(String value);
    String getimage();
    void setimage(String value);
    String getcadFile();
    void setcadFile(String value);
    String getinvestmentCost();
    void setinvestmentCost(String value);
    String getactive();
    void setactive(String value);

}
