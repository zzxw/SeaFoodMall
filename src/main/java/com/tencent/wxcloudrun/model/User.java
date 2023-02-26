package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private String userId;

    private String phoneNumber;

    private String nickName;

}
