package com.alllexe.RestKafka.service;

import com.alllexe.RestKafka.dto.Request;
import com.alllexe.RestKafka.dto.Response;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class SumConsumer {
    @KafkaListener(topics = "${kafka.topic.sum.request}")
    @SendTo
    public Response receive(Request request) {
        return Response.builder()
                .firstNumber(request.getFirstNumber())
                .secondNumber(request.getSecondNumber())
                .result(request.getFirstNumber() + request.getSecondNumber())
                .build();
    }
}
