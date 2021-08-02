package com.avaloq.api.service;

import com.avaloq.api.model.request.RollRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

@Service
public class SimulationService implements Serializable {

    @Autowired
    private Validator validator;

    public HashMap<Integer, Integer> roll(RollRequest rollRequest){
        Set<ConstraintViolation<RollRequest>> violations = validator.validate(rollRequest);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<RollRequest> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ConstraintViolationException("Please check errors", violations);
        }

        HashMap<Integer, Integer> results = new HashMap<>();
        int currentValue = 0;
        for(int i = rollRequest.getDices(); i <= rollRequest.getSides()*rollRequest.getDices(); i++){
            results.put(i,0);
        }

        for(int i = 0; i < rollRequest.getRolls(); i++){
            int rollSum = 0;
            for(int j=0; j < rollRequest.getDices(); j++){
                rollSum += (int) Math.floor(rollRequest.getSides() * Math.random()) + 1;
            }

            if(results.get(rollSum) == null){
                results.put(rollSum, 1);
            }else{
                currentValue = results.get(rollSum);
                results.put(rollSum, currentValue+1);
            }
        }

        return results;
    }

}
