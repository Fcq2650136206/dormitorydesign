package com.southwind.service.impl;

import com.southwind.dao.DormitoryAdminDao;
import com.southwind.dao.impl.DormitoryAdminDaoImpl;
import com.southwind.dto.DormitoryAdminDto;
import com.southwind.dto.SystemAdminDto;
import com.southwind.entity.DormitoryAdmin;
import com.southwind.entity.SystemAdmin;
import com.southwind.service.DormitoryAdminService;

import java.util.List;

public class DormitoryAdminServiceImpl implements DormitoryAdminService {
    private DormitoryAdminDao dormitoryAdminDao=new DormitoryAdminDaoImpl();
    @Override
    public List<DormitoryAdmin> list() {
        return this.dormitoryAdminDao.list();
    }

    @Override
    public List<DormitoryAdmin> search(String key, String value) {
        if(value.equals("")) return this.dormitoryAdminDao.list();
        return this.dormitoryAdminDao.search(key,value);
    }

    @Override
    public void save(DormitoryAdmin dormitoryAdmin) {
        Integer save = this.dormitoryAdminDao.save(dormitoryAdmin);
        if(save!=1) throw new RuntimeException("保存不成功");
    }

    @Override
    public void update(DormitoryAdmin dormitoryAdmin) {
        Integer update = this.dormitoryAdminDao.update(dormitoryAdmin);
        if(update!=1) throw new RuntimeException("更新失败");
    }

    @Override
    public void delete(Integer id) {
        Integer delete = this.dormitoryAdminDao.delete(id);
        if(delete!=1) throw new RuntimeException("删除失败");
    }

    @Override
    public DormitoryAdminDto login(String username, String password) {
        //对象返回给前端
        DormitoryAdminDto dormitoryAdminDto=new DormitoryAdminDto();
        //查询用户名
        DormitoryAdmin dormitoryAdmin = this.dormitoryAdminDao.findByUsername(username);
        //判断用户是否存在
        if (dormitoryAdmin==null) {
            dormitoryAdminDto.setCode(-1);
        }else{
            if(!dormitoryAdmin.getPassword().equals(password)){
                dormitoryAdminDto.setCode(-2);
            }else{
                dormitoryAdminDto.setCode(0);
                dormitoryAdminDto.setDormitoryAdmin(dormitoryAdmin);
            }
        }
        return dormitoryAdminDto;
    }
}
