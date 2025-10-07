package com.smis.cli_smis.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Student extends Person {
    
    private String studentId;

    private List<Course> courses;

    private Map<String, Double> grades;

    public Student(String name, int age, String studentId) {
        super(name, age);
        this.studentId = studentId;
        this.courses = new ArrayList<>();
        this.grades = new HashMap<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public void enrollInCourse(Course course) {
        this.courses.add(course);
    }

    public void setGrade(String courseName, double grade) {
        this.grades.put(courseName, grade);
    }

    public Optional<Double> getGrade(String courseName) {
        return Optional.ofNullable(this.grades.get(courseName));
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Map<String, Double> getGrades() {
        return grades;
    }

    @Override
    public void displayInfo() {
        System.out.println("----------------------------------------");
        System.out.println("Student ID: " + studentId);
        System.out.println("Name: " + getName());
        System.out.println("Age: " + getAge()); 
        System.out.println("Enrolled Courses: " + courses.size());
        System.out.println("----------------------------------------");
    }
}
