package com.smis.cli_smis.services;

import com.smis.cli_smis.entities.Student;
import java.util.List;

public interface DataRepository {

    Student findById(String id);
    
    void save(List<Student> students);

    List<Student> load();
}
