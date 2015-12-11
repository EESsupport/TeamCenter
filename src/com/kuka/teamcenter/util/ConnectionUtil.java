package com.kuka.teamcenter.util;


import java.util.Dictionary;
import java.util.Hashtable;

import com.teamcenter.services.strong.core.SessionService;
public class ConnectionUtil {
    private static SessionService service;
    private static Dictionary<String,String> connectionStrings = new Hashtable<String,String>();


    public static String T2(){
        return getHost(HostName.T2);
    }
    public static String T3(){
        return getHost(HostName.T3);
    }
	/*
	public static void connect(String username,String password,String group,String role){
		connect(t3,username,password,group,role);
	}*/

    ConnectionUtil(){


        connectionStrings.put("KSNA T3", "http://tcweb.us.kuka.net.co:80/tc");
        connectionStrings.put("KSNA T2", "http://tcweb2.us.kuka.net.co:80/tc");

        connectionStrings.put(HostName.T2, "http://tcweb2.us.kuka.net.co:80/tc");
        connectionStrings.put(HostName.T3, "http://tcweb.us.kuka.net.co:80/tc");
    }

    public static String getHost(String key){
        return connectionStrings.get(key);
    }

    public static Dictionary<String,String> getHosts(){
        if (connectionStrings.size()==0){
            connectionStrings.put("KSNA T3", "http://tcweb.us.kuka.net.co:80/tc");
            connectionStrings.put("KSNA T2", "http://tcweb2.us.kuka.net.co:80/tc");

            connectionStrings.put(HostName.T2, "http://tcweb2.us.kuka.net.co:80/tc");
            connectionStrings.put(HostName.T3, "http://tcweb.us.kuka.net.co:80/tc");
        }
        return connectionStrings;
    }
    /*
    public static void connect(String address,String username,String password,String group,String role){
        Session session = new Session(t3);
        KukaCredentialManager credentialManager = new KukaCredentialManager();
        Connection connection = new Connection(address,null,credentialManager,"REST","HTTP",true);
        connection.setExceptionHandler(new ConnectionExceptionHandler());

        service = SessionService.getService(connection);
        GetAvailableServicesResponse services=service.getAvailableServices();
    }
    */
    public static SessionService getService(){
        return service;
    }

    final class HostName{
        public static final String T1="T1";
        public static final String T2="T2";
        public static final String T3="T3";
        public static final String T4="T4";
    }
}