package com.test.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class MyObject implements Serializable {
    private int id;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}