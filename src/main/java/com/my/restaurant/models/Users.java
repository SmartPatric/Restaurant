package com.my.restaurant.models;

public class Users {

    Integer id;
    String login;
    String password;
    boolean active = true;
    String role = Role.USER.toString();

    public Users(){

    }

    public Users(String email, String password){
        this.login = email;
        this.password = password;
    }

    public Users(Integer id, String email, String password, boolean active, String role) {
        this.id = id;
        this.login = email;
        this.password = password;
        this.active = active;
        this.role = role;
    }

    public String getEmail() {
        return login;
    }

    public void setEmail(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
