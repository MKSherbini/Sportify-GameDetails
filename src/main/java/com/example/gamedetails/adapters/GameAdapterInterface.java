package com.example.gamedetails.adapters;

import com.example.gamedetails.models.GameDetails;
import com.example.gamedetails.models.Matches;
import com.example.gamedetails.models.News;

import java.util.List;

public interface GameAdapterInterface {
    public List<News> getNews();
    public List<Matches> getMatches();
    public GameDetails getGameDetails();
}
