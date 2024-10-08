package com.test.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyMessage implements Serializable {
    private static final long serialVersionUID = 1L;


    private String message;
}
