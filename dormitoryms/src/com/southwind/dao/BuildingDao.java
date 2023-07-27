package com.southwind.dao;

import com.southwind.entity.Building;

import java.util.List;

public interface BuildingDao {
    public List<Building> list();

    List<Building> search(String key,String value);

    Integer save(Building building);

    Integer update(Building building);

    Integer delete(Integer id);
}
