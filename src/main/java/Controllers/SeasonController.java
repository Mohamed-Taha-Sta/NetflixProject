package Controllers;

import DAO.SeasonDAO;
import Entities.Episode;
import Entities.Season;
import Services.SeasonService;
import Services.SerieService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SeasonController {

    public static double StreamAverageScore(Season season) throws SQLException, IOException {return SeasonService.StreamAverageScore(season);}

    public static long StreamSumViewNumber(Season season) throws SQLException, IOException {return SeasonService.StreamSumViewNumber(season);}

    public static List<Episode> FindEpisodeSeasonID(Season season) throws SQLException, IOException {return EpisodeController.FindEpisodeSeasonID(season.getID());}

    public static long getVotesSeason(Season season) throws SQLException, IOException {return SeasonService.getVotesSeason(season);}

    public static List<Season> FindSeasonSerieID(Long SerieID) throws SQLException, IOException {return SeasonService.FindSeasonSerieID(SerieID);}

    public static List<Season> FindSeasonID(Long SerieID) throws SQLException, IOException {return SeasonService.FindSeasonID(SerieID);}

    public static List<Season> FindSeasonName(String SerieName) throws SQLException, IOException {return SeasonService.FindSeasonName(SerieName);}

    public static long AddSeason(Season season) throws SQLException, IOException {return SeasonService.AddSeason(season);}



    public static boolean modifimg(Season season, File img) throws SQLException {
        return SeasonService.modifimg(season,img);
    }


    public static boolean ModifSynopsisSeason(Season season,File NewSynopsis) throws SQLException {
        return SeasonService.ModifSynopsisSeason(season,NewSynopsis);
    }


    public static boolean modifnom(Season season, String nom) throws SQLException {
        return SeasonService.modifnom(season,nom);
    }

    public static boolean modifAnnerdesoritie(Season season, LocalDate date) throws SQLException {
        return SeasonService.modifAnnerdesoritie(season,date);
    }

    public static boolean modifdescription(Season season,String description) throws SQLException {
        return SeasonService.modifdescription(season,description);
    }

    public static List<Season> GetAllSeasons() throws SQLException, IOException {
        return SeasonService.GetAllSeasons();
    }

    public static long StreamSpecificSeasons(long SerieID) throws SQLException, IOException {
        return SeasonService.StreamSpecificSeasons(SerieID);
    }


    public static List<Season> StreamSpecificSeasonsPremiereDate(long SerieID) throws SQLException, IOException{
        return SeasonService.StreamSpecificSeasonsPremiereDate(SerieID);
    }



    }
