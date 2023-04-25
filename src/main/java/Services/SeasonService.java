package Services;

import Controllers.EpisodeController;
import Controllers.ScoreEpisodeController;
import Controllers.SeasonController;
import DAO.SeasonDAO;
import Entities.Episode;
import Entities.Season;
import Entities.User;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class SeasonService {

//    public static long StreamAverageScore(Season season) throws SQLException, IOException {
//        List<Episode> episodeList = SeasonController.FindEpisodeSeasonID(season);
//        List<Long> listScore = getScoreEpisodeList(episodeList);
//        return Math.round(listScore.stream()
//                .collect(Collectors.averagingLong(Long::longValue)));
//    }

    public static double StreamAverageScore(Season season) throws SQLException, IOException {
        List<Episode> episodeList = SeasonController.FindEpisodeSeasonID(season);

        List<Double> listScore = getScoreEpisodeList(episodeList);
        int numVotes = episodeList.stream()
                .mapToInt(episode -> ScoreEpisodeService.GetNumberVotesEpisode(episode))
                .sum();
        return Math.round(listScore.stream()
                .collect(Collectors.averagingDouble(Double::doubleValue)));
    }

    public static boolean SeasonWithHighScoreByUser(List<Episode> episodes, User user){
      //  if(episodes==null||episodes.isEmpty()) return false;
        long highepisode=episodes.stream()
                .filter(episode -> ScoreEpisodeController.RetrieveUserScore(episode,user)>2.5)
                .distinct()
                .count();

       return highepisode > (episodes.size() / 2);

    }



//    public static List<Long> getScoreEpisodeList(List<Episode> episodeList) throws SQLException, IOException {
//
//        return episodeList.stream()
//                .map(episode -> episode.getScore())
//                .collect(Collectors.toList());
//    }
   public static List<Double> getScoreEpisodeList(List<Episode> episodeList) throws SQLException, IOException {

        return episodeList.stream()
                .map(ScoreEpisodeController::GetEpisodeScore)
                .collect(Collectors.toList());
    }



//    public static long getVotesSeason(Season season) throws SQLException, IOException {
//
//        List<Episode> episodeList = SeasonController.FindEpisodeSeasonID(season);
//
//        return episodeList.stream()
//                .map(episode -> episode.getVotes())
//                .reduce((aLong, aLong2) -> aLong+aLong2)
//                .get();
//
//    }

    public static long getVotesSeason(Season season) throws SQLException, IOException {

        List<Episode> episodeList = SeasonController.FindEpisodeSeasonID(season);

        return episodeList.stream()
                .map(episode -> ScoreEpisodeController.GetNumberVotesEpisode(episode))
                .reduce((aLong, aLong2) -> aLong+aLong2)
                .get();

    }






    //    public static long StreamSumViewNumber(List<Episode> episodeList) {
//
//        return episodeList.stream()
//                .map(episode -> episode.getVueNbr())
//                .reduce((aLong, aLong2) -> aLong+aLong2)
//                .get();
//    }
    public static long StreamSumViewNumber(Season season) throws SQLException, IOException {

        List<Episode> episodeList = SeasonController.FindEpisodeSeasonID(season);

        return episodeList.stream()
                .map(episode -> episode.getVueNbr())
                .reduce((aLong, aLong2) -> aLong+aLong2)
                .get();

    }


    public static List<Season> FindSeasonSerieID(Long SerieID) throws SQLException, IOException {
        return SeasonDAO.FindSeasonSerieID(SerieID);
    }

    public static List<Season> FindSeasonID(Long SerieID) throws SQLException, IOException {
        return SeasonDAO.FindSeasonID(SerieID);
    }

    public static List<Season> FindSeasonName(String SerieName) throws SQLException, IOException {
        return SeasonDAO.FindSeasonName(SerieName);
    }

    public static long AddSeason(Season season) throws SQLException, IOException {
        return SeasonDAO.AddSeason(season);
    }

    public static boolean modifimg(Season season, File img) throws SQLException {
        return SeasonDAO.modifimg(season,img);
    }


    public static boolean ModifSynopsisSeason(Season season,File NewSynopsis) throws SQLException {
        return SeasonDAO.ModifSynopsisSeason(season,NewSynopsis);
    }


    public static boolean modifnom(Season season, String nom) throws SQLException {
        return SeasonDAO.modifnom(season,nom);
    }


    public static boolean modifAnnerdesoritie(Season season, LocalDate date) throws SQLException {
        return SeasonDAO.modifAnnerdesoritie(season,date);
    }


    public static boolean modifdescription(Season season,String description) throws SQLException {
        return SeasonDAO.modifdescription(season,description);
    }

    public static List<Season> GetAllSeasons() throws SQLException, IOException {
        return SeasonDAO.GetAllSeasons();
    }

    public static long StreamSpecificSeasons(long SerieID) throws SQLException, IOException {
        return GetAllSeasons().stream()
                .filter(season -> season.getSERIE_ID()==SerieID)
                .count();
    }



    public static List<Season> StreamSpecificSeasonsPremiereDate(long SerieID) throws SQLException, IOException {
        return GetAllSeasons().stream()
                .filter(season -> season.getSERIE_ID()==SerieID)
                .collect(Collectors.toList());
    }






    }
