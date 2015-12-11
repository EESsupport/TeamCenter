package com.kuka.teamcenter;

import com.kuka.teamcenter.client.Session;

/**
 * Created by cberman on 12/10/2015.
 */
public class BaseClass {
    protected void Log(String tag,Object msg){
        Session.writeLine(tag,msg);
    }
    protected void Log(String tag,Object msg1,Object msg2){
        Session.writeLine(tag,msg1);
        Session.writeLine(tag,msg2);

    }
}
