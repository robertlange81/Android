package com.robertlange.sparesprit;

/**
 * Created by rlange on 02.02.2015.
 */

import org.json.JSONObject;

public class RESTException extends Exception {

    private static final long serialVersionUID = 4491098305202657442L;

    public RESTException(String message){
        super(message);
    }

    public RESTException(JSONObject errorObject){
        super(errorObject.toString());
    }
}
