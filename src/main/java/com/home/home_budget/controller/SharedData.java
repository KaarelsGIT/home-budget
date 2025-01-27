package com.home.home_budget.controller;

import java.util.List;

import com.home.home_budget.Model.User;
import com.home.home_budget.service.UserService;

public abstract class SharedData {

    protected final UserService userService;

    protected SharedData(UserService userService) {
        this.userService = userService;
    }

    protected List<User> getUsers() {
        try {
            return userService.getAllUsers();
        } catch (Exception e) {
            return null;
        }
    }

}
