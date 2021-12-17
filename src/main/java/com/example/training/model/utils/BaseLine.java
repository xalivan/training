package com.example.training.model.utils;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class BaseLine {
    private final int id;
    private final LocalDateTime date;
    private final int length;

}
