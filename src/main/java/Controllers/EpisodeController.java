package Controllers;

import DAO.EpisodeDAO2;
import Entities.Episode;
import Entities.Resume;
import Entities.Synopsis;
import Entities.Text;
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
                LocalDate.of(2011,11,2),resume,1500,50,150,file,imageFile);

        Episode episode2 = new Episode(21,"TahaEpsisode",2, LocalDate.of(1990,3,14),
                LocalDate.of(2015,12,12),resume2,1500,50,150,file,imageFile);


        System.out.println(episodeDAO.AddEpisode(episode));
        System.out.println(episodeDAO.AddEpisode(episode2));
//        List<Episode> episodeList = episodeDAO.FindEpisodeID(50L);
//        System.out.println(episodeDAO.FindEpisodeName("FaresSEpisode"));

//        System.out.println(EpisodeDAO2.getViewNbr(episodeList.get(0)));

//        episodeDAO.UpdateViewNbrEpisode(episodeList.get(0));
//        episodeDAO.UpdateVoteEpisode(episodeList.get(0));

//        episodeDAO.UpdateVoteEpisode(episodeList.get(0));episodeDAO.UpdateScoreEpisode(episodeList.get(0));

//        episodeDAO.UpdatePositiveScoreEpisode(episodeList.get(0));

    }


}
