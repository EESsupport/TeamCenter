package com.kuka.teamcenter.client;

import com.kuka.teamcenter.util.ConnectionUtil;

import java.util.Dictionary;
import java.util.UUID;
public class LoginInfo implements ILoginInfo{

    private String name,password,role,group,host,discriminator;

    private Dictionary<String,String> hosts;
    public Dictionary<String,String> getHosts(){
        return hosts;
    }

    public LoginInfo(){
        name=System.getProperty("user.name");
        password=name;
        discriminator=UUID.randomUUID().toString();
        hosts = ConnectionUtil.getHosts();

        String first = hosts.keys().nextElement();

        host = hosts.get(first);
    }

    public LoginInfo(String name,String password,String role,String group){
        this.name=name;
        this.password=password;
        this.role=role;
        this.group=group;
        discriminator=UUID.randomUUID().toString();
        hosts=ConnectionUtil.getHosts();

        String first = hosts.keys().nextElement();

        host = hosts.get(hosts.get(first));

    }

    public String[] getInfo(){
        return new String[]{name,password,role,group,discriminator};
    }

    public String getName() {
        return name;

    }


    public String getPassword() {
        return password;

    }


    public String getGroup() {
        return group;
    }


    public String getRole() {
        return role;
    }


    public String getHost() {
        return host;
    }


    public String getDiscriminator() {
        return discriminator;
    }




    public void setName(String name) {
        this.name=name;

    }


    public void setPassword(String password) {
        this.password=password;

    }


    public void setGroup(String group) {
        this.group=group;
    }


    public void setRole(String role) {
        this.role=role;
    }


    public void setHost(String host) {
        this.host=host;
    }


    public void setDiscriminator(String discriminator) {
        this.discriminator=discriminator;
    }



}