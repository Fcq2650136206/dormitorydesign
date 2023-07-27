package com.southwind.service.impl;

import com.southwind.dao.DormitoryDao;
import com.southwind.dao.StudentDao;
import com.southwind.dao.impl.DormitoryDaoImpl;
import com.southwind.dao.impl.StudentDaoImpl;
import com.southwind.entity.Student;
import com.southwind.service.StudentService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private StudentDao studentDao=new StudentDaoImpl();
    private DormitoryDao dormitoryDao=new DormitoryDaoImpl();
    @Override
    public List<Student> list() {
        return this.studentDao.list();
    }

    @Override
    public List<Student> search(String key, String value) {
        if(value.equals("")) return this.studentDao.list();
        return this.studentDao.search(key,value);
    }

    @Override
    public void save(Student student) {
        //加状态什么的
        student.setState("入住");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        student.setCreateDate(format);
        Integer save = this.studentDao.save(student);
        Integer integer = this.dormitoryDao.subAvailable(student.getDormitoryId());
        if(save!=1 || integer!=1) throw new RuntimeException("保存失败");
    }

    @Override
    public void update(Student student, Integer oldDormitoryId) {
        //老id加1,新id-1
        Integer update=this.studentDao.update(student);
        Integer integer1 = this.dormitoryDao.subAvailable(student.getDormitoryId());
        Integer integer = this.dormitoryDao.addAvailable(oldDormitoryId);
        if(update!=1 || integer !=1 || integer1!=1) throw new RuntimeException("更新失败");
    }

    @Override
    public void delete(int id, Integer dormitoryId) {
        Integer delete = this.studentDao.delete(id);
        Integer integer = this.dormitoryDao.addAvailable(dormitoryId);
        if(delete!=1||integer!=1) throw new RuntimeException("删除失败");
    }

    @Override
    public List<Student> moveoutList() {
        return this.studentDao.moveoutList();
    }

    @Override
    public List<Student> searchForMoveout(String key, String value) {
        if(value.equals("")) return  this.studentDao.list();
        return this.studentDao.searchForMoveout(key,value);
    }

    @Override
    public List<Student> findByDormitoryId(Integer id) {
        return this.studentDao.findByDormitoryId(id);
    }
}
