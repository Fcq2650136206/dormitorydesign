package com.southwind.controller;

import com.southwind.entity.Dormitory;
import com.southwind.entity.Student;
import com.southwind.service.BuildingService;
import com.southwind.service.DormitoryService;
import com.southwind.service.StudentService;
import com.southwind.service.impl.BuildingServiceImpl;
import com.southwind.service.impl.DormitoryServiceImpl;
import com.southwind.service.impl.StudentServiceImpl;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/dormitory")
public class DormitoryServlet extends HttpServlet {
    private DormitoryService dormitoryService=new DormitoryServiceImpl();
    private BuildingService buildingService=new BuildingServiceImpl();
    private StudentService studentService=new StudentServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String method = req.getParameter("method");
        switch (method) {
            case "list":
                req.setAttribute("list", this.dormitoryService.list());
                req.setAttribute("buildingList",this.buildingService.list());
                req.getRequestDispatcher("dormitorymanager.jsp").forward(req, resp);
                break;
            case "search":
                String key = req.getParameter("key");
                String value = req.getParameter("value");
                req.setAttribute("buildingList",this.buildingService.list());
                req.setAttribute("list", this.dormitoryService.search(key,value));
                req.getRequestDispatcher("dormitorymanager.jsp").forward(req, resp);
                break;
            case "save":
                String buildingIdStr = req.getParameter("buildingId");
                Integer buildingId = Integer.parseInt(buildingIdStr);
                String name = req.getParameter("name");
                String typeStr = req.getParameter("type");
                Integer type = Integer.parseInt(typeStr);
                String telephone = req.getParameter("telephone");
                this.dormitoryService.save(new Dormitory(buildingId,name,type,type,telephone));
                resp.sendRedirect("/dormitory?method=list");
                break;
            case "update":
                String idStr = req.getParameter("id");
                Integer id = Integer.parseInt(idStr);
                name = req.getParameter("name");
                telephone = req.getParameter("telephone");
                this.dormitoryService.update(new Dormitory(id,name,telephone));
                resp.sendRedirect("/dormitory?method=list");
                break;
            case "delete":
                idStr = req.getParameter("id");
                id = Integer.parseInt(idStr);
                this.dormitoryService.delete(id);
                resp.sendRedirect("/dormitory?method=list");
                break;
            case "findByBuildingId":
                buildingIdStr = req.getParameter("buildingId");
                buildingId = Integer.parseInt(buildingIdStr);
                List<Dormitory> dormitoryList = this.dormitoryService.findByBuildingId(buildingId);
                List<Student> studentList = this.studentService.findByDormitoryId(dormitoryList.get(0).getId());
                HashMap<String, List> map= new HashMap<>();
                map.put("dormitoryList",dormitoryList);
                map.put("studentList",studentList);
                JSONArray jsonArray = JSONArray.fromObject(map);
                resp.setContentType("text/json;charset=UTF-8");
                resp.getWriter().write(jsonArray.toString());
              //  System.out.println(jsonArray.toString());
                //学习这自己用debug看json结构
                // [{"dormitoryList":[{"available":0,"buildingId":0,"buildingName":"","id":4,"name":"212","telephone":"","type":0},{"available":0,"buildingId":0,"buildingName":"","id":5,"name":"321","telephone":"","type":0},{"available":0,"buildingId":0,"buildingName":"","id":6,"name":"322","telephone":"","type":0}],
               // "studentList":[{"createDate":"","dormitoryId":0,"dormitoryName":"","gender":"","id":13,"name":"许鹏飞","number":"","state":""},{"createDate":"","dormitoryId":0,"dormitoryName":"","gender":"","id":14,"name":"朱海","number":"","state":""},{"createDate":"","dormitoryId":0,"dormitoryName":"","gender":"","id":15,"name":"苏苏苏","number":"","state":""},{"createDate":"","dormitoryId":0,"dormitoryName":"","gender":"","id":16,"name":"李雪","number":"","state":""},{"createDate":"","dormitoryId":0,"dormitoryName":"","gender":"","id":17,"name":"李爽","number":"","state":""},{"createDate":"","dormitoryId":0,"dormitoryName":"","gender":"","id":18,"name":"王纯","number":"","state":""}]}]
                break;

        }
    }
}
