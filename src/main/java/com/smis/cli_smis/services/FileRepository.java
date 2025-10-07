package com.smis.cli_smis.services;

import com.smis.cli_smis.entities.Student;
import com.smis.cli_smis.utils.FileUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class FileRepository implements DataRepository { 
    
    private final FileUtil fileUtil;
    private final String filePath = "students.csv";

    private final Map<String, Student> studentMap = new HashMap<>();

    public FileRepository(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
        this.load();
    }

    @Override
    public void save(List<Student> students) {
        studentMap.clear();
        for (Student student : students) {
            studentMap.put(student.getStudentId(), student);
        }
        fileUtil.writeToFile(students, filePath);
    }

    @Override
    public List<Student> load() {
        List<Student> students = fileUtil.readFromFile(filePath);

        studentMap.clear();
        for (Student student : students) {
            studentMap.put(student.getStudentId(), student);
        }

        return students;
    }

    @Override
    public Optional<Student> findById(String id) {
        return Optional.ofNullable(studentMap.get(id));
    }
}
