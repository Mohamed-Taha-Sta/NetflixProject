package Controllers.FXMLControllers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

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
    public VBox VboxParent;
    public HBox ControlsMenu;

    private boolean atEndOfVideo = false;
    private boolean isPlaying = false;
    private boolean isMuted = false;
    private boolean isFullScreen=false;
    private boolean isDragging = false;

    private ImageView ivPlay;
    private ImageView ivPause;
    private ImageView ivRestart;
    private ImageView ivVolume;
    private ImageView ivMute;
    private ImageView ivFullScreen;
    private ImageView ivExit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final int IV_Size = 25;
        //setting up player
        File file = new File("src/main/java/Test/VideoTest.mp4");
        mediaVideo = new Media(file.toURI().toString());
        mpVideo = new MediaPlayer(mediaVideo);
        mvVideo.setMediaPlayer(mpVideo);
        mpVideo.setAutoPlay(false);
        //play image
        Image play = new Image(new File("src/main/resources/Images/VideoPlayer/Play.png").toURI().toString());
        ivPlay = new ImageView(play);
        ivPlay.setFitHeight(IV_Size);
        ivPlay.setFitWidth(IV_Size);
        // pause image
        Image pause = new Image(new File("src/main/resources/Images/VideoPlayer/pause.png").toURI().toString());
        ivPause = new ImageView(pause);
        ivPause.setFitHeight(IV_Size);
        ivPause.setFitWidth(IV_Size);
        //restart image
        Image restart = new Image(new File("src/main/resources/Images/VideoPlayer/restart.png").toURI().toString());
        ivRestart = new ImageView(restart);
        ivRestart.setFitHeight(IV_Size);
        ivRestart.setFitWidth(IV_Size);
        //fullscreen image
        Image fullScreen = new Image(new File("src/main/resources/Images/VideoPlayer/fullscreen.png").toURI().toString());
        ivFullScreen = new ImageView(fullScreen);
        ivFullScreen.setFitHeight(IV_Size);
        ivFullScreen.setFitWidth(IV_Size);
        LabelFullScreen.setGraphic(ivFullScreen);
        //volume image
        Image volume = new Image(new File("src/main/resources/Images/VideoPlayer/volume.png").toURI().toString());
        ivVolume = new ImageView(volume);
        ivVolume.setFitHeight(IV_Size);
        ivVolume.setFitWidth(IV_Size);
        LabelVolume.setGraphic(ivVolume);
        //mute image
        Image mute = new Image(new File("src/main/resources/Images/VideoPlayer/volumeMute.png").toURI().toString());
        ivMute = new ImageView(mute);
        ivMute.setFitHeight(IV_Size);
        ivMute.setFitWidth(IV_Size);
        LabelSpeed.setText("1x");
        //Exit Image
        Image exit = new Image(new File("src/main/resources/Images/VideoPlayer/exit.png").toURI().toString());
        ivExit = new ImageView(exit);
        ivExit.setFitWidth(IV_Size);
        ivExit.setFitHeight(IV_Size);

        ButtonPPR.setGraphic(ivPlay);
        mpVideo.volumeProperty().bindBidirectional(SliderVolume.valueProperty());

        ButtonPPR.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button buttonPlay = (Button) actionEvent.getSource();
                if (atEndOfVideo) {

                    SliderTime.setValue(0);
                    atEndOfVideo = false;
                    isPlaying = false;
                }
                if (isPlaying) {
                    ButtonPPR.setGraphic(ivPlay);
                    mpVideo.pause();
                    isPlaying = false;
                } else {
                    ButtonPPR.setGraphic(ivPause);
                    mpVideo.play();
                    isPlaying = true;
                }
            }
        });

        SliderVolume.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mpVideo.setVolume(SliderVolume.getValue());
                if (mpVideo.getVolume() != 0.0) {
                    isMuted = false;
                    LabelVolume.setGraphic(ivVolume);

                } else {
                    isMuted = true;
                    LabelVolume.setGraphic(ivMute);
                }
            }
        });

        LabelSpeed.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (LabelSpeed.getText().equals("1x")) {
                    mpVideo.setRate(2.0);
                    LabelSpeed.setText("2x");
                } else {
                    mpVideo.setRate(1.0);
                    LabelSpeed.setText("1x");
                }

            }
        });

        LabelVolume.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (isMuted) {
                    isMuted = false;
                    LabelVolume.setGraphic(ivVolume);
                    SliderVolume.setValue(0.5);
                } else {
                    isMuted = true;
                    LabelVolume.setGraphic(ivMute);
                    SliderVolume.setValue(0.0);
                }
            }
        });


        BindCurrentTimeLabel();

        VboxParent.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observableValue, Scene oldScene, Scene newScene) {
                if (oldScene == null && newScene != null) {

                    mvVideo.fitHeightProperty().bind(newScene.heightProperty().subtract(ControlsMenu.heightProperty()));
                }
            }
        });

        LabelFullScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Label label = (Label) mouseEvent.getSource();
                Stage stage = (Stage) label.getScene().getWindow();


                if (stage.isFullScreen()) {
                    isFullScreen=false;
                    stage.setFullScreen(false);
                    LabelFullScreen.setGraphic(ivFullScreen);

                } else {
                    isFullScreen=true;
                    stage.setFullScreen(true);
                    LabelFullScreen.setGraphic(ivExit);

                }

                stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        if (keyEvent.getCode() == KeyCode.ESCAPE) {
                            isFullScreen=false;
                            LabelFullScreen.setGraphic(ivFullScreen);

                        }
                    }
                });
            }
        });

        mpVideo.totalDurationProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration oldDuration, Duration newDuration) {
                SliderTime.setMax(newDuration.toSeconds());
                LabelTotalTime.setText(getTime(newDuration));
            }
        });

        SliderTime.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean wasChanging, Boolean isChanging) {
                if (!isChanging) {
                    mpVideo.seek(Duration.seconds(SliderTime.getValue()));
                }
            }
        });

        SliderTime.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                double currentTime = mpVideo.getCurrentTime().toSeconds();
                if (Math.abs(currentTime - newValue.doubleValue()) > 0) {
                    mpVideo.seek(Duration.seconds(newValue.doubleValue()));
                }
                labelMatchEndVideo(LabelCurrentTime.getText(), LabelTotalTime.getText());
            }

        });

        SliderTime.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mpVideo.pause();
                ButtonPPR.setGraphic(ivPlay);
                isDragging = true;
            }
        });
        SliderTime.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (isPlaying) {
                    mpVideo.play();
                    ButtonPPR.setGraphic(ivPause);
                }
                isDragging = false;
            }
        });

        mpVideo.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration oldTime, Duration newTime) {
                if (!SliderTime.isValueChanging()) {
                    SliderTime.setValue(newTime.toSeconds());
                }
                labelMatchEndVideo(LabelCurrentTime.getText(), LabelTotalTime.getText());
            }
        });

        mpVideo.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                ButtonPPR.setGraphic(ivRestart);
                atEndOfVideo = true;
                if (LabelCurrentTime.textProperty().equals(LabelTotalTime.textProperty())) {
                    LabelCurrentTime.textProperty().unbind();
                    LabelCurrentTime.setText(getTime(mpVideo.getTotalDuration()) );
                }
            }
        });

        mpVideo.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration current) {
                SliderTime.setValue(current.toSeconds());
            }
        });


    }

    private void labelMatchEndVideo(String labelTime, String LabelTotalTime) {
        for (int i = 0; i < LabelTotalTime.length(); i++) {
            if (labelTime.charAt(i) != LabelTotalTime.charAt(i)) {
                atEndOfVideo = false;
                if (isPlaying) ButtonPPR.setGraphic(ivPause);
                else ButtonPPR.setGraphic(ivPlay);
                break;
            } else {
                atEndOfVideo = true;
                ButtonPPR.setGraphic(ivRestart);
            }
        }
    }

    private void BindCurrentTimeLabel() {

        LabelCurrentTime.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getTime(mpVideo.getCurrentTime()) ;
            }
        }, mpVideo.currentTimeProperty()));
    }

    private static String getTime(Duration currentTime) {
        int hours = (int) currentTime.toHours();
        int minutes = (int) currentTime.toMinutes();
        int seconds = (int) currentTime.toSeconds();
        if (seconds > 59) seconds = seconds % 60;
        if (minutes > 59) minutes = minutes % 60;
        if (hours > 59) hours = hours % 60;

        if (hours != 0) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else return String.format("%02d:%02d", minutes, seconds);


    }
}
