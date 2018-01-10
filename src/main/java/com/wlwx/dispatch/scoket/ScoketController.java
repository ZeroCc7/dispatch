package com.wlwx.dispatch.scoket;

import com.wlwx.dispatch.entity.SocketMessage;
import com.wlwx.dispatch.util.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@EnableScheduling
public class ScoketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Scheduled(fixedRate = 1000)
    @SendTo("/topic/dispatchstatus")
    public Object dispatchstatus() throws Exception {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("dispatchStatrtTime", Statistics.dispatchStatrtTime);
        params.put("dispatchStopTime",Statistics.dispatchStopTime);
        params.put("taskTolNum",Statistics.taskTolNum);
        params.put("tolMobileNum",Statistics.tolMobileNum);
        params.put("sqlRedisLength",Statistics.sqlRedisLength);
        params.put("runningNum",Statistics.runningNum);

        messagingTemplate.convertAndSend("/topic/dispatchstatus", params);
        return "dispatchstatus";
    }
}

