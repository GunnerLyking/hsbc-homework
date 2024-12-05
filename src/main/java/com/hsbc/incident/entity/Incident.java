package com.hsbc.incident.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Incident {
    private int id;

    private Date createTime;

    private String type;

    private String status;

    private String msg;
}
