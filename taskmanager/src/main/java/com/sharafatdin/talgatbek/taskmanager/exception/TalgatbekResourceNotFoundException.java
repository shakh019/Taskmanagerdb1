package com.sharafatdin.talgatbek.taskmanager.exception;

/** @author Talgatbek Sharafatdin */
public class TalgatbekResourceNotFoundException extends RuntimeException {
    public TalgatbekResourceNotFoundException(String message) {
        super(message);
    }
    public TalgatbekResourceNotFoundException(String resource, Long id) {
        super(resource + " not found with id: " + id);
    }
}
