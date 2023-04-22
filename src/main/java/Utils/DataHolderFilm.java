package Utils;

import Entities.Actor;
import Entities.Film;
import Entities.Serie;
import javafx.collections.ObservableList;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataHolderFilm {

    private static String Name;
    private static String Duration;
    private static File Thumbnail;
    private static File Synopsis;
    private static File Video;
    private static LocalDate DebutDate;
    private static String Description;
    private static String Language;
    private static String CountryOfOrigin;
    private static ObservableList<Film> Films;
    private static List<String> GenreList;
    private static List<Actor> MainActorsList;
    private static List<Actor> SuppActorsList;
    private static ArrayList<Actor> AllTheActors;
    private static Film selectedFilm;

    public static Film getSelectedFilm() {
        return selectedFilm;
    }

    public static void setSelectedFilm(Film selectedFilm) {
        DataHolderFilm.selectedFilm = selectedFilm;
    }

    public static ArrayList<Actor> getAllTheActors() {
        return AllTheActors;
    }

    public static void setAllTheActors(ArrayList<Actor> allTheActors) {
        AllTheActors = allTheActors;
    }

    public static String getName() {
        return Name;
    }

    public static void setName(String name) {
        Name = name;
    }

    public static File getThumbnail() {
        return Thumbnail;
    }

    public static void setThumbnail(File thumbnail) {
        Thumbnail = thumbnail;
    }

    public static File getSynopsis() {
        return Synopsis;
    }

    public static void setSynopsis(File synopsis) {
        Synopsis = synopsis;
    }

    public static File getVideo() {
        return Video;
    }

    public static void setVideo(File video) {
        Video = video;
    }

    public static LocalDate getDebutDate() {
        return DebutDate;
    }

    public static void setDebutDate(LocalDate debutDate) {
        DebutDate = debutDate;
    }

    public static String getDescription() {
        return Description;
    }

    public static void setDescription(String description) {
        Description = description;
    }

    public static String getLanguage() {
        return Language;
    }

    public static void setLanguage(String language) {
        Language = language;
    }

    public static String getCountryOfOrigin() {
        return CountryOfOrigin;
    }

    public static void setCountryOfOrigin(String countryOfOrigin) {
        CountryOfOrigin = countryOfOrigin;
    }

    public static List<String> getGenreList() {
        return GenreList;
    }

    public static void setGenreList(List<String> genreList) {
        GenreList = genreList;
    }

    public static List<Actor> getMainActorsList() {
        return MainActorsList;
    }

    public static void setMainActorsList(List<Actor> mainActorsList) {
        MainActorsList = mainActorsList;
    }

    public static List<Actor> getSuppActorsList() {
        return SuppActorsList;
    }

    public static void setSuppActorsList(List<Actor> suppActorsList) {
        SuppActorsList = suppActorsList;
    }

    public static String getDuration() {
        return Duration;
    }

    public static void setDuration(String duration) {
        Duration = duration;
    }
    public static ObservableList<Film> getFilms() {
        return Films;
    }

    public static void setFilms(ObservableList<Film> films) {
        Films = films;
    }



}
