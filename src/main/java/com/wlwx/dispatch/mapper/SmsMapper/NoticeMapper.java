package com.wlwx.dispatch.mapper.SmsMapper;

import com.wlwx.dispatch.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    /**
     * 获取公告
     * @return
     */
    List<Notice> getNotice();
}
