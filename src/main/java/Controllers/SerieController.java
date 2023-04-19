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
        File file = new File("src/main/java/Test/Synopsis.mp4");
        File imageFile = new File("src/main/java/Test/SeriePlaceHolder.jpg");

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

        Serie serie = new Serie("Serie1",1,"Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
                "when an unknown printer took a galley of type and scrambled it to make a type specimen book.", LocalDate.of(2008,1,8),
                "English","America",listGenre,imageFile,file,listMainActors,listSuppActors);

//        AddSerie(serie);


        System.out.println(SerieDAO.GetSerieByName("Serie1"));

    }



    public static long AddSerie(Serie Serie) throws SQLException, FileNotFoundException {
        return SerieService.AddSerie(Serie);
    }

    public static List<Serie> GetSerieByName(String SerieName) throws SQLException, IOException {
        return SerieService.GetSerieByName(SerieName);
    }




}
