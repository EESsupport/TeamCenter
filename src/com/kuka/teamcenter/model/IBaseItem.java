package com.kuka.teamcenter.model;
public interface IBaseItem
{
    String getStatus();
    void setStatus(String status);
    String getLayout();
    void setLayout(String layout);
    String getAvailability();
    void setAvailability(String availability);
    String getPercentUsed();
    void setPercentUsed(String percentUsed);
    String getVariant();
    void setVariant(String variant);
    String getRelativeTo();
    void setRelativeTo(String relativeTo);
    String getCycleTime();
    void setCycleTime(String cycleTime);
    String getMaxThroughput();
    void setMaxThroughput(String maxThroughput);
    String getThroughput();
    void setThroughput(String throughput);
    String getThreeDRep();
    void setThreeDRep(String threeDRep);
    String getTwoDRep();
    void setTwoDRep(String twoDRep);
    String getWeb3DFile();
    void setWeb3DFile(String web3DFile);
    String getJtThreeDRep();
    void setJtThreeDRep(String jtThreeDRep);
    String getProcess();
    void setProcess(String process);
}
