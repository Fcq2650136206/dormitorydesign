package com.southwind.dao;

import com.southwind.entity.Student;

import java.util.List;

public interface StudentDao {
    List<Student> list();
    List<Student> search(String key, String value);
    Integer save(Student student);

    Integer update(Student student);
    Integer delete(Integer id);

    List<Integer> findStudentIdByDormitoryId(Integer dormitoryId);

    Integer exchange(Integer studentId, Integer availableDormitoryId);

    List<Student> moveoutList();

    List<Student> searchForMoveout(String key, String value);

    Integer stateForMoveout(Integer studentId);

    List<Student> findByDormitoryId(Integer id);
}
