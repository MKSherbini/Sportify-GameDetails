package com.example.gamedetails.adapter;

import com.example.gamedetails.models.dto.PlayerDto;
import com.example.gamedetails.models.orm.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerAdapter {
    private final ModelMapper modelMapper;

    public PlayerAdapter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PlayerDto ormToDto(Player teamOrm) {
        var playerDto = modelMapper.map(teamOrm, PlayerDto.class);
        playerDto.setTeamName(teamOrm.getTeam().getName());
        playerDto.setTeamAcronym(teamOrm.getTeam().getAcronym());
        playerDto.setTeamImageUrl(teamOrm.getTeam().getImageUrl());
        return playerDto;
    }

    public List<Player> jsonToOrm(String body) {
        var mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        root = root.elements().next();

        var teamMembers = new ArrayList<Player>();
//        System.out.println(root.get("id"));
        for (var player :
                root.get("players")) {
            var member = new Player(
                    player.get("birth_year").asText(),
                    player.get("birthday").asText(),
                    player.get("hometown").asText(),
                    player.get("image_url").asText(),
                    player.get("first_name").asText(),
                    player.get("last_name").asText(),
                    player.get("name").asText(),
                    player.get("nationality").asText()
            );
//            System.out.println("member = " + member);
            teamMembers.add(member);
        }

        return teamMembers;
    }


}
