package Controllers;

import Entities.*;
import Services.FilmService;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FilmController {
    public static boolean Add(Film film){
        return FilmService.Add(film);
    }
    public static List<Film> FindByID(Long filmid){
        return FilmService.FindByID(filmid);
    }
    public static ArrayList<Film> FindByName(String filmnom){
        return FilmService.FindByName(filmnom);
    }
    public static ArrayList<Film> FindByActor(Actor act){
        return FilmService.FindByActor(act);
    }
    public static ArrayList<Film> GetAllFilms(){
        return FilmService.GetAllFilms();
    }

    public static ArrayList<Film> FindByproducer(Producer prod){
        return FilmService.FindByproducer(prod);
    }

    public static List<Film> searchFilm(List<String> searchTerms) throws SQLException, IOException {
        return FilmService.searchFilm(searchTerms);
    }
    public static List<Film> searchFilmOR(List<String> searchTerms) throws SQLException, IOException {
        return FilmService.searchFilmOR(searchTerms);
    }
    public static List<Film> getMostRecentFilm(int numSeries) throws SQLException, IOException{
        return FilmService.getMostRecentFilm(numSeries);
    }
    public static Long getscorepourcantage(Film film) {
        return FilmService.getscorepourcantage(film);
    }

    public static boolean modifnom(Film film,String nom) {
        return FilmService.modifnom(film,nom);
    }

    public static boolean modifdescription(Film film,String description) {
        return FilmService.modifdescription(film,description);
    }

    public static boolean modiflangues(Film film,String langue) {
        return FilmService.modiflangues(film,langue);
    }

    public static boolean modifpaysoregine(Film film,String paysorgine) {
        return FilmService.modifpaysoregine(film,paysorgine);
    }

    public static boolean modifAnnerdesoritie(Film film, LocalDate date) {
        return FilmService.modifAnnerdesoritie(film,date);
    }

    public static boolean modiflistegenre(Film film,List<String> listegenre ) {
        return FilmService.modiflistegenre(film,listegenre);
    }

    public static boolean modifduree(Film film,String duree ) {
        return FilmService.modifduree(film,duree);
    }

    public static boolean modifimg(Film film, File img) {
        return FilmService.modifimg(film,img);
    }

    public static boolean modiffilmvedio(Film film, File vid) {
        return FilmService.modiffilmvedio(film,vid);
    }

    public static boolean modifsynop(Film film, File synop) {
        return FilmService.modifsynop(film,synop);
    }


}
