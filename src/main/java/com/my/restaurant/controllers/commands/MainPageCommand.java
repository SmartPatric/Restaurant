package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.DishesDao;
import com.my.restaurant.models.Dishes;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MainPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        int page = 1;
        int recordsPerPage = 9;
        String sortField = "name";
        String sortDir = "asc";
        String choose = "all";

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        if (request.getParameter("sortField") != null) {
            sortField = request.getParameter("sortField");
        }
        if (request.getParameter("sortDir") != null) {
            sortDir = request.getParameter("sortDir");
        }
        if (request.getParameter("choose") != null) {
            choose = request.getParameter("choose");
        }

        DishesDao dishesDao = new DishesDao();
        List<Dishes> dishesAll = dishesDao.findAllDishes();

        if (!choose.equals("all")) {
            String finalChoose = choose;
            System.out.println("inside choose " + choose);
            List<Dishes> chosenDishes = dishesAll.stream().filter(dish -> dish.getCategory().toString().equals(finalChoose.toUpperCase())).collect(Collectors.toList());
            dishesAll = chosenDishes;
            for (Dishes d : dishesAll) {
                System.out.println(d.getName());
            }
        }

        int noOfRecords = dishesAll.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        Comparator<Dishes> comparator = sortField.equals("price") ? Comparator.comparing(Dishes::getPrice) : Comparator.comparing(Dishes::getName);
        if (sortDir.equals("desc")) {
            comparator = comparator.reversed();
        }
        List<Dishes> dishes = dishesAll.stream()
                .sorted(comparator)
                .skip((page - 1) * recordsPerPage)
                .limit(recordsPerPage)
                .collect(Collectors.toList());

        request.setAttribute("dishesList", dishes);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortDir", sortDir);
        request.setAttribute("sortDirReversed", sortDir.equals("asc") ? "desc" : "asc");
        request.setAttribute("choose", choose);

        return "/main.jsp";
    }
}
