package com.example.gamedetails.publish;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;

import java.time.OffsetDateTime;

@MessagingGateway(defaultRequestChannel = "testChannel")
public interface CustomPublisher {
    //    @Gateway(headers = {@GatewayHeader(name = "time", expression = "#{T(java.time.OffsetDateTime).now()}")})
    void publish(Object obj, @Header("time") OffsetDateTime time);
}
