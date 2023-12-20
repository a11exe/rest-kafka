package com.alllexe.RestKafka.controller;

import com.alllexe.RestKafka.dto.NumbersHolder;
import com.alllexe.RestKafka.dto.Sum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SumController {

    @PostMapping(value = "/sum")
    public Sum calculateSum(NumbersHolder numbersHolder) {
        return Sum.builder()
                .sum(numbersHolder.getFirstNumber() + numbersHolder.getSecondNumber())
                .build();
    }
}
