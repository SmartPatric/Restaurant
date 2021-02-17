package com.my.restaurant.models;

import java.time.LocalDateTime;

public class Orders {

    Integer id;
    String status = Status.MAKING.toString();
    Integer userId;
    LocalDateTime creationDate = LocalDateTime.now();
    LocalDateTime updateDate = LocalDateTime.now();
    Double total;

    public Orders(Integer id, String status, Integer userId, LocalDateTime creationDate, LocalDateTime updateDate, Double total) {
        this.id = id;
        this.status = status;
        this.userId = userId;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.total = total;
    }

    public Orders() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
