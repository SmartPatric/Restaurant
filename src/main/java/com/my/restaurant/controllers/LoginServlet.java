package com.my.restaurant.controllers;

import com.my.restaurant.dao.UsersDao;
import com.my.restaurant.models.Users;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("login");
        String password = request.getParameter("password");

        System.out.println("Login"+email+" "+password);

        HttpSession session = request.getSession(false);

        UsersDao usersDao = new UsersDao();
        Users user = usersDao.validate(email, password);

        if(user!=null){
            session.setAttribute("role", user.getRole());
            session.setAttribute("name", user.getEmail());
            session.setAttribute("userId", user.getId());
            response.sendRedirect("/index.jsp");
        }
        else{
            //out.print("<p style='color:red'>Username or password is wrong</p>");
            response.sendRedirect(request.getContextPath()+"/login");
        }
/*        Object role = request.getSession().getAttribute("role");
        System.out.println("Role: "+role.toString());*/

    }
}
