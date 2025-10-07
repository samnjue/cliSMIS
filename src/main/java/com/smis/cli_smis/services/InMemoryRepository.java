package com.smis.cli_smis.services;

import com.smis.cli_smis.entities.Student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryRepository implements DataRepository {
    
    private final Map<String, Student> studentMap = new HashMap<>();

    @Override
    public void save(List<Student> students) {
        studentMap.clear();
        for (Student student : students) {
            studentMap.put(student.getStudentId(), student);
        }
        System.out.println("DEBUG: Successfully saved " + studentMap.size() + " students to memory");
    }

    @Override
    public List<Student> load() {
        return new ArrayList<>(studentMap.values());
    }

    public Student findById(String id) {
        return studentMap.get(id);
    }
}
