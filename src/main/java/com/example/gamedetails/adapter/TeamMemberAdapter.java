package com.example.gamedetails.adapter;

import com.example.gamedetails.models.dto.NewsDto;
import com.example.gamedetails.models.dto.TeamDto;
import com.example.gamedetails.models.dto.TeamMemberDto;
import com.example.gamedetails.models.dto.pandora.PandoraMatchesDto;
import com.example.gamedetails.models.orm.News;
import com.example.gamedetails.models.orm.TeamMember;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeamMemberAdapter {
    private final ModelMapper modelMapper;

    public TeamMemberAdapter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TeamMemberDto ormToDto(TeamMember teamOrm) {
        return modelMapper.map(teamOrm, TeamMemberDto.class);
    }

    public List<TeamMember> jsonToOrm(String body) {
        var mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        root = root.elements().next();

        var teamMembers = new ArrayList<TeamMember>();
//        System.out.println(root.get("id"));
        for (var player :
                root.get("players")) {
            var member = new TeamMember(
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
