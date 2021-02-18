package com.my.restaurant.controllers;

import com.my.restaurant.controllers.commands.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Servlet extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig) {
        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());
        commands.put("logout",
                new LogOutCommand());
        commands.put("login",
                new LoginCommand());
        commands.put("exception", new ExceptionCommand());
        commands.put("userCabinet", new UserCommand());
        commands.put("userCabinetPost", new UserCommandPost());
        commands.put("admin", new AdminCommand());
        commands.put("adminPost", new AdminCommandPost());
        commands.put("registration", new RegistrationCommand());
        commands.put("main", new MainPageCommand());
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
        //response.getWriter().print("Hello from servlet");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        System.out.println("request URI " + path);
        if (!path.contains(".css") && !path.contains(".png")) {
            path = path.replaceAll("/", "");
        }
        System.out.println("request URI after " + path);

        Command command = commands.getOrDefault(path,
                new MainPageCommand());
        System.out.println(command.getClass().getName());
        String page = command.execute(request);
        //request.getRequestDispatcher(page).forward(request,response);
        if (page.contains("/admin") || page.contains("/user")) {
            response.sendRedirect(page);
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
