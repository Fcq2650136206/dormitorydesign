package com.southwind.service.impl;

import com.southwind.dao.BuildingDao;
import com.southwind.dao.DormitoryDao;
import com.southwind.dao.StudentDao;
import com.southwind.dao.impl.BuildingDaoImpl;
import com.southwind.dao.impl.DormitoryDaoImpl;
import com.southwind.dao.impl.StudentDaoImpl;
import com.southwind.entity.Building;
import com.southwind.service.BuildingService;

import java.util.List;

public class BuildingServiceImpl implements BuildingService {
    private BuildingDao buildingDao=new BuildingDaoImpl();
    private DormitoryDao dormitoryDao=new DormitoryDaoImpl();
    private StudentDao studentDao=new StudentDaoImpl();
    @Override
    public List<Building> list() {
        return this.buildingDao.list();
    }

    @Override
    public List<Building> search(String key, String value) {
        if(value.equals("")) return this.buildingDao.list();
        return this.buildingDao.search(key,value);
    }

    @Override
    public void save(Building building) {
        Integer save = this.buildingDao.save(building);
        if(save!=1) throw new RuntimeException("保存失败");
    }

    @Override
    public void update(Building building) {
        Integer update = this.buildingDao.update(building);
        if(update!=1) throw new RuntimeException("保存失败");
    }

    @Override
    public void delete(Integer id) {
        /**
         * 先把删除楼宇宿舍里面的学生换宿舍
         * 在删除宿舍然后删除楼宇
         */
        //因为宿舍和学生很多不要用多级联查-对多关系
        //先查询宿舍id
        List<Integer> dormitoryIdList = this.dormitoryDao.findDormitoryIdByBuildingID(id);
        //在查询学生id
        for (Integer dormitoryId : dormitoryIdList) {
            List<Integer> studentIdList = this.studentDao.findStudentIdByDormitoryId(dormitoryId);
            for (Integer studentId : studentIdList) {
                //查找空余宿舍每次只取一条
                Integer availableDormitoryId = this.dormitoryDao.availableId();
                //如果空位没l
                if(availableDormitoryId==0) throw new RuntimeException("没有空位了");
                //开始交换学生
                Integer exchange = this.studentDao.exchange(studentId, availableDormitoryId);
                //开始减空余床位
                Integer integer = this.dormitoryDao.subAvailable(availableDormitoryId);
                if(integer!=1 || exchange != 1) throw new RuntimeException("学生交换失败");
            }
            //开始删除宿舍
            Integer delete = this.dormitoryDao.delete(dormitoryId);
            if(delete!=1) throw new RuntimeException("宿舍删除失败");
        }
        //开始删除楼宇
        Integer delete = this.buildingDao.delete(id);
        if(delete!=1) throw new RuntimeException("楼宇删除失败");

    }

}
