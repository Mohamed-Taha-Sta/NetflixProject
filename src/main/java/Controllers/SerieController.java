package Controllers;

import DAO.SerieDAO;
import Entities.Actor;
import Entities.MainActor;
import Entities.Serie;
import Services.SerieService;

import java.io.File;
import java.io.FileNotFoundException;
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

//        listGenre.add("Action");
        listGenre.add("Comedie");
        listGenre.add("Drama");
//        listGenre.add("Torki");

        Serie serie = new Serie("Serie5","Ahmed", LocalDate.of(2009,3,12),
                "German","Germany",listGenre,imageFile,4L,file,listMainActors,listSuppActors);

//        SerieDAO.AddSerie(serie);

//        System.out.println(SerieDAO.GetSerieByName("Serie1"));

    }



    public static long AddSerie(Serie Serie) throws SQLException, FileNotFoundException {
        return SerieService.AddSerie(Serie);
    }

    public static List<Serie> GetSerieByName(String SerieName) throws SQLException, IOException {
        return SerieService.GetSerieByName(SerieName);
    }




}
