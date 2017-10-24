package com.wlwx.dispatch.controller;

import com.wlwx.dispatch.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/userlist")
    public String userList(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        return "user/userlist";
    }
}
