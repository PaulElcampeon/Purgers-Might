package com.purgersmight.purgersmightapp.models;

import lombok.Data;

@Data
public class AvatarAttribute<T extends Number> {

    private T running;
    private T actual;

    public AvatarAttribute() {
    }

    public AvatarAttribute(T value) {
        this.running = value;
        this.actual = value;
    }

    public AvatarAttribute(T valueR, T valueA) {
        this.running = valueR;
        this.actual = valueA;
    }
}
