package com.southwind.service.impl;

import com.southwind.dao.DormitoryDao;
import com.southwind.dao.MoveoutDao;
import com.southwind.dao.StudentDao;
import com.southwind.dao.impl.DormitoryDaoImpl;
import com.southwind.dao.impl.MoveoutDaoImpl;
import com.southwind.dao.impl.StudentDaoImpl;
import com.southwind.entity.Moveout;
import com.southwind.service.MoveoutService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MoveoutServiceImpl implements MoveoutService {
    private MoveoutDao moveoutDao=new MoveoutDaoImpl();
    private DormitoryDao dormitoryDao=new DormitoryDaoImpl();
    private StudentDao studentDao=new StudentDaoImpl();
    @Override
    public void save(Moveout moveout) {
        //先把员宿舍床位加1
        Integer integer = this.dormitoryDao.addAvailable(moveout.getDormitoryId());
        //把状态改为迁出
        Integer integer1 = this.studentDao.stateForMoveout(moveout.getStudentId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        moveout.setCreateDate(format);
        Integer save = this.moveoutDao.save(moveout);
        if(save!=1 || integer!=1 || integer1!=1) throw new RuntimeException("迁出失败");
    }

    @Override
    public List<Moveout> list() {
        return this.moveoutDao.list();
    }

    @Override
    public List<Moveout> search(String key, String value) {
        if(value.equals("")) return this.moveoutDao.list();
        return this.moveoutDao.search(key,value);
    }
}
