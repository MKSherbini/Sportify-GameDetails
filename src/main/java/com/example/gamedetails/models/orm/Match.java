package com.example.gamedetails.models.orm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String title;
    private LocalDateTime matchTime;
    private boolean inProgress;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy = "match")
    private List<TeamMatchScore> teams;
}
