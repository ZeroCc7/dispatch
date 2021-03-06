package com.wlwx.dispatch.mapper.EFDMapper;

import com.wlwx.dispatch.entity.dispatch.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EFDDispatchMapper {
    /**
     * 获取所有待发送任务
     * @return
     */
    List<Smdown> getAllWaitSendSmsTask();

    void initTaskStatus();

    void updateTaskStatusToEnd(Smdown sm);

    void insertEfdSmrpt(EfdSmrpt efdSmrpt);

    void updateSubmitEfdRpt(EfdSmrpt efdSmrpt);

    void updateReportEfdRpt(EfdSmrpt efdSmrpt);

    void updateSmdownStatus(Smdown smdown);

    void insertEfdSmup(EfdSmup efdSmup);

}
