package com.smis.cli_smis.entities;

public class Course {
    
    private String name;
    private String lecturer;

    public Course(String name, String lecturer) {
        this.name = name;
        this.lecturer = lecturer;
    }

    public String getName() {
        return name;
    }

    public String getLecturer() {
        return lecturer;
    }

    @Override
    public String toString() {
        return name + " (" + lecturer + ") ";
    }
}
