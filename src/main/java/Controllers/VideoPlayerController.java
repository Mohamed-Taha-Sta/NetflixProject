package Controllers;

import DAO.EpisodeDAO2;
import Utils.DataHolder;
import javafx.event.ActionEvent;
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
import javafx.stage.FileChooser;

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
    @FXML
    public void handleButtonAction() throws Exception{
        FileChooser fc= new FileChooser();
        File file= fc.showOpenDialog(null);
        String filePath = file.toURI().toString();

        if(filePath!= null) {
            System.out.println("FilePath is- "+filePath);
            Media media= new Media(filePath);
            mpVideo= new MediaPlayer(media);

            // move initialization of mvVideo after creating the MediaPlayer
            mvVideo= new MediaView(mpVideo);
            mvVideo.setFitWidth(600);
            mvVideo.setFitHeight(600);
            mvVideo.setPreserveRatio(false);
            mvVideo.setSmooth(true);

            mpVideo.setAutoPlay(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
