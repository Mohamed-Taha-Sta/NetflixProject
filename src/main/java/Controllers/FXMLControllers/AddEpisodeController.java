package Controllers.FXMLControllers;

import Controllers.EpisodeController;
import Entities.Episode;
import Utils.DataHolderEpisode;
import Utils.DataHolderSeason;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.isTextExceedingLength;
import static Utils.RepeatableFunction.showErrorMessage;

public class AddEpisodeController implements Initializable {

    public TextField Name;

    public TextField Thumbnail;
    public TextField Synopsis;
    public TextField Video;
    public DatePicker PremiereDate;
    public DatePicker DebutDate;
    public TextArea DescriptionBox;
    public Label AlertText;

    public void OnDone() throws Exception {
        DataHolderSeries.setSeries(null);
        HelloApplication.SetRoot("ProducerLandingPage");
    }

    @FXML
    protected void OnBack() throws Exception {
        HelloApplication.SetRoot(DataHolderEpisode.getPreviousPage());
    }


    @FXML
    protected void onAdd() throws Exception {
        if (Name.getText().isEmpty()) {
            showErrorMessage(AlertText, "Episode must have a name");
        } else if (Name.getText().isEmpty()) {
            showErrorMessage(AlertText, "Episode must have a valid name");
        } else if (isTextExceedingLength(Name, 50)) {
            showErrorMessage(AlertText, "Episode name too long");
        } else if (DescriptionBox.getText().isEmpty()) {
            showErrorMessage(AlertText, "Episode must have a Description");
        } else if (DescriptionBox.getText().isEmpty()) {
            showErrorMessage(AlertText, "Episode must have a valid Description");
        } else if (isTextExceedingLength(DescriptionBox, 150)) {
            showErrorMessage(AlertText, "Episode description too long");
        } else if (DebutDate.getValue() == null) {
            showErrorMessage(AlertText, "Episode must have a valid DebutDate");
        } else if (PremiereDate.getValue() == null) {
            showErrorMessage(AlertText, "Episode must have a valid PremiereDate");
        } else if (PremiereDate.getValue().isBefore(DebutDate.getValue())) {
            showErrorMessage(AlertText, "PremiereDate must be put after the Debut Date");
        } else {
            DataHolderEpisode.setName(Name.getText());
            DataHolderEpisode.setDescription(DescriptionBox.getText());
            DataHolderEpisode.setPremiereDate(PremiereDate.getValue());
            DataHolderEpisode.setDebutDate(DebutDate.getValue());

            long IdEpisode = EpisodeController.AddEpisode(new Episode(DataHolderSeason.getIDSeason(), DataHolderEpisode.getName(),
                    DataHolderEpisode.getDescription(), DataHolderEpisode.getDebutDate(), DataHolderEpisode.getPremiereDate(),
                    DataHolderEpisode.getSynopsis(), DataHolderEpisode.getVideo(), DataHolderEpisode.getThumbnail()));

            DataHolderEpisode.setIDEpisode(IdEpisode);

            HelloApplication.SetRoot("AddEpisode");

        }
    }

    @FXML
    public void ThumbnailSelect(javafx.scene.input.MouseEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Thumbnail");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.jpeg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            double targetAspectRatio = 16.0 / 9.0;

            double width = image.getWidth();
            double height = image.getHeight();
            double aspectRatio = width / height;


            if (aspectRatio != targetAspectRatio) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Image Aspect Ratio");
                alert.setContentText("Please select an image with an aspect ratio of 16:9.");
                alert.showAndWait();
            } else if (width <= 1920 && height <= 1080) {
                DataHolderEpisode.setThumbnail(selectedFile);
                Thumbnail.setText(selectedFile.toURI().toString());

            } else {
                // If the selected image does not have the required dimensions, display an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Image Size");
                alert.setContentText("Please select an image with dimensions of 1920*1080 pixels.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void SynopsisSelect(javafx.scene.input.MouseEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Synopsis");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter videoFilter = new FileChooser.ExtensionFilter("MP4 videos", "*.mp4");
        fileChooser.getExtensionFilters().add(videoFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (extension.equals("mp4")) {
                DataHolderEpisode.setSynopsis(selectedFile);
                Synopsis.setText(selectedFile.toURI().toString());

            } else {
                // If the selected image does not have the required dimensions, display an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Synopsis Size");
                alert.setContentText("Please select a valid Synopsis.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void VideoSelect(javafx.scene.input.MouseEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Video");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter videoFilter = new FileChooser.ExtensionFilter("MP4 videos", "*.mp4");
        fileChooser.getExtensionFilters().add(videoFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
//        Media media;
//        media=new Media(selectedFile.toURI().toString());
//        media.getDuration();
        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (extension.equals("mp4")) {
                DataHolderEpisode.setVideo(selectedFile);
                Video.setText(selectedFile.toURI().toString());

            } else {
                // If the selected image does not have the required dimensions, display an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Video Size");
                alert.setContentText("Please select a valid Video.");
                alert.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Thumbnail.setOnMouseClicked(event -> {
            try {
                ThumbnailSelect(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Synopsis.setOnMouseClicked(event -> {
            try {
                SynopsisSelect(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Video.setOnMouseClicked(event -> {
            try {
                VideoSelect(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }


}
