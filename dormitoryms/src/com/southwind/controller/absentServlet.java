package com.southwind.controller;

import com.southwind.entity.*;
import com.southwind.service.AbsentService;
import com.southwind.service.BuildingService;
import com.southwind.service.DormitoryService;
import com.southwind.service.StudentService;
import com.southwind.service.impl.AbsentServiceImpl;
import com.southwind.service.impl.BuildingServiceImpl;
import com.southwind.service.impl.DormitoryServiceImpl;
import com.southwind.service.impl.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/absent")
public class absentServlet extends HttpServlet {
    private BuildingService buildingService=new BuildingServiceImpl();
    private DormitoryService dormitoryService=new DormitoryServiceImpl();
    private StudentService studentService=new StudentServiceImpl();
    private AbsentService absentService=new AbsentServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String method = req.getParameter("method");
        switch (method) {
            case "init":
                //显示楼宇,宿舍和学生都是第一个元素
                List<Building> buildingList = this.buildingService.list();//默认就显示第一个楼宇
                List<Dormitory> dormitoryList = this.dormitoryService.findByBuildingId(buildingList.get(0).getId());
                List<Student> studentList = this.studentService.findByDormitoryId(dormitoryList.get(0).getId());
                req.setAttribute("buildingList",buildingList);
                req.setAttribute("dormitoryList",dormitoryList);
                req.setAttribute("studentList",studentList);
                req.getRequestDispatcher("/absentregister.jsp").forward(req,resp);
                break;
            case "save":
                String buildingIdStr = req.getParameter("buildingId");
                Integer buildingId = Integer.parseInt(buildingIdStr);
                String dormitoryIdStr = req.getParameter("dormitoryId");
                Integer dormitoryId = Integer.parseInt(dormitoryIdStr);
                String studentIdStr = req.getParameter("studentId");
                Integer studentId = Integer.parseInt(studentIdStr);
                String date = req.getParameter("date");
                String reason = req.getParameter("reason");
                HttpSession session = req.getSession();
                DormitoryAdmin dormitoryAdmin = (DormitoryAdmin) session.getAttribute("dormitoryAdmin");
                this.absentService.save(new Absent(buildingId,dormitoryId,studentId,dormitoryAdmin.getId(),date,reason));
                resp.sendRedirect("/absent?method=init");
                break;
            case "list":
                req.setAttribute("list",this.absentService.list());
                req.getRequestDispatcher("absentrecord.jsp").forward(req,resp);
                break;
            case  "search":
                String key = req.getParameter("key");
                String value = req.getParameter("value");
                req.setAttribute("list",this.absentService.search(key,value));
                req.getRequestDispatcher("absentrecord.jsp").forward(req,resp);
                break;
        }
    }
}
