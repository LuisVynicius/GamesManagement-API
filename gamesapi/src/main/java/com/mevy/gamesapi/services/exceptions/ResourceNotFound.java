package com.mevy.gamesapi.services.exceptions;

public class ResourceNotFound extends RuntimeException {
    
    public ResourceNotFound(Class<?> object, Object id) {
        super(object.getSimpleName() + " Not Found. Id: " + id);
    }

}
