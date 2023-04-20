package Utils;

import java.io.File;
import java.time.LocalDate;

public class DataHolderEpisode {

    private static long IDEpisode;
    private static String Name;
    private static File Thumbnail;
    private static File Synopsis;
    private static File Video;
    private static LocalDate PremiereDate;

    private static LocalDate DebutDate;
    private static String Description;

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

    public static LocalDate getPremiereDate() {
        return PremiereDate;
    }

    public static void setPremiereDate(LocalDate premiereDate) {
        PremiereDate = premiereDate;
    }

    public static String getDescription() {
        return Description;
    }

    public static void setDescription(String description) {
        Description = description;
    }


    public static LocalDate getDebutDate() {
        return DebutDate;
    }

    public static void setDebutDate(LocalDate debutDate) {
        DebutDate = debutDate;
    }

    public static long getIDEpisode() {
        return IDEpisode;
    }

    public static void setIDEpisode(long IDEpisode) {
        DataHolderEpisode.IDEpisode = IDEpisode;
    }

}
