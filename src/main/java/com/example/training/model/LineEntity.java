package com.example.training.model;

import com.example.training.model.utils.BaseLine;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class LineEntity extends BaseLine {

    private final String coordinates;

    public LineEntity(int id, LocalDateTime date, int length, String coordinates) {
        super(id, date, length);
        this.coordinates = coordinates;
    }
}