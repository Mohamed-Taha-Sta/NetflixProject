package Utils;

import Entities.Actor;
import Entities.Serie;

import java.io.File;
import java.util.List;

public class DataHolderSeries {

    private static String SeriesName;
    private static String Language;
    private static String CountryOfOrigin;
    private static File Thumbnail;
    private static File Synopsis;
    private static List<String> GenreList;
    private static List<Actor> MainActorsList;
    private static List<Actor> SuppActorsList;

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

    public static String getSeriesName() {
        return SeriesName;
    }

    public static void setSeriesName(String seriesName) {
        SeriesName = seriesName;
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

    public static List<String> getGenreList() {
        return GenreList;
    }

    public static void setGenreList(List<String> genreList) {
        GenreList = genreList;
    }
}
