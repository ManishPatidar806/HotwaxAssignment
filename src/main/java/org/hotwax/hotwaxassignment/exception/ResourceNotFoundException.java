package org.hotwax.hotwaxassignment.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
            super(message);
    }
}