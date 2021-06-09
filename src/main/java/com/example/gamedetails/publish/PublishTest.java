package com.example.gamedetails.publish;

import com.example.gamedetails.models.orm.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.OffsetDateTime;

@Component
public class PublishTest {

    @Autowired
    private CustomPublisher gateway;

    @PostConstruct
    public void init(){
        gateway.publish(new Game(), OffsetDateTime.now());
    }
}
