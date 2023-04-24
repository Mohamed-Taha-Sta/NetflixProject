package Services;

import DAO.EpisodeDAO2;
import Entities.Episode;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EpisodeService {




    public static long AddEpisode(Episode episode) throws SQLException, IOException {
        return EpisodeDAO2.AddEpisode(episode);
    }

    public static List<Episode> FindEpisodeSeasonID(long id) throws SQLException, IOException {
        return EpisodeDAO2.FindEpisodeSeasonID(id);
    }

    public static List<Episode> FindEpisodeName(String EpisodeName) throws SQLException, IOException {
        return EpisodeDAO2.FindEpisodeName(EpisodeName);
    }

    public static List<Episode> FindEpisodeID(long id) throws SQLException, IOException {
        return EpisodeDAO2.FindEpisodeID(id);
    }

    public static long getVotesEpisode(Episode ep) throws SQLException {
        return EpisodeDAO2.getVotesEpisode(ep);
    }

    public static long getScoreEpisode(Episode ep) throws SQLException {
        return EpisodeDAO2.getScoreEpisode(ep);
    }

    public static long getViewNbrEpisode(Episode ep){
        return EpisodeDAO2.getViewNbrEpisode(ep);
    }

    public static boolean UpdatePositiveScoreEpisode(Episode ep) throws SQLException {
        return EpisodeDAO2.UpdatePositiveScoreEpisode(ep);
    }

    public static boolean UpdateNegativeScoreEpisode(Episode ep) throws SQLException {
        return EpisodeDAO2.UpdateNegativeScoreEpisode(ep);
    }

    public static boolean UpdateViewNbrEpisode(Episode ep) throws SQLException {
        return EpisodeDAO2.UpdateViewNbrEpisode(ep);
    }

    public static boolean modifImg(Episode episode, File img) throws SQLException {
        return EpisodeDAO2.modifImg(episode,img);
    }

    public static boolean modifSynopsis(Episode episode,File NewSynopsis) throws SQLException {
        return EpisodeDAO2.modifSynopsis(episode,NewSynopsis);
    }

    public static boolean modifVideo(Episode episode,File NewSynopsis) throws SQLException {
        return EpisodeDAO2.modifVideo(episode,NewSynopsis);
    }

    public static boolean modifNom(Episode episode, String nom) throws SQLException {
        return EpisodeDAO2.modifNom(episode,nom);
    }

    public static boolean modifDebutDate(Episode episode, LocalDate date) throws SQLException {
        return EpisodeDAO2.modifDebutDate(episode,date);
    }

    public static boolean modifPremiereDate(Episode episode, LocalDate date) throws SQLException {
        return EpisodeDAO2.modifPremiereDate(episode,date);
    }

    public static boolean modifDescription(Episode episode,String description) throws SQLException {
        return EpisodeDAO2.modifDescription(episode,description);
    }
    public static List<Episode> GetAllEpisodes() throws SQLException, IOException {
        return EpisodeDAO2.GetAllEpisodes();
    }

    public static List<Episode> GetAllEpisodesPremiereDate() throws SQLException, IOException {
        return EpisodeDAO2.GetAllEpisodesPremiereDate();
    }



    public static long StreamSpecificEpisodes(long id) throws SQLException, IOException {
        return GetAllEpisodes().stream()
                .filter(episode -> episode.getSeasonParentID()==id)
                .count();
    }
    public static List<Episode> StreamSpecificEpisodesPremiereDate(long id) throws SQLException, IOException {
        return GetAllEpisodesPremiereDate().stream()
                .filter(episode -> episode.getSeasonParentID()==id)
                .collect(Collectors.toList());
    }
}
