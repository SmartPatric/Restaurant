package com.my.restaurant.controllers.commands;

import com.my.restaurant.models.Role;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

/**
 * Utility for setting and checking user role
 * @author - Mariia Shaiko
 * @version - 1.0
 */

class CommandUtility {
    private static final Logger logger = LogManager.getLogger(CommandUtility.class);

    static void setUserRole(HttpServletRequest request,
                            Role role, String name) {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        context.setAttribute("userName", name);
        session.setAttribute("role", role);
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");
        loggedUsers.add(name);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
    }

    static boolean checkUserIsLogged(HttpServletRequest request, String userName){
        logger.info("Checking user logged");

        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");
        if(loggedUsers.stream().anyMatch(userName::equals)){
            return true;
        }
        logger.info("Logged users "+loggedUsers);
        return false;
    }
}