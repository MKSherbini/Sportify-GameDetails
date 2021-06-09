package com.example.gamedetails.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;


@Service
@Slf4j
public class LoggerService {

    @ServiceActivator(inputChannel = "testChannel", outputChannel = "returnTestChannel")
    public Object logIt(@Header("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime time,
                        @Payload Object obj) {
        log.info("{} LoggerService.logIt: {}", time, obj);
//        throw new RuntimeException();
        return obj;
    }

    //    @ServiceActivator(inputChannel = "testChannel")
    @ServiceActivator(inputChannel = "returnTestChannel")
    public void logIt2(Object obj) {
        log.info("LoggerService.logIt2: {}", obj);
//        throw new RuntimeException();
    }

    @ServiceActivator(inputChannel = "returnTestChannel")
    public void logIt3(Object obj) {
        log.info("LoggerService.logIt3: {}", obj);
    }
}
