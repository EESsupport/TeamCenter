package com.kuka.teamcenter.ui;

import java.util.Date;

/**
 * Created by cberman on 12/4/2015.
 */
public class Message{
    public static final int EXCEPTION=0;
    public static final int INFO=1;
    public static final int ERROR=2;
    public static final int REQUEST=3;
    private int level;
    private String sender;
    private Object message;
    private Date date;

    public void setSender(String value){sender=value;}
    public void setMessage(Object value){message=value;}
    public void setDate(Date value){date=value;}
    public String getSender() {
        return sender;
    }

    public Object getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
public int getLevel(){
    return setLevel();

}
    private int setLevel(){
        if (sender.contains("Error"))
            return ERROR;
        switch(sender){
            case "state":
                 return INFO;

            case "serviceRequest":
            case "serviceResponse":
            case "login":
            case"localObjectChange":
                return REQUEST;

            case "handlePartialError":
                return ERROR;
            default:
                return 5;

        }
    }

    public Message(){
        this.date = new Date(System.currentTimeMillis());
        this.sender="";
        this.message="";
    }
    private String capitalize(String input){
        if (input.length()<=0)
            return input;
        return input.substring(0,1).toUpperCase()+input.substring(1);
    }
    public Message(String sender, Object message){


        this.sender=capitalize(sender);
        this.message=capitalize(message.toString());
        this.date = new Date(System.currentTimeMillis());

    }
}