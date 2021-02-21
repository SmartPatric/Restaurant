package com.my.restaurant.controllers.commands;

import com.my.restaurant.dao.OrdersDishesDao;

import javax.servlet.http.HttpServletRequest;

public class ChangeDishAmountCommand implements Command{

    private final OrdersDishesDao ordersDishesDao = new OrdersDishesDao();

    @Override
    public String execute(HttpServletRequest request) {
        String amountChange = request.getParameter("amountChange");
        Integer orderId = Integer.parseInt(request.getParameter("orderId"));
        Integer dishId = Integer.parseInt(request.getParameter("dishId"));

        //System.out.println("amountChange "+amountChange+" dish "+dishId+" order "+orderId);

        if(amountChange.equals("plus")){
            ordersDishesDao.increaseOrderDishAmount(orderId, dishId);
        }
        else if(amountChange.equals("minus") && Integer.parseInt(request.getParameter("dishAmount"))>1){
            ordersDishesDao.decreaseOrderDishAmount(orderId, dishId);
        }
        else if(amountChange.equals("remove")){
            System.out.println("removing dish from order");
            ordersDishesDao.removeOrderDish(orderId, dishId);
        }

        return "changeUser";
    }
}
