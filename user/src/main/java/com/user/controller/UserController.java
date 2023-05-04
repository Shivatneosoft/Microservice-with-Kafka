package com.user.controller;

import com.user.ResponseBean.ResponseBean;
import com.user.dto.request.UserRequestBean;
import com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseBean addUser(@RequestBody UserRequestBean user){
            return userService.addUser(user);
    }

    @GetMapping("/")
    public ResponseBean findAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseBean findUserById(@PathVariable("userId") Long userId){
        return userService.findUserById(userId);
    }

    @PutMapping("/")
    public ResponseBean updateUser(@RequestBody UserRequestBean userRequestBean){
        return userService.updateUser(userRequestBean);
    }

    @DeleteMapping("/")
    public void deleteUser(@RequestBody Long userId){
        userService.deleteUser(userId);
    }
}
