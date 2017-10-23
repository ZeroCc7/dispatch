package com.wlwx.dispatch.controller;

import com.sun.xml.internal.txw2.output.ResultFactory;
import com.wlwx.dispatch.entity.Person;
import com.wlwx.dispatch.util.ResultInfo;
import com.wlwx.dispatch.util.ResultInfoFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class LoginController {

    @RequestMapping("/")
    public String login() {
        return "login";
    }

    @RequestMapping("index")
    public String GetUserList() {
        return "index";
    }
}
