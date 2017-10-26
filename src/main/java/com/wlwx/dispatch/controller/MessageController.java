package com.wlwx.dispatch.controller;

import com.wlwx.dispatch.entity.Notice;
import com.wlwx.dispatch.entity.SocketMessage;
import com.wlwx.dispatch.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@EnableScheduling
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @RequestMapping("/index/message/messages")
    public String loadMessage(Model model){
        System.out.println("getNotice");
        List<Notice> noticeList =messageService.getTopMessage();
        model.addAttribute("noticeList",noticeList);
        return "message/messageTab";

    }

    @MessageMapping("/send")
    @SendTo("/topic/send")
    public SocketMessage send(SocketMessage message) throws Exception {
        System.out.println(message.getMessage());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.setDate(df.format(new Date())) ;
        return message;
    }

    @Scheduled(fixedRate = 1000)
    @SendTo("/topic/callback")
    public Object callback() throws Exception {
        // 发现消息
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/topic/callback", df.format(new Date()));
        return "callback";
    }

}
