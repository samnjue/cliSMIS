package com.smis.cli_smis.services;

import com.smis.cli_smis.entities.Student;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.springframework.stereotype.Service;

@Service
public class StudentService {
    
    private final InMemoryRepository repository;

    private final Queue<Student> enrollmementQueue = new LinkedList<>();

    public StudentService(InMemoryRepository repository) {
        this.repository = repository;
        this.repository.load();
    }

    public void addStudent(Student student) throws DuplicateStudentException {
        if (repository.findById(student.getStudentId()) != null) {
            throw new DuplicateStudentException("Student with ID " + student.getStudentId() + " already exists");
        }

        enrollmementQueue.offer(student);
        System.out.println("Student " + student.getName() + " added to enrollment queue.");

        List<Student> currentStudents = repository.load();
        currentStudents.add(student);
        repository.save(currentStudents);
    }

    public List<Student> findAll() {
        return repository.load();
    }

    public Student findById(String id) {
        return repository.findById(id);
    }

    public void processEnrollments() {

        System.out.println("Processing " + enrollmementQueue.size() + " students in the queue...");
        while (!enrollmementQueue.isEmpty()) {
            Student student = enrollmementQueue.poll();
            System.out.println("Enrolled student: " + student.getName());
        }
    }
}
