package com.kuka.teamcenter.client;
public interface ILoginInfo {

    String getName();
    String getPassword();
    String getGroup();
    String getRole();
    String getHost();
    String getDiscriminator();

    void setName(String name);
    void setPassword(String password);
    void setGroup(String group);
    void setRole(String role);
    void setHost(String host);
    void setDiscriminator(String discriminator);
}
