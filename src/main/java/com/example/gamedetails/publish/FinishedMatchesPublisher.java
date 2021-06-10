package com.example.gamedetails.publish;

import com.example.gamedetails.models.dto.broker.MatchResultMsgDto;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;

import java.time.OffsetDateTime;

@MessagingGateway(defaultRequestChannel = "finishedMatchesChannel")
public interface FinishedMatchesPublisher {
    void publish(MatchResultMsgDto matchResultMsgDto);
}
