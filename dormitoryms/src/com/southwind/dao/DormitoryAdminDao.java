package com.southwind.dao;

import com.southwind.entity.DormitoryAdmin;

import java.util.List;

public interface DormitoryAdminDao {
    public List<DormitoryAdmin> list();

    public List<DormitoryAdmin> search(String key,String value);

    Integer save(DormitoryAdmin dormitoryAdmin);

    Integer update(DormitoryAdmin dormitoryAdmin);

    Integer delete(Integer id);

    DormitoryAdmin findByUsername(String username);
}
