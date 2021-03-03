package com.my.restaurant.controllers;

import com.my.restaurant.controllers.commands.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Main servlet that contains mapping for commands
 *
 * @author - Mariia Shaiko
 * @version - 1.0
 */

public class Servlet extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>();
    private static final Logger logger = LogManager.getLogger(Servlet.class);

    public void init(ServletConfig servletConfig) {
        servletConfig.getServletContext().setAttribute("loggedUsers", new HashSet<String>());
        commands.put("logout", new LogOutCommand());
        commands.put("login", new LoginCommand());
        commands.put("loginPost", new LoginPostCommand());
        commands.put("exception", new ExceptionCommand());
        commands.put("userCabinet", new UserCommand());
        commands.put("userCabinetPost", new UserCommandPost());
        commands.put("admin", new AdminCommand());
        commands.put("adminPost", new AdminCommandPost());
        commands.put("registration", new RegistrationCommand());
        commands.put("registrationPost", new RegistrationPostCommand());
        commands.put("main", new MainPageCommand());
        commands.put("amountChange", new ChangeDishAmountCommand());
        commands.put("userCancelOrder", new UserCancelOrderCommand());
        commands.put("userCabinetPay", new PayOrderCommand());

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Servlet start");

        String path = request.getRequestURI();
        logger.info("request URI " + path);

        path = path.replaceAll("restaurant", "");
        path = path.replaceAll("/", "");

        Command command = commands.getOrDefault(path,
                new MainPageCommand());
        String page = command.execute(request);

        if (page.contains("redirect:")) {
            page = page.replaceAll("redirect:", "");
            logger.info("redirect to page " + page);
            response.sendRedirect("/restaurant" + page);
        } else {
            logger.info("forward to page " + page);
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
