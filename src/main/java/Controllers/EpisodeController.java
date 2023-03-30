package Controllers;

import DAO.EpisodeDAO;
import Entities.Episode;
import Entities.Resume;
import Entities.Text;


import java.io.File;
import java.time.LocalDate;

public class EpisodeController {


    public static void main(String[] args) {
        EpisodeDAO episodeDAO = new EpisodeDAO();

        //long seasonParentID, String name, int number, LocalDate debutDate, LocalDate premiereDate, Image image, Resume resume, Media media, long vueNbr, long score, long votes
        Resume resume = new Text();
        File file = new File("C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\VideoTest.mp4");

        Episode episode = new Episode(1,"Hal9a700",2, LocalDate.of(2002,12,14),
                LocalDate.of(2002,12,15),resume,1500,50,150,file);

        File file1 = episode.getMedia();

        episodeDAO.ajout_episode(episode);

//        episodeDAO.recherche_Episode("Hal9a1");
//        episodeDAO.FindAllEpisodes();

    }


}
