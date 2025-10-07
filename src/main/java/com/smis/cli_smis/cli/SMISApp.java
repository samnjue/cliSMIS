package com.smis.cli_smis.cli;

import com.smis.cli_smis.entities.Student;
import com.smis.cli_smis.services.StudentService;
import com.smis.cli_smis.entities.Course;
import com.smis.cli_smis.services.DuplicateStudentException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.Optional;

@Component
public class SMISApp implements CommandLineRunner {
    
    private final StudentService studentService;
    private final Scanner scanner = new Scanner(System.in);

    public SMISApp(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- Welcome to the Student Management Information System ---");

        System.out.println("Loaded " + studentService.findAll().size() + " existing students");

        boolean running = true;
        while (running) {
            displayMenu();
            String command = scanner.nextLine().trim().toLowerCase();

            try {
                switch (command) {
                    case "1":
                        handleAddStudent();                        
                        break;
                    case "2":
                        handleFindStudent();
                        break;
                    case "3":
                        handleSearchByName();
                        break;
                    case "4":
                        handleViewAllStudents();
                        break;
                    case "5":
                        handleEnrollCourse();
                        break;
                    case "6":
                        handleSetGrade();
                        break;
                    case "7":
                        studentService.generateReportAsync();
                        break;
                    case "8":
                        running = false;
                        break;                
                    default:
                        System.out.println("Invalid command. Please enter a number from the menu");
                }
            } catch (Exception e) {
                System.err.println("SYSTEM ERROR: " + e.getMessage());
            } 
        }

        studentService.saveAll(studentService.findAll());
        studentService.shutdown();
        System.out.println("SMIS shutting down. Goodbye!");
    }

    private void displayMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Add New Student");
        System.out.println("2. Find Student by ID");
        System.out.println("3. Search Students by Name");
        System.out.println("4. View All Students");
        System.out.println("5. Enroll Student in Course");
        System.out.println("6. Set Student Grade");
        System.out.println("7. Generate Grade Report");
        System.out.println("8. Exit and Save");
        System.out.print("Enter command number: ");
    }

    private void handleEnrollCourse() {
        System.out.print("Enter Student ID for enrollment: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter Course Name: ");
        String courseName = scanner.nextLine();
        System.out.print("Enter lecturer name: ");
        String lecturerName = scanner.nextLine();

        try {
            Course course = new Course(courseName, lecturerName);
            studentService.enrollStudentInCourse(studentId, course);
            System.out.println("Student " + studentId + " successfully enrolled in " + courseName);
        } catch (Exception e) {
            System.err.println("ENROLLMENT FAILED: " + e.getMessage());
        }
    }

    private void handleSetGrade() {
        System.out.println("Enter Student ID to update grade: ");
        String studentId = scanner.nextLine();
        System.out.println("Enter course Name: ");
        String courseName = scanner.nextLine();
        System.out.println("Enter Grade (0-100): ");

        try {
            double grade = Double.parseDouble(scanner.nextLine());
            studentService.setStudentGrade(studentId, courseName, grade);
            System.out.println("Grade " + grade + " set for " + studentId + " in " + courseName);
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Invalid grade format. Must be a number");
        } catch (Exception e) {
            System.err.println("GRADE UPDATE FAILED: " + e.getMessage());
        }
    }

    private void handleAddStudent() {
        System.out.print("Enter the Student's Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the Student's Age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the Student's ID: ");
        String id = scanner.nextLine();

        try {
            Student newStudent = new Student(name, age, id);
            studentService.addStudent(newStudent);
            System.out.println("Student added successfully and data saved!");
        } catch (DuplicateStudentException e) {
            System.err.println("ERROR: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Invalid age format");
        }
    }

    private void handleFindStudent() {
        System.out.print("Enter the Student ID to find: ");
        String id = scanner.nextLine();

        Optional<Student> studentOpt = studentService.findById(id);

        studentOpt.ifPresentOrElse(
            student -> student.displayInfo(), 
            () -> System.out.println("Student with ID " + id + " not found.")
        );
    }

    private void handleSearchByName() {
        System.out.print("Enter the student's name to search: ");
        String name = scanner.nextLine();

        List<Student> results = studentService.searchByName(name);

        if (results.isEmpty()) {
            System.out.println("No students found matching '" + name + "'");
        } else {
            System.out.println("--- Search Results (" + results.size() + " found) ---");
            results.forEach(Student::displayInfo);
        }
    }

    private void handleViewAllStudents() {
        List<Student> sortedStudents = studentService.sortStudentsByName();
        
        if (sortedStudents.isEmpty()) {
            System.out.println("The system contains no student records");
        } else {
            System.out.println("--- All Students (Sorted by Name) ---");
            sortedStudents.forEach(Student::displayInfo);
        }
    }
}
