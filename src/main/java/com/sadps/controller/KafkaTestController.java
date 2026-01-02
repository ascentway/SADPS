package com.sadps.controller;
//
//import com.sadps.security.kafka.SecurityEvent;
//import com.sadps.security.kafka.SecurityEventProducer;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/test/kafka")
//@RequiredArgsConstructor
public class KafkaTestController {
//
//    private final SecurityEventProducer producer;
//
//    @PostMapping("/publish")
//    public ResponseEntity<String> testKafka() {
//        producer.publish(
//                new SecurityEvent(
//                        "KAFKA_TEST_EVENT",
//                        "test@system"
//                )
//        );
//        return ResponseEntity.ok("Kafka event published");
//    }
}
