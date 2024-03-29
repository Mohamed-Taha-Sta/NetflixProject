package Controllers.FXMLControllers;

import Controllers.SeasonController;
import Entities.Season;
import Utils.DataHolderEpisode;
import Utils.DataHolderSeason;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.isTextExceedingLength;

public class AddSeasonController implements Initializable {

    public TextField Name;
    public Text AlertText;
    public TextField Thumbnail;
    public TextArea Description;
    public TextField Synopsis;
    public DatePicker DebutDate;


    @FXML
    protected void OnBack() throws Exception {
        HelloApplication.SetRoot(DataHolderSeason.getPreviousPage());
    }

    @FXML
    protected void onAdd() throws Exception {
        if (Name.getText().isEmpty()) {
            AlertText.setText("Season must have a name");
            AlertText.setOpacity(1);
        } else if (Name.getText().length() < 1) {
            AlertText.setText("Season must have a valid name");
            AlertText.setOpacity(1);
        } else if (isTextExceedingLength(Name, 50)) {
            AlertText.setText("Season name too long");
            AlertText.setOpacity(1);
        } else if (Description.getText().isEmpty()) {
            AlertText.setText("Season must have a description");
            AlertText.setOpacity(1);
        } else if (Description.getText().length() < 1) {
            AlertText.setText("Season must have a valid description");
            AlertText.setOpacity(1);
        } else if (isTextExceedingLength(Description, 150)) {
            AlertText.setText("Season description too long");
            AlertText.setOpacity(1);
        } else if (Thumbnail.getText().isEmpty()) {
            AlertText.setText("Season must have a thumbnail");
            AlertText.setOpacity(1);
        } else if (Synopsis.getText().isEmpty()) {
            AlertText.setText("Season must have a synopsis");
            AlertText.setOpacity(1);
        } else if (DebutDate.getValue() == null) {
            AlertText.setText("Season must have a Debut Date");
            AlertText.setOpacity(1);
        } else {
            DataHolderSeason.setName(Name.getText());
            DataHolderSeason.setDescription(Description.getText());
            DataHolderSeason.setDebutDate(DebutDate.getValue());
            long idSeason = SeasonController.AddSeason(new Season(DataHolderSeries.getIDSerie(), DataHolderSeason.getDescription(),
                    DataHolderSeason.getName(), DataHolderSeason.getDebutDate(), DataHolderSeason.getThumbnail(),
                    DataHolderSeason.getSynopsis()));
            DataHolderSeason.setIDSeason(idSeason);
            DataHolderEpisode.setPreviousPage("AddSeason");
            HelloApplication.SetRoot("AddEpisode");
        }
    }

    @FXML
    public void ThumbnailSelect() {
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
                DataHolderSeason.setThumbnail(selectedFile);
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
    public void SynopsisSelect() {
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
                DataHolderSeason.setSynopsis(selectedFile);
                Synopsis.setText(selectedFile.toURI().toString());

            } else {
                // If the selected image does not have the required dimensions, display an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Image Size");
                alert.setContentText("Please select a valid Synopsis.");
                alert.showAndWait();
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Thumbnail.setOnMouseClicked(event -> {
            try {
                ThumbnailSelect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Synopsis.setOnMouseClicked(event -> {
            try {
                SynopsisSelect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
