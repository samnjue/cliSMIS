package com.smis.cli_smis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.smis.cli_smis.entities.Student;
import com.smis.cli_smis.entities.Course;
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
			System.out.println("--- PHASE 2: TESTING SERVICE LAYER AND DATA STRUCTURES ---");

			Student sammy = new Student("Sammy Njue", 20, "A2001");
			Student alex = new Student("Alex Mwangi", 19, "B3002");

			try {
				studentService.addStudent(sammy);
				studentService.addStudent(alex);

				System.out.println("\nAttempting to add Sduplicate sudent (A2001)...");
				studentService.addStudent(new Student("Fake Alice", 22, "A2001"));
			} catch (DuplicateStudentException e) {
				System.err.println("SUCCESSFULLY CAUGHT ERROR: " + e.getMessage());
			}

			System.out.println("\nTotal students in repository: " + studentService.findAll().size());
			studentService.processEnrollments();

			System.out.println("\n--- PHASE 2 TEST COMPLETE ---");
		};
	}

}
