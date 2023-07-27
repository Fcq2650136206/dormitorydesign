package com.southwind.dao;

import com.southwind.entity.Moveout;

import java.util.List;

public interface MoveoutDao {
    Integer save(Moveout moveout);

    List<Moveout> list();

    List<Moveout> search(String key, String value);
}
