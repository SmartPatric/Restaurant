package com.my.restaurant.filters;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        //this.context.log("Requested Resource::" + uri);
        HttpSession session = req.getSession(false);
        Object role = session.getAttribute("role");
        //this.context.log("Фильтр аутентификации, пользователь::" + role);

        if (role != null && role.toString().equals("USER") && !(uri.endsWith("admin"))) {
            //this.context.log("Авторизованный запрос, сессия:: " + session);
            System.out.println("do auth user");
            chain.doFilter(request, response);
            res.sendRedirect("/userCabinet");
        } else if (role != null && role.toString().equals("ADMIN") && !(uri.endsWith("userCabinet"))) {
            System.out.println("do auth admin");
            chain.doFilter(request, response);
            res.sendRedirect("/admin");
        } else {
            System.out.println("not do auth");
            //this.context.log("Неавторизованный запрос");
            res.sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {
        //close any resources here
    }
}
