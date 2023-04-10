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

import static DAO.EpisodeDAO2.UpdateViewNbrEpisode;

public class EpisodeController {


    public static void main(String[] args) throws SQLException, IOException {
        EpisodeDAO2 episodeDAO = new EpisodeDAO2();

        //long seasonParentID, String name, int number, LocalDate debutDate, LocalDate premiereDate, Image image, Resume resume, Media media, long vueNbr, long score, long votes
        Resume resume = new Text();
        ((Text)resume).setTexte("This is an Episode");

        File file = new File("src/main/java/Test/VideoTest.mp4");
        File imageFile = new File("src/main/java/Test/LionTest.jpeg");
        Resume resume2 = new Synopsis(file);


        Episode episode = new Episode(21,"EpisodeInteresting",4, LocalDate.of(2014,12,9),
                LocalDate.of(2011,11,2),resume,file,imageFile);

        Episode episode2 = new Episode(21,"TahaEpsisode",2, LocalDate.of(1990,3,14),
                LocalDate.of(2015,12,12),resume2,file,imageFile);


//        System.out.println(episodeDAO.AddEpisode(episode));
//        System.out.println(episodeDAO.AddEpisode(episode2));
//        List<Episode> episodeList = episodeDAO.FindEpisodeID(90L);
//        System.out.println(episodeDAO.FindEpisodeName("FaresSEpisode"));

//        System.out.println(EpisodeDAO2.getViewNbr(episodeList.get(0)));

//        episodeDAO.UpdateViewNbrEpisode(episodeList.get(0));
//        episodeDAO.UpdateVoteEpisode(episodeList.get(0));

//        episodeDAO.UpdateVoteEpisode(episodeList.get(0));episodeDAO.UpdateScoreEpisode(episodeList.get(0));

//        episodeDAO.UpdatePositiveScoreEpisode(episodeList.get(0));

    }


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


    public static boolean AddEpisode(Episode episode) throws SQLException, IOException {
        return EpisodeService.AddEpisode(episode);
    }


}
