package com.example.training.model.utils;

import lombok.*;

import java.time.LocalDateTime;
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    int id;
    LocalDateTime date;
    int length;

}
