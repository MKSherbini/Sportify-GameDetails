package com.example.gamedetails.repos;

import com.example.gamedetails.models.orm.Match;
import com.example.gamedetails.models.orm.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsJpaRepo extends JpaRepository<News, Integer> {
}
