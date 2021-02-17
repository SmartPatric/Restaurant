package com.my.restaurant.models;

public class OrdersDishes {

    Integer orderId;
    Integer dishId;
    Integer amount;
    Double total_dish;

    public OrdersDishes(Integer orderId, Integer dishId, Integer amount) {
        this.orderId = orderId;
        this.dishId = dishId;
        this.amount = amount;
    }

    public OrdersDishes() {
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getTotal_dish() {
        return total_dish;
    }

    public void setTotal_dish(Double total_dish) {
        this.total_dish = total_dish;
    }
}
