package com.example.gamedetails.publish;

import com.example.gamedetails.models.dto.broker.MatchResultMsgDto;
import com.example.gamedetails.models.orm.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("die")
public class PublishTest {

    @Autowired
    private FinishedMatchesPublisher gateway;

    @GetMapping
    public String init() {
        gateway.publish(new MatchResultMsgDto(66, 66));
        return "66";
    }
}
