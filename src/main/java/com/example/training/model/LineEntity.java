package com.example.training.model;

import com.example.training.model.utils.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Value;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Value
public class LineEntity extends BaseEntity {

    String coordinates;

    public LineEntity(int id, LocalDateTime date, int length, String coordinates) {
        super(id, date, length);
        this.coordinates = coordinates;
    }
}