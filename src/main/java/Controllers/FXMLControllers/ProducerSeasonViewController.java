package Controllers.FXMLControllers;

import Controllers.EpisodeController;
import Controllers.SeasonController;
import Entities.Episode;
import Utils.DataHolderEpisode;
import Utils.DataHolderSeason;
import com.example.netflixproject.HelloApplication;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;
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


    public void onBack(ActionEvent actionEvent) throws Exception {
        HelloApplication.SetRoot("ProducerSeriesView");
    }

    public void onEditSeasonImg(MouseEvent mouseEvent) throws SQLException {

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
                Thumbnail.setImage(image);
                SeasonController.modifimg(DataHolderSeason.getSelectedSeason(),selectedFile);
                showMessage(AlertText,"Thumbnail Changed successfully");

            } else {
                // If the selected image does not have the required dimensions, display an error message
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


    public void onEdtSynosisBtn(ActionEvent actionEvent) throws SQLException {
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
                SeasonController.ModifSynopsisSeason(DataHolderSeason.getSelectedSeason(),selectedFile);
                showMessage(AlertText,"Synopsis Changed successfully");

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

    public void SeasonOverviewBtn(MouseEvent mouseEvent) throws Exception {
        HelloApplication.SetRoot("CheckStatsProdSeason");
    }

    public void onAddEpisode(ActionEvent actionEvent) throws Exception {
        DataHolderEpisode.setPreviousPage("ProducerSeasonView");
        DataHolderSeason.setIDSeason(DataHolderSeason.getSelectedSeason().getID());
        HelloApplication.SetRoot("AddEpisode");
    }

    public void OnSeasonNameBtn(ActionEvent actionEvent) throws SQLException {
        if (SeasonName.getText().isEmpty()) {
            showMessage(AlertText,"Your New Season Title field is empty");
        } else {
            SeasonController.modifnom(DataHolderSeason.getSelectedSeason(),SeasonName.getText());
            DataHolderSeason.getSelectedSeason().setName(SeasonName.getText());
            Title.setText(SeasonName.getText());
            showMessage(AlertText,"Attribute changed successfully");

        }
    }
    public void OnDebutDateBtn(ActionEvent actionEvent) throws SQLException {
        if (DebutDatePicker.getValue().equals(DataHolderSeason.getSelectedSeason().getDebutDate())) {
            showMessage(AlertText, "You didnt change the Debut Date");
        } else if (DebutDatePicker.getValue()==null) {
            showMessage(AlertText, "Your new DebutDate is Empty");
        } else {
            SeasonController.modifAnnerdesoritie(DataHolderSeason.getSelectedSeason(),DebutDatePicker.getValue());
            DataHolderSeason.getSelectedSeason().setDebutDate(DebutDatePicker.getValue());
            DebutDateLabel.setText(DebutDatePicker.getValue().toString());
            showMessage(AlertText,"Attribute changed successfully");
        }
    }

    public void ChangeDescriptionBtn(ActionEvent actionEvent) throws SQLException {
        if (New_Description.getText().isEmpty()) {
            showMessage(AlertText,"Season must have a description");
        } else if (New_Description.getText().equals(DataHolderSeason.getSelectedSeason().getDescription())) {
            showMessage(AlertText,"You didn't change the Description");
        } else {
            SeasonController.modifdescription(DataHolderSeason.getSelectedSeason(),New_Description.getText() );
            DataHolderSeason.getSelectedSeason().setDescription(New_Description.getText() );
            Old_Description.setText(New_Description.getText());
            showMessage(AlertText,"Attribute changed successfully");

        }
    }

    private void showMessage(Label label, String message) {
        label.setText(message);
        label.setOpacity(1);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            label.setOpacity(0);
        }));
        timeline.play();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        EpisodeViewer.setSpacing(15);
        IconSetter(BackBtn,"src/main/resources/Images/Design/BackButton.png",70);
        try {
            episodeList = EpisodeController.FindEpisodeSeasonID(DataHolderSeason.getSelectedSeason().getID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Episode episode : episodeList)
        {
            ImageView imageView = new ImageView();
//            imageView.setFitHeight(100);
//            imageView.setFitWidth(150);
            System.out.println(episode.getImage().toURI());
            ImageSetter(imageView,episode.getImage().toURI().toString(),150,100);
            ImageClipper(imageView);
            imageView.setOnMouseClicked(this::handleImageClick);
            EpisodeViewer.getChildren().add(imageView);
        }


        Title.setText(DataHolderSeason.getSelectedSeason().getName());
        try {
            RatingLabel.setText(String.valueOf(SeasonController.StreamAverageScore(DataHolderSeason.getSelectedSeason())+"%"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
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
