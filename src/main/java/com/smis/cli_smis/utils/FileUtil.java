package com.smis.cli_smis.utils;

import org.springframework.stereotype.Component;

import com.smis.cli_smis.entities.Student;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileUtil {
    
    private static final String CSV_DELIMITER = ",";

    public void writeToFile(List<Student> students, String filePath) {

         try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("studentId,name,age\n");
            
            for (Student student : students) {
                
                String line = String.join(CSV_DELIMITER,
                    student.getStudentId(),
                    student.getName(),
                    String.valueOf(student.getAge())
                );
                writer.write(line);
                writer.newLine();                
            }
            System.out.println("DEBUG: Succcessfully wrote " + students.size() + " records to " + filePath);
         } catch (IOException e) {
            
            throw new RuntimeException("Error writing to file " + filePath, e);
         }
    }

    public List<Student> readFromFile(String filePath) {
        List<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                if (firstLine) {
                    firstLine = false;
                    if (line.toLowerCase().startsWith("studentid")) {
                        continue;
                    }
                }

                String[] parts = line.split(CSV_DELIMITER, -1);
                if (parts.length < 3) {
                    System.err.println("Skipping malformed CSV line: " + line);
                    continue;
                }

                String studentId = parts[0].trim();
                String name = parts[1].trim();
                int age;
                try {
                    age = Integer.parseInt(parts[2].trim());
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Invalid age value for studentId=" + studentId + " in file " + filePath);
                }

                students.add(new Student(name, age, studentId));
            }
        } catch (java.io.FileNotFoundException e) {
            System.err.println("File not found at " + filePath + ". Starting with an empty dataset");
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file " + filePath, e);
        }

        return students;
    }
}
