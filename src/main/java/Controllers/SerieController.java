package Controllers;

import DAO.SerieDAO;
import Entities.Actor;
import Entities.MainActor;
import Entities.Serie;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SerieController {

    public static void main(String[] args) throws SQLException, IOException {
        File file = new File("src/main/java/Test/VideoTest.mp4");
        File imageFile = new File("src/main/java/Test/LionTest.jpeg");

        List<Long> listMainActors = new ArrayList<>();
        List<Long> listSuppActors = new ArrayList<>();
        List<String> listGenre = new ArrayList<>();

        listMainActors.add(1L);
        listMainActors.add(2L);

        listSuppActors.add(1L);

        listGenre.add("Action");
        listGenre.add("Comedie");
        listGenre.add("Drama");

        Serie serie = new Serie("SerieNumDra9adeh","Ahmed", LocalDate.of(2009,3,12),
                "Chel7a","Germany",listGenre,imageFile,4L,file,listMainActors,listSuppActors);

        SerieDAO.AddSerie(serie);

//        System.out.println(SerieDAO.GetSerieByName("Hajet9dom"));

    }


}
