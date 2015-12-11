package com.kuka.teamcenter.model;

/**
 * Created by cberman on 12/3/2015.
 */
public class AssemblyBase extends ChildCollection
implements ICadSystem{
    private String cadSystem;
    private String estimatedCost;

    public String getcadSystem() {
        return cadSystem;
    }

    public void setcadSystem(String cadSystem) {
        this.cadSystem = cadSystem;
    }

    public String getestimatedCost() {
        return estimatedCost;
    }

    public void setestimatedCost(String estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public class AssemblyPart extends AssemblyBase{}
    public class AssemblyStation extends AssemblyBase{}
    public class AssemblySegment extends AssemblyBase{}
}
