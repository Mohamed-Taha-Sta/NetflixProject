package Services;

import Controllers.SeasonController;
import DAO.EpisodeDAO2;
import Entities.Episode;
import Entities.Season;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SeasonService {

    public static long StreamAverageScore(Season season) throws SQLException, IOException {
        List<Episode> episodeList = SeasonController.FindEpisodeSeasonID(season);
        List<Long> listScore = getScoreEpisodeList(episodeList);
        return Math.round(listScore.stream()
                .collect(Collectors.averagingLong(Long::longValue)));
    }

//    public static long StreamSumViewNumber(List<Episode> episodeList) {
//
//        return episodeList.stream()
//                .map(episode -> episode.getVueNbr())
//                .reduce((aLong, aLong2) -> aLong+aLong2)
//                .get();
//    }

    public static long getVotesSeason(Season season) throws SQLException, IOException {

        List<Episode> episodeList = SeasonController.FindEpisodeSeasonID(season);

        return episodeList.stream()
                .map(episode -> episode.getVotes())
                .reduce((aLong, aLong2) -> aLong+aLong2)
                .get();

    }
    public static long StreamSumViewNumber(Season season) throws SQLException, IOException {

        List<Episode> episodeList = SeasonController.FindEpisodeSeasonID(season);

        return episodeList.stream()
                .map(episode -> episode.getVueNbr())
                .reduce((aLong, aLong2) -> aLong+aLong2)
                .get();

    }
    public static List<Long> getScoreEpisodeList(List<Episode> episodeList) throws SQLException, IOException {

        return episodeList.stream()
                .map(episode -> episode.getScore())
                .collect(Collectors.toList());

    }



}
