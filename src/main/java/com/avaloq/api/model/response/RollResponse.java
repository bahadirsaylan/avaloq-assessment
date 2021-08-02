package com.avaloq.api.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class RollResponse {
    private HashMap<Integer,Integer> sums;
}
