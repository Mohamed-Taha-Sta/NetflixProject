package Controllers.FXMLControllers;

import Controllers.EpisodeController;
import Controllers.SeasonController;
import Controllers.SerieController;
import Entities.Episode;
import Utils.DataHolderEpisode;
import Utils.DataHolderSeason;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.*;

public class ProducerSeasonViewController implements Initializable {
    public Label SeasonOverView;
    public Button AddEpisode;
    public Button SynopsisBtn;
    public Button DebutDateBtn;
    public DatePicker DebutDatePicker;
    public TextField SeasonName;
    public Button ChangeDescriptionBtn;
    public TextArea New_Description;
    public TextArea Old_Description;
    public ImageView Thumbnail;
    public Button BackBtn;
    public Label AlertText;
    public Label Title;
    public Label RatingLabel;
    private List<Episode> episodeList;
    public Label DebutDateLabel;
    public HBox EpisodeViewer;


    public void onBack() throws Exception {
        DataHolderSeries.setSelectedSeries(SerieController.GetSerieByID(DataHolderSeason.getSelectedSeason().getSERIE_ID()).get(0));
        HelloApplication.SetRoot("ProducerSeriesView");
    }

    public void onEditSeasonImg() throws SQLException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a Season Thumbnail");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.jpeg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());

            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            if (width <= 1920 && height <= 1080) {
                double aspectRatio = (double) width / (double) height;
                if (aspectRatio <= 16.0 / 9.0) {
                    Thumbnail.setImage(image);
                    SeasonController.modifimg(DataHolderSeason.getSelectedSeason(), selectedFile);
                    showErrorMessage(AlertText, "Thumbnail Changed successfully");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Aspect Ratio");
                    alert.setContentText("Please select an image with an aspect ratio of 16:9 or smaller.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Image Size");
                alert.setContentText("Please select an image with dimensions of 1920x1080 pixels.");
                alert.showAndWait();
            }
        }


    }

    @FXML
    public void handleImageClick(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        Episode selectedEpisode = null;
        for (Episode ep : episodeList) {
            if (imageView.getImage().getUrl().equals(String.valueOf(ep.getImage().toURI()))) {
                selectedEpisode = ep;
                break;
            }
        }
        if (selectedEpisode != null) {
            try {
                DataHolderEpisode.setSelectedEpisode(EpisodeController.FindEpisodeID(selectedEpisode.getID()).get(0));
                HelloApplication.SetRoot("ProducerEpisodeView");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void onEdtSynosisBtn() throws SQLException {
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
                SeasonController.ModifSynopsisSeason(DataHolderSeason.getSelectedSeason(), selectedFile);
                showErrorMessage(AlertText, "Synopsis Changed successfully");

            } else {
                // If the selected image does not have the required dimensions, display an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Synopsis");
                alert.setContentText("Please select a valid Synopsis");
                alert.showAndWait();
            }
        }
    }

    public void SeasonOverviewBtn() throws Exception {
        HelloApplication.SetRoot("CheckStatsProdSeason");
    }

    public void onAddEpisode() throws Exception {
        DataHolderEpisode.setPreviousPage("ProducerSeasonView");
        DataHolderSeason.setIDSeason(DataHolderSeason.getSelectedSeason().getID());
        HelloApplication.SetRoot("AddEpisode");
    }

    public void OnSeasonNameBtn() throws SQLException {
        if (SeasonName.getText().isEmpty()) {
            showErrorMessage(AlertText, "Your New Season Title field is empty");
        } else if (isTextExceedingLength(SeasonName, 50)) {
            showErrorMessage(AlertText, "Your New Season Title field is too long");
        } else {
            SeasonController.modifnom(DataHolderSeason.getSelectedSeason(), SeasonName.getText());
            DataHolderSeason.getSelectedSeason().setName(SeasonName.getText());
            Title.setText(SeasonName.getText());
            showErrorMessage(AlertText, "Attribute changed successfully");

        }
    }

    public void OnDebutDateBtn() throws SQLException {
        if (DebutDatePicker.getValue().equals(DataHolderSeason.getSelectedSeason().getDebutDate())) {
            showErrorMessage(AlertText, "You didnt change the Debut Date");
        } else if (DebutDatePicker.getValue() == null) {
            showErrorMessage(AlertText, "Your new DebutDate is Empty");
        } else {
            SeasonController.modifAnnerdesoritie(DataHolderSeason.getSelectedSeason(), DebutDatePicker.getValue());
            DataHolderSeason.getSelectedSeason().setDebutDate(DebutDatePicker.getValue());
            DebutDateLabel.setText(DebutDatePicker.getValue().toString());
            showErrorMessage(AlertText, "Attribute changed successfully");
        }
    }

    public void ChangeDescriptionBtn() throws SQLException {
        if (New_Description.getText().isEmpty()) {
            showErrorMessage(AlertText, "Season must have a description");
        } else if (isTextExceedingLength(New_Description, 150)) {
            showErrorMessage(AlertText, "Season's new description is too long");
        } else if (New_Description.getText().equals(DataHolderSeason.getSelectedSeason().getDescription())) {
            showErrorMessage(AlertText, "You didn't change the Description");
        } else {
            SeasonController.modifdescription(DataHolderSeason.getSelectedSeason(), New_Description.getText());
            DataHolderSeason.getSelectedSeason().setDescription(New_Description.getText());
            Old_Description.setText(New_Description.getText());
            showErrorMessage(AlertText, "Attribute changed successfully");

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        EpisodeViewer.setSpacing(15);
        IconSetter(BackBtn, "src/main/resources/Images/Design/BackButton.png", 70);
        try {
            episodeList = EpisodeController.FindEpisodeSeasonID(DataHolderSeason.getSelectedSeason().getID());
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        for (Episode episode : episodeList) {
            ImageView imageView = new ImageView();
//            imageView.setFitHeight(100);
//            imageView.setFitWidth(150);
            System.out.println(episode.getImage().toURI());
            ImageSetter(imageView, episode.getImage().toURI().toString(), 150, 100);
            ImageClipper(imageView);
            imageView.setOnMouseClicked(this::handleImageClick);
            EpisodeViewer.getChildren().add(imageView);
        }


        Title.setText(DataHolderSeason.getSelectedSeason().getName());
        try {
            RatingLabel.setText(SeasonController.StreamAverageScore(DataHolderSeason.getSelectedSeason()) + "%");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        DebutDateLabel.setText(DataHolderSeason.getSelectedSeason().getDebutDate().toString());
        Old_Description.setText(DataHolderSeason.getSelectedSeason().getDescription());
        Thumbnail.setImage(new Image(DataHolderSeason.getSelectedSeason().getThumbnail().toURI().toString()));
        Thumbnail.setFitWidth(240);
        Thumbnail.setFitHeight(135);
        ImageClipper(Thumbnail);

    }
}
