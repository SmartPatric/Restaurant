package com.my.restaurant.listeners;

import com.my.restaurant.models.Dishes;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

        List<Dishes> dishesList = new ArrayList<>();
        httpSessionEvent
                .getSession().getServletContext()
                .setAttribute("dishesList", dishesList);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        System.out.println("////listener");
        HashSet<String> loggedUsers = (HashSet<String>) httpSessionEvent
                .getSession().getServletContext()
                .getAttribute("loggedUsers");

        String userName = (String) httpSessionEvent.getSession()
                .getAttribute("userName");
        loggedUsers.remove(userName);

        httpSessionEvent.getSession().setAttribute("loggedUsers", loggedUsers);
    }
}
