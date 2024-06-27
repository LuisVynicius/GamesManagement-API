package com.mevy.gamesapi.services.exceptions;

public class ResourceNotFound extends RuntimeException {
    
    public ResourceNotFound(Class<?> object, Object identifier) {
        super(object.getSimpleName() + " Not Found. Identifier: " + identifier);
    }

}
