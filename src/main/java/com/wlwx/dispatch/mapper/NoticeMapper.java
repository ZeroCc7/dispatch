package com.wlwx.dispatch.mapper;

import com.wlwx.dispatch.entity.Notice;
import com.wlwx.dispatch.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {
    /**
     * 获取公告
     * @return
     */
    List<Notice> getNotice();
}
