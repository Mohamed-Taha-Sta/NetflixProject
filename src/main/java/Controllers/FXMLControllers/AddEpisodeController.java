package Controllers.FXMLControllers;

import Controllers.EpisodeController;
import Entities.Episode;
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
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddEpisodeController implements Initializable {

    public TextField Name;
    public Text AlertText;
    public TextField Thumbnail;
    public TextField Synopsis;
    public TextField Video;
    public DatePicker PremiereDate;
    public DatePicker DebutDate;
    public TextArea DescriptionBox;



    @FXML
    protected void OnBack() throws Exception {
        HelloApplication.SetRoot("AddSeason");
    }


    @FXML
    protected  void onAdd() throws Exception{
        if(Name.getText().isEmpty()){
            AlertText.setText("Episode must have a name");
            AlertText.setOpacity(1);
        } else if(Name.getText().length()<1){
            AlertText.setText("Episode must have a valid name");
            AlertText.setOpacity(1);
        } else if (DescriptionBox.getText().isEmpty()) {
            AlertText.setText("Episode must have a Description");
            AlertText.setOpacity(1);
        } else if (DescriptionBox.getText().length()<1) {
            AlertText.setText("Episode must have a valid Description");
            AlertText.setOpacity(1);
        }  else if (PremiereDate.getValue() == null) {
            AlertText.setText("Episode must have a valid PremiereDate");
            AlertText.setOpacity(1);
        } else if (PremiereDate.getValue().isBefore(LocalDate.now())) {
            AlertText.setText("PremiereDate must be put in the future");
            AlertText.setOpacity(1);
        } else if (DebutDate.getValue() == null) {
            AlertText.setText("Episode must have a valid DebutDate");
            AlertText.setOpacity(1);
        } else{
            DataHolderEpisode.setName(Name.getText());
            DataHolderEpisode.setDescription(DescriptionBox.getText());
            DataHolderEpisode.setPremiereDate(PremiereDate.getValue());
            DataHolderEpisode.setDebutDate(DebutDate.getValue());

            long IdEpisode = EpisodeController.AddEpisode(new Episode(DataHolderSeason.getIDSeason(),DataHolderEpisode.getName(),
                    DataHolderEpisode.getDescription(),DataHolderEpisode.getDebutDate(),DataHolderEpisode.getPremiereDate(),
                    DataHolderEpisode.getSynopsis(),DataHolderEpisode.getVideo(),DataHolderEpisode.getThumbnail()));

            DataHolderEpisode.setIDEpisode(IdEpisode);

            HelloApplication.SetRoot("ProducerLandingPage");
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

            int width = (int) image.getWidth();
            int height = (int) image.getHeight();
            if (width <= 1920 && height <= 1080) {
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

    Media media;
    @FXML
    public void VideoSelect(javafx.scene.input.MouseEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Video");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter videoFilter = new FileChooser.ExtensionFilter("MP4 videos", "*.mp4");
        fileChooser.getExtensionFilters().add(videoFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        media=new Media(selectedFile.toURI().toString());
        media.getDuration();
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
