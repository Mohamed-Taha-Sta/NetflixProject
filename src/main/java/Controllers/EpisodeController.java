package Controllers;

import DAO.EpisodeDAO;
import DAO.EpisodeDAO2;
import Entities.Episode;
import Entities.Resume;
import Entities.Synopsis;
import Entities.Text;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class EpisodeController {


    public static void main(String[] args) throws SQLException, IOException {
        EpisodeDAO2 episodeDAO = new EpisodeDAO2();

        //long seasonParentID, String name, int number, LocalDate debutDate, LocalDate premiereDate, Image image, Resume resume, Media media, long vueNbr, long score, long votes
        Resume resume = new Text();
        ((Text)resume).setTexte("This is an Episode");

        File file = new File("C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\VideoTest.mp4");

        Resume resume2 = new Synopsis(file);


        Episode episode = new Episode(1,"EpisodeFamilyGuy",5, LocalDate.of(2012,10,12),
                LocalDate.of(2012,10,10),resume,1500,50,150,file);

        Episode episode2 = new Episode(1,"HajaJdida",2, LocalDate.of(2023,12,14),
                LocalDate.of(2023,12,15),resume2,1500,50,150,file);


//        System.out.println(episodeDAO.ajout_episode(episode));
//        System.out.println(episodeDAO.ajout_episode(episode2));
        System.out.println(episodeDAO.FindEpisodeName("HajaJdida"));





    }


}
