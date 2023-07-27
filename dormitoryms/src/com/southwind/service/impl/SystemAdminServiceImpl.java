package com.southwind.service.impl;

import com.southwind.dao.SystemAdminDao;
import com.southwind.dao.impl.SystemAdminDaoImpl;
import com.southwind.dto.SystemAdminDto;
import com.southwind.entity.SystemAdmin;
import com.southwind.service.SystemAdminService;

public class SystemAdminServiceImpl implements SystemAdminService {
    private SystemAdminDao systemAdminDto=new SystemAdminDaoImpl();
    @Override
    public SystemAdminDto login(String username, String password) {
        //对象返回给前端
        SystemAdminDto systemAdminDto = new SystemAdminDto();
        //查询用户名
        SystemAdmin systemAdmin = this.systemAdminDto.findByUsername(username);
        //判断用户是否存在
        if (systemAdmin==null) {
            systemAdminDto.setCode(-1);
        }else{
            if(!systemAdmin.getPassword().equals(password)){
                systemAdminDto.setCode(-2);
            }else{
                systemAdminDto.setCode(0);
                systemAdminDto.setSystemAdmin(systemAdmin);
            }
        }
        return systemAdminDto;
    }
}
