package Controllers.FXMLControllers;

import Controllers.EpisodeController;
import Controllers.ScoreEpisodeController;
import Utils.DataHolderEpisode;
import com.example.netflixproject.HelloApplication;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import static Utils.RepeatableFunction.IconSetter;
import static Utils.RepeatableFunction.ImageClipper;

public class ProducerEpisodeViewController implements Initializable {


    public Label Title;
    public TextField EpisodeName;
    public Button EpisodeNameBtn;
    public Label DebutDateLabel;
    public Button DebutDateBtn;
    public DatePicker DebutDatePicker;
    public Label PremiereDateLabel;
    public DatePicker PremiereDatePicker;
    public Button PremiereDateBtn;
    public TextArea Old_Description;
    public TextArea New_Description;
    public Button ChangeDescriptionBtn;
    public Button BackBtn;
    public ImageView Thumbnail;
    public Label AlertText;
    public Label RatingLabel;

    public void onBack(ActionEvent actionEvent) throws Exception {
        HelloApplication.SetRoot("ProducerSeasonView");

    }

    public void EpisodeOverviewBtn(MouseEvent mouseEvent) throws Exception {
        HelloApplication.SetRoot("CheckStatsProdEpisode");
    }

    public void onEditEpisodeImg(MouseEvent mouseEvent) throws SQLException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Episode Thumbnail");
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
                EpisodeController.modifImg(DataHolderEpisode.getSelectedEpisode(),selectedFile);
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
                EpisodeController.modifSynopsis(DataHolderEpisode.getSelectedEpisode(),selectedFile);
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

    public void onEditVideo(ActionEvent actionEvent) throws SQLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Video");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter videoFilter = new FileChooser.ExtensionFilter("MP4 videos", "*.mp4");
        fileChooser.getExtensionFilters().add(videoFilter);
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (extension.equals("mp4")) {
                EpisodeController.modifVideo(DataHolderEpisode.getSelectedEpisode(),selectedFile);
                showMessage(AlertText,"Video Changed successfully");

            } else {
                // If the selected image does not have the required dimensions, display an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Video");
                alert.setContentText("Please select a valid Video");
                alert.showAndWait();
            }
        }
    }
    
    public void OnDebutDateBtn(ActionEvent actionEvent) throws SQLException {
        if (DebutDatePicker.getValue().equals(DataHolderEpisode.getSelectedEpisode().getDebutDate())) {
            showMessage(AlertText, "You didnt change the DebutDate");
        } else if (DebutDatePicker.getValue()==null) {
            showMessage(AlertText, "Your new DebutDate is Empty");
        } else {
            EpisodeController.modifDebutDate(DataHolderEpisode.getSelectedEpisode(),DebutDatePicker.getValue());
            DataHolderEpisode.getSelectedEpisode().setDebutDate(DebutDatePicker.getValue());
            DebutDateLabel.setText(DebutDatePicker.getValue().toString());
            showMessage(AlertText,"Attribute changed successfully");
        }
    }

    public void OnPremiereDateBtn(ActionEvent actionEvent) throws SQLException {
        if (PremiereDatePicker.getValue().equals(DataHolderEpisode.getSelectedEpisode().getPremiereDate())) {
            showMessage(AlertText, "You didnt change the PremiereDate");
        } else if (PremiereDatePicker.getValue()==null) {
            showMessage(AlertText, "Your new PremiereDate is Empty");
        } else if (PremiereDatePicker.getValue().isBefore(LocalDate.now())) {
            showMessage(AlertText, "PremiereDate must be put in the future");
        } else {
            EpisodeController.modifPremiereDate(DataHolderEpisode.getSelectedEpisode(),PremiereDatePicker.getValue());
            DataHolderEpisode.getSelectedEpisode().setPremiereDate(PremiereDatePicker.getValue());
            PremiereDateLabel.setText(PremiereDatePicker.getValue().toString());
            showMessage(AlertText,"Attribute changed successfully");
        }
    }

    public void ChangeDescriptionBtn(ActionEvent actionEvent) throws SQLException {
        if (New_Description.getText().isEmpty()) {
            showMessage(AlertText,"Episode must have a description");
        } else if (New_Description.getText().equals(DataHolderEpisode.getSelectedEpisode().getDescription())) {
            showMessage(AlertText,"You didn't change the description");
        } else {
            EpisodeController.modifDescription(DataHolderEpisode.getSelectedEpisode(),New_Description.getText() );
            DataHolderEpisode.getSelectedEpisode().setDescription(New_Description.getText() );
            Old_Description.setText(New_Description.getText());
            showMessage(AlertText,"Attribute changed successfully");

        }
    }

    public void OnEpisodeNameBtn(ActionEvent actionEvent) throws SQLException {
        if (EpisodeName.getText().isEmpty()) {
            showMessage(AlertText,"Your New Episode Title field is empty");
        } else {
            EpisodeController.modifNom(DataHolderEpisode.getSelectedEpisode(),EpisodeName.getText());
            DataHolderEpisode.getSelectedEpisode().setName(EpisodeName.getText());
            Title.setText(EpisodeName.getText());
            showMessage(AlertText,"Attribute changed successfully");

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IconSetter(BackBtn,"src/main/resources/Images/Design/BackButton.png",70);

        Title.setText(DataHolderEpisode.getSelectedEpisode().getName());
        RatingLabel.setText(Math.round(ScoreEpisodeController.GetEpisodeScore(DataHolderEpisode.getSelectedEpisode()))+"%");
        DebutDateLabel.setText(DataHolderEpisode.getSelectedEpisode().getDebutDate().toString());
        PremiereDateLabel.setText(DataHolderEpisode.getSelectedEpisode().getPremiereDate().toString());
        Old_Description.setText(DataHolderEpisode.getSelectedEpisode().getDescription());
        Thumbnail.setImage(new Image(DataHolderEpisode.getSelectedEpisode().getImage().toURI().toString()));
        Thumbnail.setFitWidth(240);
        Thumbnail.setFitHeight(135);
        ImageClipper(Thumbnail);

    }


    private void showMessage(Label label, String message) {
        label.setText(message);
        label.setOpacity(1);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            label.setOpacity(0);
        }));
        timeline.play();
    }



}
