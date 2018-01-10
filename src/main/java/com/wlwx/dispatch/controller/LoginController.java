package com.wlwx.dispatch.controller;

import com.wlwx.dispatch.entity.SmsTask;
import com.wlwx.dispatch.entity.User;
import com.wlwx.dispatch.job.SmsDispatchJob;
import com.wlwx.dispatch.service.SmsTaskService;
import com.wlwx.dispatch.util.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
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

    @RequestMapping("/index")
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        boolean dispatch_status = SmsDispatchJob.getInstance().isState();

        model.addAttribute("dispatch_status",dispatch_status);
        model.addAttribute("dispatchStatrtTime", Statistics.dispatchStatrtTime);
        model.addAttribute("dispatchStopTime",Statistics.dispatchStopTime);
        model.addAttribute("taskTolNum",Statistics.taskTolNum);
        model.addAttribute("tolMobileNum",Statistics.tolMobileNum);
        model.addAttribute("sqlRedisLength",Statistics.sqlRedisLength);
        model.addAttribute("runningNum",Statistics.runningNum);
        model.addAttribute("user", user);
        return "index";
    }
}
