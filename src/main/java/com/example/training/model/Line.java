package com.example.training.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.Value;

import java.sql.Date;

@Value
public class Line {

    int id;
    Date creation_date;
    int length;
    String geom;

    @JsonCreator
    public Line(@JsonProperty("id") int id,
                @JsonProperty("creation_date") Date creation_date,
                @JsonProperty("length") int length,
                @JsonProperty("geom") @NonNull String geom) {
        this.id = id;
        this.creation_date = creation_date;
        this.length = length;
        this.geom = geom;
    }
}