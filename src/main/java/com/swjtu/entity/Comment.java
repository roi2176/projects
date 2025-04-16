package com.swjtu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int id;
    private int userId;
    private int entityType;
    private int entityId;
    private String content;
    private int targetId;
    private int status;
    private Date createTime;
}
