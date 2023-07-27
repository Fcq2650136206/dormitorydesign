package com.southwind.service;

import com.southwind.entity.Absent;

import java.util.List;

public interface AbsentService {
    void save(Absent absent);

    List<Absent> list();

    List<Absent> search(String key, String value);
}
