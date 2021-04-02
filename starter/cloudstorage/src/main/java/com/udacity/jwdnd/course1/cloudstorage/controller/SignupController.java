package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private UserService userService;
    public SignupController(UserMapper userMapper, UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String viewSignupPage(){
        return "signup";
    }
    @PostMapping()
    public String signup(@ModelAttribute("user") User user, Model model) {
        String signupError = null;
        if(!userService.isUsernameAvailable(user.getUsername()))
            signupError = "Username already exists";
        if(signupError == null) {
            int userId = userService.createUser(user);
            if (userId < 0)
                signupError = "Signup failed, there were something wrong!";
        }
        if(signupError == null){
            model.addAttribute("success",true);
            //go to login page after successfully signup
            return "login";
        }else
            model.addAttribute("signupError", signupError);
        //stay at signup page if signup failed
        return "signup";
    }
}
