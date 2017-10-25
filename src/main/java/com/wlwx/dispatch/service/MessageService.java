package com.wlwx.dispatch.service;

import com.wlwx.dispatch.entity.Notice;

import java.util.List;

public interface MessageService {
    List<Notice> getTopMessage();
}
