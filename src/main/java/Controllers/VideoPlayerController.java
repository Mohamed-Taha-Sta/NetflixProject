package Controllers;

import DAO.EpisodeDAO2;
import Utils.DataHolder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class VideoPlayerController implements Initializable {
    public MediaView mvVideo;
    @FXML
    public MediaPlayer mpVideo;
    @FXML
    public Media mediaVideo;
    public Slider SliderTime;
    public Button ButtonPPR;
    public HBox HboxVolume;
    public Label LabelVolume;
    public Slider SliderVolume;
    public Label LabelCurrentTime;
    public Label LabelTotalTime;
    public Label LabelSpeed;
    public Label LabelFullScreen;

    private boolean atEndOfVideo = false;
    private boolean isPalying = false;
    private boolean isMuted = false;

    private ImageView ivPlay;
    private ImageView ivPause;
    private ImageView ivRestart;
    private ImageView ivVolume;
    private ImageView ivMute;
    private ImageView ivFullScreen;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("inVideoPlayerController");
        final int IV_SIZE = 25;
        mediaVideo = new Media("file:src/main/java/Test/VideoTest.mp4");
        mpVideo = new MediaPlayer(mediaVideo);
        mvVideo.setMediaPlayer(mpVideo);
        Image imagePlay = new Image(new File("src/main/resources/Images/VideoPlayer/Play.png").toURI().toString());
        ivPlay = new ImageView(imagePlay);
        ivPlay.setFitHeight(IV_SIZE);
        ivPlay.setFitWidth(IV_SIZE);
    }

    // private ImageView
}
