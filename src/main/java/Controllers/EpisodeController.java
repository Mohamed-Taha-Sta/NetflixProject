package Controllers;

import Entities.Episode;
import Services.EpisodeService;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EpisodeController {


    public static List<Episode> FindEpisodeSeasonID(long id) throws SQLException, IOException {
        return EpisodeService.FindEpisodeSeasonID(id);
    }

    public static List<Episode> FindEpisodeName(String EpisodeName) throws SQLException, IOException {
        return EpisodeService.FindEpisodeName(EpisodeName);
    }

    public static List<Episode> FindEpisodeID(long id) throws SQLException, IOException {
        return EpisodeService.FindEpisodeID(id);
    }


    public static long AddEpisode(Episode episode) throws SQLException, IOException {
        return EpisodeService.AddEpisode(episode);
    }


    public static boolean modifImg(Episode episode, File img) throws SQLException {
        return EpisodeService.modifImg(episode,img);
    }

    public static boolean modifSynopsis(Episode episode,File NewSynopsis) throws SQLException {
        return EpisodeService.modifSynopsis(episode,NewSynopsis);
    }

    public static boolean modifVideo(Episode episode,File NewSynopsis) throws SQLException {
        return EpisodeService.modifVideo(episode,NewSynopsis);
    }

    public static boolean modifNom(Episode episode, String nom) throws SQLException {
        return EpisodeService.modifNom(episode,nom);
    }

    public static boolean modifDebutDate(Episode episode, LocalDate date) throws SQLException {
        return EpisodeService.modifDebutDate(episode,date);
    }

    public static boolean modifPremiereDate(Episode episode, LocalDate date) throws SQLException {
        return EpisodeService.modifPremiereDate(episode,date);
    }

    public static boolean modifDescription(Episode episode,String description) throws SQLException {
        return EpisodeService.modifDescription(episode,description);
    }


    public static long StreamSpecificEpisodes(long id) throws SQLException, IOException {
        return EpisodeService.StreamSpecificEpisodes(id);
    }

    public static List<Episode> GetAllEpisodes() throws SQLException, IOException {
        return EpisodeService.GetAllEpisodes();
    }
}
