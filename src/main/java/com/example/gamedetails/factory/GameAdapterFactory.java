package com.example.gamedetails.factory;

import com.example.gamedetails.adapters.GameAdapterInterface;
import com.example.gamedetails.adapters.impl.DotaAdapter;
import com.example.gamedetails.adapters.impl.LolAdapter;
import com.example.gamedetails.models.GamesNames;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameAdapterFactory {
    Map<GamesNames, GameAdapterInterface> adapters = new ConcurrentHashMap<>();

    public GameAdapterInterface getGameAdapter(GamesNames name){
        if(!adapters.containsKey(name)) {
            switch (name) {
                case LOL:
                    adapters.put(name, new LolAdapter());
                    break;
                case DOTA:
                    adapters.put(name, new DotaAdapter());
                    break;
                default:
            }
        }
        return adapters.get(name);
    }
}
