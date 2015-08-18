package com.github.bingoohuang.springrest.boot.dto;

import javax.validation.constraints.Size;

public class HelloForm {
    @Size(min = 1)
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
