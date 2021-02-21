package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.UsersDao;
import com.my.restaurant.models.Role;
import com.my.restaurant.models.Users;
import com.mysql.cj.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {

    private final UsersDao usersDao = new UsersDao();

    @Override
    public String execute(HttpServletRequest request) {
        String post = request.getParameter("post");

        if (post != null) {
            String name = request.getParameter("login");
            String password = request.getParameter("password");

            System.out.println("Login " + name + " " + password);
            if (name == null || name.equals("") || password == null || password.equals("")) {
                request.setAttribute("loginStatus", "Enter login and password");
                return "/login.jsp";
            }

            if (CommandUtility.checkUserIsLogged(request, name)) {
                request.setAttribute("loginStatus", "User already logged");
                return "/login.jsp";
            }

            Users user = usersDao.validate(name, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userName", user.getEmail());
                session.setAttribute("userId", user.getId());
                session.setAttribute("userRole", user.getRole());
                session.setAttribute("userActive", user.isActive());
                System.out.println("is active "+user.isActive());
                if (user.getRole().equals("ADMIN") && user.isActive()) {
                    System.out.println("login as admin");
                    CommandUtility.setUserRole(request, Role.ADMIN, name);
                } else if (user.getRole().equals("USER") && user.isActive()) {
                    System.out.println("login as user");
                    CommandUtility.setUserRole(request, Role.USER, name);
                } else {
                    System.out.println("user blocked");
                    request.setAttribute("loginStatus", "User is blocked");
                    return "/login.jsp";
                }
                return "main";
            } else {
                CommandUtility.setUserRole(request, Role.UNKNOWN, name);
                request.setAttribute("loginStatus", "Login or password is incorrect");
                return "/login.jsp";
            }
        }
        return "/login.jsp";
    }
}
