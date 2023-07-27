package com.southwind.service;

import com.southwind.dto.DormitoryAdminDto;
import com.southwind.entity.DormitoryAdmin;

import java.util.List;

public interface DormitoryAdminService {
    public List<DormitoryAdmin> list();

    public List<DormitoryAdmin> search(String key, String value);

    void save(DormitoryAdmin dormitoryAdmin);

    void update(DormitoryAdmin dormitoryAdmin);

    void delete(Integer id);
    DormitoryAdminDto login(String username, String password);
}
