package Services;

import DAO.FilmDAO;
import Entities.Actor;
import Entities.Film;
import Entities.Producer;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilmService {
    public static boolean Add(Film film){
        return FilmDAO.Add(film);
    }
    public static List<Film> FindByID(Long filmid){
        return FilmDAO.FindByID(filmid);
    }
    public static ArrayList<Film> FindByName(String filmnom){
        return FilmService.FindByName(filmnom);
    }
    public static ArrayList<Film> FindByActor(Actor act){
        return FilmDAO.FindByActor(act);
    }
    public static ArrayList<Film> GetAllFilms(){
        return FilmDAO.GetAllFilms();
    }
    public static ArrayList<Film> FindByproducer(Producer prod){
        return FilmDAO.FindByproducer(prod);
    }
    public static List<Film> filterByGenre( String genreFilter) {
        List<Film> films=FilmDAO.FindByName("");
            return films.stream()
                    .filter(film -> film.getListegenre().toString().contains(genreFilter))
                    .collect(Collectors.toList());
        }
    public static List<Film> searchFilm(List<String> searchTerms) throws SQLException, IOException {
        return FilmDAO.searchFilm(searchTerms);
    }
    public static List<Film> searchFilmOR(List<String> searchTerms) throws SQLException, IOException {
        return FilmDAO.searchFilmOR(searchTerms);
    }
    public static List<Film> getMostRecentFilm(int numSeries) throws SQLException, IOException{
        return FilmDAO.getMostRecentFilm(numSeries);
    }

    public static double getscorepourcantage(Film film){
        return film.getScore();
    }

    public static boolean modifnom(Film film,String nom) {
        return FilmDAO.modifnom(film,nom);
    }

    public static boolean modifdescription(Film film,String description) {
        return FilmDAO.modifdescription(film,description);
    }

    public static boolean modiflangues(Film film,String langue) {
        return FilmDAO.modiflangues(film,langue);
    }

    public static boolean modifpaysoregine(Film film,String paysorgine) {
        return FilmDAO.modifpaysoregine(film,paysorgine);
    }

    public static boolean modifAnnerdesoritie(Film film, LocalDate date) {
        return FilmDAO.modifAnnerdesoritie(film,date);
    }

    public static boolean modiflistegenre(Film film,List<String> listegenre ) {
        return FilmDAO.modiflistegenre(film,listegenre);
    }

    public static boolean modifduree(Film film,String duree ) {
        return FilmDAO.modifduree(film,duree);
    }

    public static boolean modifimg(Film film, File img) {
        return FilmDAO.modifimg(film,img);
    }

    public static boolean modiffilmvedio(Film film, File vid) {
        return FilmDAO.Editvideo(film,vid);
    }

    public static boolean modifsynop(Film film, File synop) {
        return FilmDAO.modifsynop(film,synop);
    }





















    }
