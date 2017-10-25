package com.wlwx.dispatch.service.impl;

import com.wlwx.dispatch.entity.Notice;
import com.wlwx.dispatch.mapper.NoticeMapper;
import com.wlwx.dispatch.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class MessageServiceImp implements MessageService {

    @Resource
    NoticeMapper noticeMapper;

    @Override
    public List<Notice> getTopMessage() {
        return noticeMapper.getNotice();
    }
}
