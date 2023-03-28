package DAO;

import Entities.Content;
import Entities.Episode;
import Entities.Season;
import Entities.Serie;

import java.util.ArrayList;
import java.util.List;

public class ActorDAO {

    public List<Long> CheckVues(Content content, Season season, Episode episode)
    {
        List<Long> vues=new ArrayList<>();

        if (content instanceof Serie) {
            Serie serie = (Serie) content;
            if (season != null) {
                for (Season se : serie.getSeasonList()) {
                    if (se.equals(season)) {

                    }
                }

            }

        }
    }
}