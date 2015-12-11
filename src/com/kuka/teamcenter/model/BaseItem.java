package com.kuka.teamcenter.model;

/**
 * Created by cberman on 12/3/2015.
 */
public class BaseItem implements IBaseItem{
    private String Status;
    @Override
    public String getStatus(){return Status;}
    @Override
    public void setStatus(String value){this.Status = value;}private String Layout;
    @Override
    public String getLayout(){return Layout;}
    @Override
    public void setLayout(String value){this.Layout = value;}private String Availability;
    @Override
    public String getAvailability(){return Availability;}
    @Override
    public void setAvailability(String value){this.Availability = value;}private String PercentUsed;
    @Override
    public String getPercentUsed(){return PercentUsed;}
    @Override
    public void setPercentUsed(String value){this.PercentUsed = value;}private String Variant;
    @Override
    public String getVariant(){return Variant;}
    @Override
    public void setVariant(String value){this.Variant = value;}private String RelativeTo;
    @Override
    public String getRelativeTo(){return RelativeTo;}
    @Override
    public void setRelativeTo(String value){this.RelativeTo = value;}private String CycleTime;
    @Override
    public String getCycleTime(){return CycleTime;}
    @Override
    public void setCycleTime(String value){this.CycleTime = value;}private String MaxThroughput;
    @Override
    public String getMaxThroughput(){return MaxThroughput;}
    @Override
    public void setMaxThroughput(String value){this.MaxThroughput = value;}private String Throughput;
    @Override
    public String getThroughput(){return Throughput;}
    @Override
    public void setThroughput(String value){this.Throughput = value;}private String ThreeDRep;
    @Override
    public String getThreeDRep(){return ThreeDRep;}
    @Override
    public void setThreeDRep(String value){this.ThreeDRep = value;}private String TwoDRep;
    @Override
    public String getTwoDRep(){return TwoDRep;}
    @Override
    public void setTwoDRep(String value){this.TwoDRep = value;}private String Web3DFile;
    @Override
    public String getWeb3DFile(){return Web3DFile;}
    @Override
    public void setWeb3DFile(String value){this.Web3DFile = value;}private String JtThreeDRep;
    @Override
    public String getJtThreeDRep(){return JtThreeDRep;}
    @Override
    public void setJtThreeDRep(String value){this.JtThreeDRep = value;}private String Process;
    @Override
    public String getProcess(){return Process;}
    @Override
    public void setProcess(String value){this.Process = value;}
}
