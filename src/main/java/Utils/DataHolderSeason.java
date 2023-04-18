package Utils;

import java.io.File;

public class DataHolderSeason {

    private static String Name;
    private static File Thumbnail;
    private static File Synopsis;

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
}
