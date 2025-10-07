package com.smis.cli_smis.services;

import com.smis.cli_smis.entities.Student;
import java.util.List;

public class ReportGenerator implements Runnable {
    
    private final List<Student> students;

    public ReportGenerator(List<Student> students) {
        this.students = students;
    }

    @Override
    public void run() {
        System.out.println("\n[REPORT THREAD] Starting grade report generation...");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[REPORT THREAD] Report generation interrupted");
            return;
        }

        double averageCourses = students.stream()
            .mapToDouble(s -> s.getCourses().size())
            .average()
            .orElse(0.0);

        System.out.println("[REPORT THREAD] Total students processed: " + students.size());
        System.out.printf("[REPORT THREAD] Average courses enrolled per student: %.2f\n", averageCourses);
        System.out.println("[REPORT THREAD] Report generation finished");
    }
}
