package com.kuka.teamcenter.exceptions;

/**
 * Created by cberman on 12/2/2015.
 */
public class InvalidLoginException extends Exception{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidLoginException(String message){
        super(message);
    }
}
