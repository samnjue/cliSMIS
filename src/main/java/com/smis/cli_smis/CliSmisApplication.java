package com.smis.cli_smis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.smis.cli_smis.entities.Student;
//import com.smis.cli_smis.entities.Course;
import com.smis.cli_smis.services.StudentService;
import com.smis.cli_smis.services.DuplicateStudentException;

@SpringBootApplication
public class CliSmisApplication {

	public static void main(String[] args) {
		SpringApplication.run(CliSmisApplication.class, args);
	}

	@Bean
	public CommandLineRunner runTest(StudentService studentService) {
		return args -> {
			System.out.println("--- PHASE 3: TESTING FILE PERSISTENCE (I/O) ---");

			List<Student> existingStudents = studentService.findAll();
			System.out.println("STARTUP: Found " + existingStudents.size() + " existing students in the system");

			if (existingStudents.isEmpty()) {
				System.out.println("\nFIRST RUN: Adding new students to the system...");
				try {
					studentService.addStudent(new Student("Sammy Njue", 20, "A2001"));
					studentService.addStudent(new Student("Bob Collymore", 19, "B3002"));
					studentService.addStudent(new Student("Charlie Kirk", 22, "C4003"));

					System.out.println("Saving data on exit...");
					studentService.saveAll(studentService.findAll());

					System.out.println("Please run the application again immediately to test the load function");
				} catch (DuplicateStudentException e) {
					System.err.println("Error: " + e.getMessage());
				}
			} else {
				System.out.println("\nSECOND RUN: Verifying data persistence...");
				System.out.println("Loaded " + existingStudents.size() + " students.");
				existingStudents.forEach(s -> s.displayInfo());

				if (existingStudents.size() == 3) {
					try {
						System.out.println("\nAdding a new student: David (D5004)");
						studentService.addStudent(new Student("David Lee", 23, "D5004"));
						studentService.saveAll(studentService.findAll());
						System.out.println("Data saved with new student. Total count: " + studentService.findAll().size());
					} catch (DuplicateStudentException e) {
						System.err.println("Error: " + e.getMessage());
					}
				}

				System.out.println("File I/O Test Complete. Check the project folder for students.csv");
			}

			System.out.println("\n--- PHASE 3 TEST COMPLETE ---");
		};
	}

}
