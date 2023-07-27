package com.southwind.controller;

import com.southwind.entity.DormitoryAdmin;
import com.southwind.service.DormitoryAdminService;
import com.southwind.service.impl.DormitoryAdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dormitoryAdmin")
public class DormitoryAdminServlet extends HttpServlet {
    private DormitoryAdminService dormitoryAdminService=new DormitoryAdminServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //只一次有效
        //req.setAttribute("list", this.dormitoryAdminService.list());
       // req.getRequestDispatcher("adminmanager.jsp").forward(req, resp);
        //在session结束内有效。
//        req.getSession().setAttribute("list",this.dormitoryAdminService.list());
//        resp.sendRedirect("adminmanager.jsp");
        //设置编码
        req.setCharacterEncoding("utf-8");
        String method = req.getParameter("method");
        switch (method){
            case "list":
                req.setAttribute("list", this.dormitoryAdminService.list());
                req.getRequestDispatcher("adminmanager.jsp").forward(req, resp);
                break;
            case "search":
                String key = req.getParameter("key");
                String value = req.getParameter("value");
                req.setAttribute("list",this.dormitoryAdminService.search(key,value));
                req.getRequestDispatcher("adminmanager.jsp").forward(req,resp);
                break;
            case "save":
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                String name = req.getParameter("name");
                String gender = req.getParameter("gender");
                String telephone = req.getParameter("telephone");
                this.dormitoryAdminService.save(new DormitoryAdmin(username,password,name,gender,telephone));
                //username字符不要太长
                resp.sendRedirect("/dormitoryAdmin?method=list");
                break;
            case "update":
                String idStr= req.getParameter("id");
                Integer id = Integer.parseInt(idStr);
                username = req.getParameter("username");
                password = req.getParameter("password");
                name = req.getParameter("name");
                gender = req.getParameter("gender");
                telephone = req.getParameter("telephone");
                this.dormitoryAdminService.update(new DormitoryAdmin(id,username,password,name,gender,telephone));
                //username字符不要太长
                resp.sendRedirect("/dormitoryAdmin?method=list");
                break;
            case "delete":
                idStr= req.getParameter("id");
                id = Integer.parseInt(idStr);
                this.dormitoryAdminService.delete(id);
                //username字符不要太长
                resp.sendRedirect("/dormitoryAdmin?method=list");
                break;
        }
    }
}