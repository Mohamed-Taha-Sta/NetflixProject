package Entities;
import javafx.scene.media.*;

import java.io.File;

public class Synopsis extends Resume{

    File TinyVideo;

    public File getTinyVideo() {
        return TinyVideo;
    }

    public void setTinyVideo(File tinyVideo) {
        TinyVideo = tinyVideo;
    }

    public Synopsis(String title, File tinyVideo) {
        super(title);
        TinyVideo = tinyVideo;
    }

    public Synopsis(File tinyVideo) {
        TinyVideo = tinyVideo;
    }
}
