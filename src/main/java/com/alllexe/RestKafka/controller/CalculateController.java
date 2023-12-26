package com.alllexe.RestKafka.controller;

import com.alllexe.RestKafka.dto.Request;
import com.alllexe.RestKafka.dto.Response;
import com.alllexe.RestKafka.service.CalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CalculateController {

    private final CalculationService calculationService;

    @PostMapping(value = "/sum")
    public Response calculateSum(@RequestBody Request request) {
        return calculationService.calculateSum(request);
    }

    @PostMapping(value = "/subtract")
    public Response calculateSubtract(@RequestBody Request request) {
        return Response.builder()
                .result(request.getFirstNumber() - request.getSecondNumber())
                .build();
    }
}
