package com.wlwx.dispatch.controller;

import com.wlwx.dispatch.entity.DayNum;
import com.wlwx.dispatch.entity.SmsTask;
import com.wlwx.dispatch.job.SmsDispatchJob;
import com.wlwx.dispatch.mapper.SmsTaskMapper;
import com.wlwx.dispatch.service.SmsTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/smstask")
public class SmsTaskController {

    @Autowired
    SmsTaskService smsTaskService;

    private SmsDispatchJob smsDispatchJob;

    /**
     * 查询当月分组数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMonthlySmsTask")
    public List<DayNum> getMonthlySmsTask(){
        List<DayNum> dayNums = smsTaskService.getMonthlySmsTasks();
        return dayNums;
    }

    /**
     * 获取短信群发任务
     * @return
     */
    @RequestMapping("/smstasklist")
    public String getSmsTaskList(Model model){
        List<SmsTask> smsTasks = smsTaskService.getTodaySmsTasks();
        model.addAttribute("smsTasks",smsTasks);
        return "smstask/smstasklist";
    }

    /**
     * 启动发送线程
     * @return
     */
    //TODO
    @ResponseBody
    @RequestMapping("/startSendDispatch")
    public String startSendDispatch(Model model){
        if(smsDispatchJob == null){
            smsDispatchJob = new SmsDispatchJob();
            smsDispatchJob.startDispatch();
        }else{
            if(smsDispatchJob.isState()){
                return "working...";
            }else{
                smsDispatchJob.startDispatch();
            }
        }
        return "success";
    }

    /**
     * 停止发送线程
     * @return
     */
    @ResponseBody
    @RequestMapping("/stopSendDispatch")
    public String stopSendDispatch(Model model){
        if(smsDispatchJob == null){
            return "Not Working...";
        }else{
            if(smsDispatchJob.isState()){
                smsDispatchJob.shutdownDispatch();
                return "success";
            }else{
                return "Not Working...";
            }
        }
    }
}
