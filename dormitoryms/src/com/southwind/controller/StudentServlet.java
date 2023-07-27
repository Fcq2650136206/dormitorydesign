package com.southwind.controller;

import com.southwind.dao.DormitoryDao;
import com.southwind.dao.impl.DormitoryDaoImpl;
import com.southwind.entity.Student;
import com.southwind.service.StudentService;
import com.southwind.service.impl.StudentServiceImpl;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    private StudentService studentService=new StudentServiceImpl();
    private DormitoryDao dormitoryDao=new DormitoryDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    /**
     * 实现学生的添加就要,先判断那个宿舍多床位
     * 其二就是要首页和查询页面已经知道多余宿舍
     * 其三就是加完后要加一个位置
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String method = req.getParameter("method");
        switch (method){
            case "list":
                req.setAttribute("list", this.studentService.list());
                req.setAttribute("dormitoryList",this.dormitoryDao.availableList());
                req.getRequestDispatcher("studentmanager.jsp").forward(req, resp);
                break;
            case "search":
                String key = req.getParameter("key");
                String value = req.getParameter("value");
                req.setAttribute("list", this.studentService.search(key,value));
                req.setAttribute("dormitoryList",this.dormitoryDao.availableList());
                req.getRequestDispatcher("studentmanager.jsp").forward(req, resp);
                break;
            case "save":
                //对面只传过来四个字段
                String dormitoryIdStr = req.getParameter("dormitoryId");
                Integer dormitoryId = Integer.parseInt(dormitoryIdStr);
                String number = req.getParameter("number");
                String name = req.getParameter("name");
                String gender = req.getParameter("gender");
                this.studentService.save(new Student(number,name,gender,dormitoryId));
                resp.sendRedirect("/student?method=list");
                break;
            case "update":
                String strId = req.getParameter("id");
                int id = Integer.parseInt(strId);
                dormitoryIdStr = req.getParameter("dormitoryId");
                dormitoryId = Integer.parseInt(dormitoryIdStr);
                number = req.getParameter("number");
                name = req.getParameter("name");
                gender = req.getParameter("gender");
                String oldDormitoryIDStr = req.getParameter("oldDormitoryId");
                Integer oldDormitoryId = Integer.parseInt(oldDormitoryIDStr);
                this.studentService.update(new Student(id,number,name,gender,dormitoryId),oldDormitoryId);
                resp.sendRedirect("/student?method=list");
                break;
            case "delete":
                String idStr = req.getParameter("id");
                id = Integer.parseInt(idStr);
                dormitoryIdStr = req.getParameter("dormitoryId");
                dormitoryId = Integer.parseInt(dormitoryIdStr);
                this.studentService.delete(id,dormitoryId);
                resp.sendRedirect("/student?method=list");
                break;
            case "findByDormitoryId":
                dormitoryIdStr = req.getParameter("dormitoryId");
                dormitoryId = Integer.parseInt(dormitoryIdStr);
                List<Student> studentList = this.studentService.findByDormitoryId(dormitoryId);
                JSONArray jsonArray = JSONArray.fromObject(studentList);
                resp.setContentType("text/json;charset=UTF-8");
                resp.getWriter().write(jsonArray.toString());
                System.out.println(jsonArray.toString());
                //[{"createDate":"","dormitoryId":0,"dormitoryName":"","gender":"","id":5,"name":"王维利","number":"","state":""},{"createDate":"","dormitoryId":0,"dormitoryName":"","gender":"","id":6,"name":"李双","number":"","state":""},{"createDate":"","dormitoryId":0,"dormitoryName":"","gender":"","id":7,"name":"李小峰","number":"","state":""},{"createDate":"","dormitoryId":0,"dormitoryName":"","gender":"","id":8,"name":"孙奇","number":"","state":""}]
                break;
        }
    }
}
