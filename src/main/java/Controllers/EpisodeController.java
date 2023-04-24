package Controllers;

import DAO.EpisodeDAO2;
import Entities.Episode;
import Entities.Resume;
import Entities.Synopsis;
import Entities.Text;
import Services.EpisodeService;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

//import static DAO.EpisodeDAO2.UpdateViewNbrEpisode;

public class EpisodeController {


    public static List<Episode> FindEpisodeSeasonID(long id) throws SQLException, IOException {
        return EpisodeService.FindEpisodeSeasonID(id);
    }

    public static long getVotesEpisode(Episode ep) throws SQLException {
        return EpisodeService.getVotesEpisode(ep);
    }

    public static long getScoreEpisode(Episode ep) throws SQLException {
        return EpisodeService.getScoreEpisode(ep);
    }

    public static long getViewNbrEpisode(Episode ep){
        return EpisodeService.getViewNbrEpisode(ep);
    }

    public static boolean UpdatePositiveScoreEpisode(Episode ep) throws SQLException {
        return EpisodeService.UpdatePositiveScoreEpisode(ep);
    }

    public static boolean UpdateNegativeScoreEpisode(Episode ep) throws SQLException {
        return EpisodeService.UpdateNegativeScoreEpisode(ep);
    }


    public static boolean UpdateViewNbrEpisode(Episode ep) throws SQLException {
        return EpisodeService.UpdateViewNbrEpisode(ep);
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

    public static List<Episode> StreamSpecificEpisodesPremiereDate(long id) throws SQLException, IOException{
        return  EpisodeService.StreamSpecificEpisodesPremiereDate(id);
    }

    public static List<Episode> GetAllEpisodes() throws SQLException, IOException {
        return EpisodeService.GetAllEpisodes();
    }
}
