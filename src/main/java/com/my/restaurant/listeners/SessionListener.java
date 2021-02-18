package com.my.restaurant.listeners;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        System.out.println("////listener");
        HashSet<String> loggedUsers = (HashSet<String>) httpSessionEvent
                .getSession().getServletContext()
                .getAttribute("loggedUsers");
        for (String s : loggedUsers) {
            System.out.println(s);
        }
        String userName = (String) httpSessionEvent.getSession()
                .getAttribute("userName");
        System.out.println("///username " + userName);
        loggedUsers.remove(userName);
        System.out.println("////after remove");
        for (String s : loggedUsers) {
            System.out.println(s);
        }
        httpSessionEvent.getSession().setAttribute("loggedUsers", loggedUsers);
        System.out.println("////end listener");

    }
}
