package com.backend.android.api.exceptions;

import java.lang.RuntimeException;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }
}
