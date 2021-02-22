package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.UsersDao;
import com.my.restaurant.models.Role;
import com.my.restaurant.models.Users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginPostCommand implements Command{

    private final UsersDao usersDao = new UsersDao();

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("login");
        String password = request.getParameter("password");

        //System.out.println("Login " + name + " " + password);
        if (name == null || name.equals("") || password == null || password.equals("")) {
            request.setAttribute("loginStatus", "Enter login and password");
            return "redirect:/login?status=noData";
        }

        if (CommandUtility.checkUserIsLogged(request, name)) {
            return "redirect:/login?status=logged";
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
                return "redirect:/login?status=blocked";
            }
            return "redirect:/main";
        } else {
            CommandUtility.setUserRole(request, Role.UNKNOWN, name);
            return "redirect:/login?status=no";
        }
    }
}
