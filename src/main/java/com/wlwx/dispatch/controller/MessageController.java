package com.wlwx.dispatch.controller;

import com.wlwx.dispatch.entity.Notice;
import com.wlwx.dispatch.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;

    @RequestMapping("/index/message/messages")
    public String loadMessage(Model model){
        System.out.println("getNotice");
        List<Notice> noticeList =messageService.getTopMessage();
        model.addAttribute("noticeList",noticeList);
        return "message/messageTab";

    }

}
