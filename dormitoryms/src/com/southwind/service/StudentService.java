package com.southwind.service;

import com.southwind.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> list();
    List<Student> search(String key,String value);
    void save(Student student);

    void update(Student student, Integer oldDormitoryId);

    void delete(int id, Integer dormitoryId);
    List<Student> moveoutList();

    List<Student> searchForMoveout(String key, String value);

    List<Student> findByDormitoryId(Integer id);
}
