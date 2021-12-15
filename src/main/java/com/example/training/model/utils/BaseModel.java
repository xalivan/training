package com.example.training.model.utils;

import java.time.LocalDateTime;

public class BaseModel {
    int id;
    LocalDateTime date;
    int length;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public BaseModel(int id, LocalDateTime date, int length) {
        this.id = id;
        this.date = date;
        this.length = length;
    }
}
