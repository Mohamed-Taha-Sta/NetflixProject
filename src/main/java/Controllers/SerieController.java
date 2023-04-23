package Controllers;

import DAO.SerieDAO;
import Entities.Actor;
import Entities.Producer;
import Entities.Serie;
import Services.SerieService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SerieController {

    public static void main(String[] args) throws SQLException, IOException {
//        File file = new File("src/main/java/Test/Synopsis.mp4");

//        List<String> listGenre = Arrays.asList("Biography","Documentary");
//        List<Serie> serieList = SerieDAO.searchSeries(listGenre);
//
//        System.out.println(serieList);



//        File imageFile = new File("src/main/java/Test/SeriePlaceHolder.jpg");
//
//        List<Long> listMainActors = new ArrayList<>();
//        List<Long> listSuppActors = new ArrayList<>();
//        List<String> listGenre = new ArrayList<>();

//        listMainActors.add(21L);
//        listMainActors.add(10L);
//
//        listSuppActors.add(4L);
//
////        listGenre.add("Action");
//        listGenre.add("Comedie");
//        listGenre.add("Drama");
//        listGenre.add("Torki");

//        Serie serie = new Serie(65);



//        ModifSynopsisSerie(serie,file);

//        GetSerieByName("yezfezfez");

//        System.out.println(AddSerie(serie));


//        System.out.println(SerieDAO.GetSerieByName("Serie1"));

    }



    public static long AddSerie(Serie Serie) throws SQLException, FileNotFoundException {
        return SerieService.AddSerie(Serie);
    }

    public static List<Serie> GetSerieByName(String SerieName) throws SQLException, IOException {
        return SerieService.GetSerieByName(SerieName);
    }


    public static List<Serie> GetManySeries(long limit) throws SQLException, IOException {
        return SerieService.GetManySeries(limit);
    }


    public static List<Serie> GetAllSeries() throws SQLException, IOException {
        return SerieService.GetAllSeries();
    }


    public static List<Serie> GetSeriesByProducer(Producer producer) throws SQLException, IOException {
        return SerieService.GetSeriesByProducer(producer);
    }

    public static double StreamAverageScore(Serie serie) throws SQLException, IOException {
        return SerieService.StreamAverageScore(serie);
    }


    public static boolean DeleteSerie(Serie serie) throws SQLException, IOException {
        return SerieService.DeleteSerie(serie);
    }


    public static boolean modifimg(Serie serie, File img) throws SQLException, IOException {
        return SerieService.modifimg(serie,img);
    }

    public static boolean ModifSynopsisSerie(Serie serie,File NewSynopsis) throws SQLException {
        return SerieService.ModifSynopsisSerie(serie,NewSynopsis);
    }

    public static boolean modifnom(Serie Serie,String nom) throws SQLException {
        return SerieService.modifnom(Serie,nom);
    }

    public static boolean modifdescription(Serie serie,String description) throws SQLException {
        return SerieService.modifdescription(serie, description);
    }

    public static boolean modiflangues(Serie Serie,String langue) throws SQLException {
        return SerieService.modiflangues(Serie, langue);
    }

    public static boolean modifpaysoregine(Serie Serie,String paysorgine) throws SQLException {
        return SerieService.modifpaysoregine(Serie, paysorgine);
    }

    public static boolean modifAnnerdesoritie(Serie serie, LocalDate date) throws SQLException {
        return SerieService.modifAnnerdesoritie(serie, date);
    }

    public static boolean modiflistegenre(Serie serie,List<String> listegenre ) throws SQLException {
        return SerieService.modiflistegenre(serie, listegenre);
    }

    public static boolean deleteSerie_actsec(Serie serie, Actor act) throws SQLException {
        return SerieService.deleteSerie_actsec(serie, act);
    }

    public static boolean deleteSerie_actprinc(Serie serie,Actor act) throws SQLException {
        return SerieService.deleteSerie_actprinc(serie, act);
    }

    public static boolean ajoutSerie_actprinc(Serie serie,Actor act) throws SQLException {
        return SerieService.ajoutSerie_actprinc(serie, act);
    }

    public static boolean ajoutSerie_actsec(Serie serie,Actor act) throws SQLException {
        return SerieService.ajoutSerie_actsec(serie, act);
    }


    public static List<Serie> searchSeries(List<String> searchTerms) throws SQLException, IOException {
        /** Keep in mind, this returns everything on null except ID, Name and Thumbnail*/
        return SerieService.searchSeries(searchTerms);
    }

    public static List<Serie> searchSeriesOR(List<String> searchTerms) throws SQLException, IOException {
        return SerieService.searchSeries(searchTerms);
    }

    public static List<Serie> getMostRecentSeries(int numSeries) throws SQLException, IOException {
        /** Keep in mind, this returns everything on null except ID, Name and Thumbnail*/
        return SerieService.getMostRecentSeries(numSeries);
    }


    public static List<Serie> GetSerieByID(long ID) throws SQLException, IOException {
        return SerieService.GetSerieByID(ID);
    }

    public static List<Serie> FindSeriesByActor(Actor act) {
        return SerieService.FindSeriesByActor(act);
    }









}
