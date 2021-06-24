package com.example.gamedetails.repos;

import com.example.gamedetails.models.orm.Match;
import com.example.gamedetails.models.orm.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface NewsJpaRepo extends JpaRepository<News, Integer> {
}
