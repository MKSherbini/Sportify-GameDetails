package com.example.gamedetails.service;

import com.example.gamedetails.adapter.MatchAdapter;
import com.example.gamedetails.models.dto.MatchDto;
import com.example.gamedetails.repos.MatchJpaRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchJpaRepo matchJpaRepo;
    private final MatchAdapter matchAdapter;

    public MatchService(MatchJpaRepo matchJpaRepo, MatchAdapter matchAdapter) {
        this.matchJpaRepo = matchJpaRepo;
        this.matchAdapter = matchAdapter;
    }

    public MatchDto getMatch(Integer id) {
        return matchAdapter.ormToDto(matchJpaRepo.getById(id));
    }

    public List<MatchDto> getMatches() {
        return matchJpaRepo.findAll().stream()
                .map(matchAdapter::ormToDto).collect(Collectors.toList());
    }

}
