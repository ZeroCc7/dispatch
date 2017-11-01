package com.wlwx.dispatch.controller;

import com.wlwx.dispatch.entity.Notice;
import com.wlwx.dispatch.entity.SocketMessage;
import com.wlwx.dispatch.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;

    @RequestMapping("/index/message/messages")
    public String loadMessage(Model model){
        List<Notice> noticeList =messageService.getTopMessage();
        model.addAttribute("noticeList",noticeList);
        return "message/messageTab";

    }


}
