package com.avaloq.api.model.request;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class RollRequest {
    @Min(value = 4, message = "The sides of a dice should not be less than 4")
    private int sides;
    @Min(value = 1, message = "The number of dices must be at least 1")
    private int dices;
    @Min(value = 1, message = "The total number of rolls must be at least 1")
    private int rolls;
}
