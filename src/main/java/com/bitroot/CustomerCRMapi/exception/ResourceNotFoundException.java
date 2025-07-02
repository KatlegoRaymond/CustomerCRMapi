package com.bitroot.CustomerCRMapi.exception;

// Thrown when a requested resource doesn't exist
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}