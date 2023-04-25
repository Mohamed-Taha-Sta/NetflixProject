package Controllers.FXMLControllers;

import Utils.DataHolderFilm;
import com.example.netflixproject.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.isTextExceedingLength;
import static Utils.RepeatableFunction.showErrorMessage;

public class AddFilmController implements Initializable {

    public TextField Name;
    public CheckComboBox<String> GenreSelector;
    public ComboBox<String> CountrySelector;
    public ComboBox<String> LanguageSelector;


    public TextField Thumbnail;
    public TextField Synopsis;
    public TextField Video;
    public TextArea DescriptionBox;
    public DatePicker DebutDate;
    public Label AlertText;


    @FXML
    protected void onBack() throws Exception {
        DataHolderFilm.setSelectedFilm(null);
        HelloApplication.SetRoot("ProducerLandingPage");
    }

    @FXML
    protected void onAdd() throws Exception {
        if (Name.getText().isEmpty()) {
            showErrorMessage(AlertText, "Film must have a name");
        } else if (isTextExceedingLength(Name, 50)) {
            showErrorMessage(AlertText, "Name field too long");
        } else if (DescriptionBox.getText().isEmpty()) {
            showErrorMessage(AlertText, "Film must have a Description");
        } else if (isTextExceedingLength(DescriptionBox,150)) {
            showErrorMessage(AlertText,"Description is longer then the film");
        } else if (DebutDate.getValue() == null) {
            showErrorMessage(AlertText, "Film must have a valid DebutDate");
        } else if (CountrySelector.getValue() == null) {
            showErrorMessage(AlertText, "Film must have a country");
        } else if (LanguageSelector.getValue() == null) {
            showErrorMessage(AlertText, "Film must have a language");
        } else if (GenreSelector.getCheckModel().isEmpty()) {
            showErrorMessage(AlertText, "Film must have at least 1 genre");
        } else if (Thumbnail.getText().isEmpty()) {
            showErrorMessage(AlertText, "Film must have a Thumbnail");
        } else {
            DataHolderFilm.setName(Name.getText());
            DataHolderFilm.setDescription(DescriptionBox.getText());
            DataHolderFilm.setDebutDate(DebutDate.getValue());
            DataHolderFilm.setGenreList(GenreSelector.getCheckModel().getCheckedItems());
            DataHolderFilm.setLanguage(LanguageSelector.getValue());
            DataHolderFilm.setCountryOfOrigin(CountrySelector.getValue());
            HelloApplication.SetRoot("PickActorsPage_afterAddingFilm");
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
                    DataHolderFilm.setThumbnail(selectedFile);
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
                DataHolderFilm.setSynopsis(selectedFile);
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
        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (extension.equals("mp4")) {
                DataHolderFilm.setVideo(selectedFile);
                Video.setText(selectedFile.toURI().toString());
                Media media = new Media(selectedFile.toURI().toString());
                String duration = VideoPlayerController.getTime(media.getDuration());
                DataHolderFilm.setDuration(duration);

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

        ObservableList<String> genreNames = FXCollections.observableArrayList(
                "Action",
                "Adventure",
                "Animation",
                "Biography",
                "Comedy",
                "Crime",
                "Documentary",
                "Drama",
                "Family",
                "Fantasy",
                "Film-Noir",
                "Game-Show",
                "History",
                "Horror",
                "Music",
                "Musical",
                "Mystery",
                "News",
                "Reality-TV",
                "Romance",
                "Sci-Fi",
                "Sport",
                "Talk-Show",
                "Thriller",
                "War",
                "Western"
        );

        ObservableList<String> languages = FXCollections.observableArrayList(
                "Arabic",
                "Bengali",
                "Chinese (Mandarin)",
                "English",
                "French",
                "German",
                "Hindi",
                "Indonesian",
                "Italian",
                "Japanese",
                "Korean",
                "Malay",
                "Portuguese",
                "Russian",
                "Spanish",
                "Swahili",
                "Thai",
                "Turkish",
                "Vietnamese"
        );


        ObservableList<String> countries = FXCollections.observableArrayList(
                "Australia",
                "Austria",
                "Belgium",
                "Brazil",
                "Canada",
                "China",
                "Denmark",
                "Egypt",
                "France",
                "Germany",
                "Hong Kong",
                "India",
                "Indonesia",
                "Iran",
                "Ireland",
                "Italy",
                "Japan",
                "Malaysia",
                "Mexico",
                "Netherlands",
                "Norway",
                "Philippines",
                "Poland",
                "Russia",
                "Saudi Arabia",
                "Singapore",
                "South Africa",
                "South Korea",
                "Spain",
                "Sweden",
                "Switzerland",
                "Turkey",
                "Tunisia",
                "United Arab Emirates",
                "United Kingdom",
                "United States"
        );


        GenreSelector.getItems().addAll(genreNames);
        GenreSelector.setTitle("Genres");

        CountrySelector.getItems().addAll(countries);

        LanguageSelector.getItems().addAll(languages);

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
