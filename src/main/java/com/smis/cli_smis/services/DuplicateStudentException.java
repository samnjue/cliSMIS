package com.smis.cli_smis.services;

public class DuplicateStudentException extends Exception {
    
    public DuplicateStudentException(String message) {
        super(message);
    }
}