package Controllers.FXMLControllers;

import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AddSeriesController implements Initializable {

    public TextField Name;
    public Text AlertText;
    public TextField Thumbnail;
    public TextArea DescriptionBox;
    public TextField Synopsis;
    public DatePicker DebutDate;

    public CheckComboBox<String> GenreSelector;
    public ComboBox<String> CountrySelector;
    public ComboBox<String> LanguageSelector;

    @FXML
    protected void onBack() throws Exception {
        HelloApplication.SetRoot("ProducerLandingPage");
    }

    @FXML
    protected  void onAdd() throws Exception{
        if(Name.getText().isEmpty()){
            AlertText.setText("Series must have a name");
            AlertText.setOpacity(1);
        }
        else if(Name.getText().length()<1){
            AlertText.setText("Series must have a valid name");
            AlertText.setOpacity(1);
        } else if (CountrySelector.getValue()==null) {
            AlertText.setText("Series must have a country");
            AlertText.setOpacity(1);
        } else if (LanguageSelector.getValue()==null) {
            AlertText.setText("Series must have a language");
            AlertText.setOpacity(1);
        } else if (GenreSelector.getCheckModel().isEmpty()) {
            AlertText.setText("Series must have at least 1 genre");
            AlertText.setOpacity(1);
        } else if (Thumbnail.getText().isEmpty()) {
            AlertText.setText("Series must have a thumbnail");
            AlertText.setOpacity(1);
        } else if (Synopsis.getText().isEmpty()) {
            AlertText.setText("Series must have a synopsis");
            AlertText.setOpacity(1);
        } else if (DescriptionBox.getText().isEmpty()) {
            AlertText.setText("Series must have a description");
            AlertText.setOpacity(1);
        } else if (GenreSelector.getCheckModel().getCheckedItems() == null) {
            AlertText.setText("Series must have at least a genre");
            AlertText.setOpacity(1);
        }  else if (DebutDate.getValue() == null) {
            AlertText.setText("Series must have a Debut Date");
            AlertText.setOpacity(1);
        } else {
            DataHolderSeries.setSeriesName(Name.getText());
            DataHolderSeries.setCountryOfOrigin(CountrySelector.getValue());
            DataHolderSeries.setLanguage(LanguageSelector.getValue());
            DataHolderSeries.setDebutDate(DebutDate.getValue());
            DataHolderSeries.setDescription(DescriptionBox.getText());
            DataHolderSeries.setGenreList(GenreSelector.getCheckModel().getCheckedItems());
            HelloApplication.SetRoot("PickActorsPage_afterAddingContent");
        }


    }

//    @FXML
//    public void ThumbnailSelect(javafx.scene.input.MouseEvent event) throws Exception {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Choose Thumbnail");
//        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
//        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.jpeg", "*.png");
//        fileChooser.getExtensionFilters().add(imageFilter);
//        File selectedFile = fileChooser.showOpenDialog(null);
//        //Taha i added the ability of checking the aspect ration
//        double targetAspectRatio = 16.0 / 9.0;
//        if (selectedFile != null) {
//            Image image = new Image(selectedFile.toURI().toString());
//
////            int width = (int) image.getWidth();
////            int height = (int) image.getHeight();
//            double width = image.getWidth();
//            double height = image.getHeight();
//            double aspectRatio = width / height;
//            if(aspectRatio==targetAspectRatio){
//                DataHolderSeries.setThumbnail(selectedFile);
//                Thumbnail.setText(selectedFile.toURI().toString());
//
//            }
//            else{
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error");
//                alert.setHeaderText("Invalid Image Aspect Ratio");
//                alert.setContentText("Please select an image with an aspect ratio of 16:9.");
//                alert.showAndWait();
//            }
//            if (width <= 1920 && height <= 1080) {
//                DataHolderSeries.setThumbnail(selectedFile);
//                Thumbnail.setText(selectedFile.toURI().toString());
//
//            } else {
//                // If the selected image does not have the required dimensions, display an error message
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error");
//                alert.setHeaderText("Invalid Image Size");
//                alert.setContentText("Please select an image with dimensions of 1920*1080 pixels.");
//                alert.showAndWait();
//            }
//        }
//    }
    //Tiny changes
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
        } else if (width > 1920 || height > 1080) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Image Size");
            alert.setContentText("Please select an image with dimensions of 1920*1080 pixels or less.");
            alert.showAndWait();
        } else {
            DataHolderSeries.setThumbnail(selectedFile);
            Thumbnail.setText(selectedFile.toURI().toString());
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
                DataHolderSeries.setSynopsis(selectedFile);
                Synopsis.setText(selectedFile.toURI().toString());

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

    }
}
