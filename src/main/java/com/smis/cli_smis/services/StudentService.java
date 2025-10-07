package com.smis.cli_smis.services;

import com.smis.cli_smis.entities.Student;
import com.smis.cli_smis.entities.Course;
import java.util.List;
//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

@Service
public class StudentService {
    
    private final DataRepository repository;

    private final Queue<Student> enrollmementQueue = new LinkedList<>();

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    public void generateReportAsync() {
        List<Student> currentStudents = repository.load();

        executorService.submit(new ReportGenerator(currentStudents));
        System.out.println("Report generation started in the background. Check console for the output later");
    }

    public void enrollStudentInCourse(String studentId, Course course) throws Exception {
        Student student = findById(studentId).orElseThrow(
            () -> new Exception("Student not found for enrollment")
        );

        student.enrollInCourse(course);

        saveAll(repository.load());
    }

    public void setStudentGrade(String studentId, String courseName, double grade) throws Exception {
        Student student =  findById(studentId).orElseThrow(
            () -> new Exception("Student not found to set grade")
        );

        student.setGrade(courseName, grade);

        saveAll(repository.load());
    }

    public void shutdown() {
        System.out.println("[SYSTEM] Shutting down thread pool...");
        executorService.shutdown();
    }

    public StudentService(DataRepository repository) {
        this.repository = repository;
        //this.repository.load();
    }

    public void addStudent(Student student) throws DuplicateStudentException {

        if (repository.findById(student.getStudentId()).isPresent()) {
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
