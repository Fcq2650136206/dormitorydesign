package com.southwind.dao;

import com.southwind.entity.Dormitory;

import java.util.List;

public interface DormitoryDao {
    List<Dormitory> list();
    List<Dormitory> availableList();
    Integer subAvailable(Integer id);
    Integer addAvailable(Integer id);

    List<Integer> findDormitoryIdByBuildingID(Integer id);
    Integer availableId();

    Integer delete(Integer dormitoryId);

    List<Dormitory> search(String key, String value);

    Integer save(Dormitory dormitory);

    Integer update(Dormitory dormitory);

    List<Dormitory> findByBuildingId(Integer id);
}
