package com.wlwx.dispatch.controller;

import com.wlwx.dispatch.entity.SmsTask;
import com.wlwx.dispatch.entity.User;
import com.wlwx.dispatch.service.SmsTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;


@Controller
public class LoginController {

    @Autowired
    SmsTaskService smsTaskService;

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
        List<SmsTask> smsTasks = smsTaskService.getTodaySmsTasks();
        int tolTaskNum = smsTasks.size();
        int tolSuccessNum = 0;
        int tolFailNum = 0;
        int tolSendEdNum = 0;
        for (SmsTask smsTask : smsTasks){
            tolSuccessNum += smsTask.getSuccess_num();
            tolFailNum += smsTask.getFail_num();
            tolSendEdNum += smsTask.getSended_num();
        }
        model.addAttribute("tolTaskNum",tolTaskNum);
        model.addAttribute("tolSuccessNum",tolSuccessNum);
        model.addAttribute("tolFailNum",tolFailNum);
        model.addAttribute("tolSendEdNum",tolSendEdNum);
        model.addAttribute("user", user);
        return "index";
    }
}
