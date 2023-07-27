package com.southwind.service.impl;

import com.southwind.dao.DormitoryDao;
import com.southwind.dao.StudentDao;
import com.southwind.dao.impl.DormitoryDaoImpl;
import com.southwind.dao.impl.StudentDaoImpl;
import com.southwind.entity.Dormitory;
import com.southwind.service.DormitoryService;

import java.util.List;

public class DormitoryServiceImpl implements DormitoryService {
    private DormitoryDao dormitoryDao=new DormitoryDaoImpl();
    private StudentDao studentDao=new StudentDaoImpl();
    @Override
    public List<Dormitory> list() {
        return this.dormitoryDao.list();
    }

    @Override
    public List<Dormitory> search(String key, String value) {
        if(value.equals("")) return this.dormitoryDao.list();
        return this.dormitoryDao.search(key,value);
    }

    @Override
    public void save(Dormitory dormitory) {
        Integer save = this.dormitoryDao.save(dormitory);
        if(save!=1) throw new RuntimeException("保存失败");
    }

    @Override
    public void update(Dormitory dormitory) {
        Integer update = this.dormitoryDao.update(dormitory);
        if(update!=1) throw new RuntimeException("更新失败");
    }

    @Override
    public void delete(Integer id) {
        List<Integer> studentIdList = this.studentDao.findStudentIdByDormitoryId(id);
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
        Integer delete = this.dormitoryDao.delete(id);
        if(delete!=1) throw new RuntimeException("宿舍删除失败");
    }

    @Override
    public List<Dormitory> findByBuildingId(Integer id) {
        return this.dormitoryDao.findByBuildingId(id);
    }
}
