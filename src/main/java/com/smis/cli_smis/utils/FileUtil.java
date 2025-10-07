package com.smis.cli_smis.utils;

import com.smis.cli_smis.entities.Student;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        } catch (java.io.FileNotFoundException e) {
            System.err.println("File not found at " + filePath + ". Starting with an empty dataset");
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file " + filePath, e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Data corruption: Age field is not a number in file " + filePath, e);
        }

        return students;
    }
}
