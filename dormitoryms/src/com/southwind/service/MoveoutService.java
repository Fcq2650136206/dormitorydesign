package com.southwind.service;

import com.southwind.entity.Moveout;

import java.util.List;

public interface MoveoutService {

    void save(Moveout moveout);

    List<Moveout> list();

    List<Moveout> search(String key, String value);
}
