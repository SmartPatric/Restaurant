package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.DishesDao;
import com.my.restaurant.models.Dishes;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Main page command
 * @brief page with dish list
 * pagination is implemented through page
 * sort is implemented through sortField, sortDir
 * categories are implemented through choose
 * @author - Mariia Shaiko
 * @version - 1.0
 */

public class MainPageCommand implements Command {
    static final Logger logger = LogManager.getLogger(MainPageCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("\nStart main page command");

        int page = 1;
        int recordsPerPage = 9;
        String sortField = "name";
        String sortDir = "asc";
        String choose = "all";

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
            logger.info("   -page " + page);
        }
        if (request.getParameter("sortField") != null) {
            sortField = request.getParameter("sortField");
            logger.info("   -sort field " + sortField);
        }
        if (request.getParameter("sortDir") != null) {
            sortDir = request.getParameter("sortDir");
            logger.info("   -sort dir " + sortDir);
        }
        if (request.getParameter("choose") != null) {
            choose = request.getParameter("choose");
            logger.info("   -category " + choose);
        }

        DishesDao dishesDao = new DishesDao();
        List<Dishes> dishesAll = dishesDao.findAllDishes();

        if (!choose.equals("all")) {
            String finalChoose = choose;
            dishesAll = dishesAll.stream().filter(dish -> dish.getCategory().toString().equals(finalChoose.toUpperCase())).collect(Collectors.toList());
        }

        int noOfRecords = dishesAll.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        Comparator<Dishes> comparator = sortField.equals("price") ? Comparator.comparing(Dishes::getPrice) : Comparator.comparing(Dishes::getName);
        if (sortDir.equals("desc")) {
            comparator = comparator.reversed();
        }
        List<Dishes> dishes = dishesAll.stream()
                .sorted(comparator)
                .skip((long) (page - 1) * recordsPerPage)
                .limit(recordsPerPage)
                .collect(Collectors.toList());

        request.setAttribute("dishesList", dishes);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortDir", sortDir);
        request.setAttribute("sortDirReversed", sortDir.equals("asc") ? "desc" : "asc");
        request.setAttribute("choose", choose);
        logger.info("End main page command\n");

        return "/main.jsp";
    }
}
