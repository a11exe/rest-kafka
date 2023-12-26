package com.alllexe.RestKafka.service;

import com.alllexe.RestKafka.dto.Request;
import com.alllexe.RestKafka.dto.Response;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalculationService {

    private final ReplyingKafkaTemplate<String, Request, Response> kafkaTemplate;

    @Value("${kafka.topic.sum.request}")
    String sumRequestTopic;
    @Value("${kafka.topic.sum.reply}")
    String sumReplyTopic;

    @SneakyThrows
    public Response calculateSum(Request request) {
        // create producer record
        ProducerRecord<String, Request> record = new ProducerRecord<>(sumRequestTopic, request);
        // set reply topic in header
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, sumReplyTopic.getBytes()));
        // post in kafka topic
        RequestReplyFuture<String, Request, Response> sendAndReceive = kafkaTemplate.sendAndReceive(record);

        // confirm if producer produced successfully
        SendResult<String, Request> sendResult = sendAndReceive.getSendFuture().get();

        //print all headers
        sendResult.getProducerRecord().headers()
                .forEach(header -> log.info("header: key: [{}], value: [{}]", header.key(), new String(header.value())));

        // get consumer record
        ConsumerRecord<String, Response> consumerRecord = sendAndReceive.get();

        // return consumer value
        return consumerRecord.value();
    }

}
