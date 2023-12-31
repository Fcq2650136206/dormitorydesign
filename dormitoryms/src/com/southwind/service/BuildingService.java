package com.southwind.service;

import com.southwind.entity.Building;

import java.util.List;

public interface BuildingService {
    List<Building> list();

    List<Building> search(String key, String value);

    void save(Building building);

    void update(Building building);

    void delete(Integer id);

}
