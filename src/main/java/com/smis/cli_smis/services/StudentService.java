package com.smis.cli_smis.services;

import com.smis.cli_smis.entities.Student;
import java.util.List;
//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class StudentService {
    
    private final DataRepository repository;

    private final Queue<Student> enrollmementQueue = new LinkedList<>();

    public StudentService(DataRepository repository) {
        this.repository = repository;
        this.repository.load();
    }

    public void addStudent(Student student) throws DuplicateStudentException {

        if (repository.findById(student.getStudentId()).isPresent()) {
            throw new DuplicateStudentException("Student with ID " + student.getStudentId() + " already exists");
        }
        
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

    public Optional<Student> findById(String id) {
        //return ((FileRepository)repository).findById(id);
        return repository.findById(id);
    }

    public void processEnrollments() {

        System.out.println("Processing " + enrollmementQueue.size() + " students in the queue...");
        while (!enrollmementQueue.isEmpty()) {
            Student student = enrollmementQueue.poll();
            System.out.println("Enrolled student: " + student.getName());
        }
    }

    public void saveAll(List<Student> students) {
    repository.save(students);
    }

    public List<Student> searchByName(String name) {
        String lowerCaseName = name.toLowerCase();

        return repository.load().stream()
            .filter(student -> student.getName().toLowerCase()
            .contains(lowerCaseName)).collect(Collectors.toList());
    }

    public List<Student> sortStudentsByName() {
        return repository.load().stream()
            .sorted(java.util.Comparator.comparing(Student::getName))
            .collect(Collectors.toList());
    }
}
