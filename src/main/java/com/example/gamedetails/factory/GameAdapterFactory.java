package com.example.gamedetails.factory;

import com.example.gamedetails.adapters.GameAdapterInterface;
import com.example.gamedetails.adapters.impl.DotaAdapter;
import com.example.gamedetails.adapters.impl.LolAdapter;
import com.example.gamedetails.models.GamesNames;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameAdapterFactory {
    Map<GamesNames, GameAdapterInterface> adapters = new ConcurrentHashMap<>();

    public GameAdapterInterface getGameAdapter(GamesNames name){
        if(!adapters.containsKey(name)) {
            switch (name) {
                case Lol:
                    adapters.put(name, new LolAdapter());
                    break;
                case Dota:
                    adapters.put(name, new DotaAdapter());
                    break;
                default:
            }
        }
        return adapters.get(name);
    }
}
