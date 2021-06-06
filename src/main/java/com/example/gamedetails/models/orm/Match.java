package com.example.gamedetails.models.orm;

import com.example.gamedetails.models.enums.MatchStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer pandoraId;

    private String name;
    private LocalDateTime scheduledAt;
    private MatchStatus status;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy = "match")
    private List<TeamMatchScore> teams;
}
