package com.wlwx.dispatch.controller;

import com.wlwx.dispatch.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


@Controller
public class LoginController {

    @RequestMapping("/")
    public String login() {
        return "login";
    }

    @RequestMapping("/login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("index")
    public String GetUserList(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "index";
    }
}
