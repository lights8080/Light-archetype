package com.light.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class User {

    @NotNull
    private String name;

    private Integer age;

}
