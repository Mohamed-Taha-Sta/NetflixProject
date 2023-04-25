package Controllers.FXMLControllers;

import Controllers.FilmController;
import Entities.Genre;
import Utils.DataHolderFilm;
import com.example.netflixproject.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.*;
import static javafx.collections.FXCollections.observableArrayList;

public class ProducerFilmViewController implements Initializable {

    public ImageView Thumbnail;
    public Label AlertText;
    public TextField FilmName;
    public Label Title;
    public DatePicker DebutDatePicker;
    public Label DebutDateLabel;
    public CheckComboBox<String> GenreSelector;
    public ComboBox<String> CountrySelector;
    public Label CountryOfOrigin;
    public ComboBox<String> LanguageSelector;
    public TextArea Old_Description;
    public TextArea New_Description;
    public Label Language;
    public Button ChangeDescriptionBtn;
    public Label RatingLabel;
    public Button BackBtn;

    ObservableList<Genre> genres = observableArrayList(
            new Genre("Action"),
            new Genre("Adventure"),
            new Genre("Animation"),
            new Genre("Biography"),
            new Genre("Comedy"),
            new Genre("Crime"),
            new Genre("Documentary"),
            new Genre("Drama"),
            new Genre("Family"),
            new Genre("Fantasy"),
            new Genre("Film-Noir"),
            new Genre("Game-Show"),
            new Genre("History"),
            new Genre("Horror"),
            new Genre("Music"),
            new Genre("Musical"),
            new Genre("Mystery"),
            new Genre("News"),
            new Genre("Reality-TV"),
            new Genre("Romance"),
            new Genre("Sci-Fi"),
            new Genre("Sport"),
            new Genre("Talk-Show"),
            new Genre("Thriller"),
            new Genre("War"),
            new Genre("Western")
    );


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


    public void onBack() throws Exception {
        HelloApplication.SetRoot("ProducerLandingPage");
    }

    public void onEditFilmImg() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a Film Thumbnail");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.jpeg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());

            int width = (int) image.getWidth();
            int height = (int) image.getHeight();
            double aspectRatio = (double) width / height; // calculate aspect ratio

            if (width <= 1920 && height <= 1080) {
                if (aspectRatio <= 16.0 / 9.0) {
                    Thumbnail.setImage(image);
                    FilmController.modifimg(DataHolderFilm.getSelectedFilm(), selectedFile);
                    showErrorMessage(AlertText, "Thumbnail Changed successfully");
                } else {
                    // If the selected image does not have the required aspect ratio, display an error message
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Aspect Ratio");
                    alert.setContentText("Please select an image with an aspect ratio of 16:9 or less.");
                    alert.showAndWait();
                }
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


    public void FilmOverviewBtn() throws Exception {
        HelloApplication.SetRoot("CheckStatsProdFilm");
    }

    public void onEditVideo() {
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
                FilmController.modiffilmvedio(DataHolderFilm.getSelectedFilm(), selectedFile);
                showErrorMessage(AlertText, "Video Changed successfully");

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


    public void onEdtSynosisBtn() {
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
                FilmController.modifsynop(DataHolderFilm.getSelectedFilm(), selectedFile);
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

    public void OnFilmBtn() {
        if (FilmName.getText().isEmpty()) {
            showErrorMessage(AlertText, "Your New Film Title field is empty");
        } else if (isTextExceedingLength(FilmName, 50)) {
            showErrorMessage(AlertText, "Your new film name is waaayyy too long");
        } else {
            FilmController.modifnom(DataHolderFilm.getSelectedFilm(), FilmName.getText());
            DataHolderFilm.getSelectedFilm().setNom(FilmName.getText());
            Title.setText(FilmName.getText());
            showErrorMessage(AlertText, "Attribute changed successfully");
        }
    }

    public void OnDebutDateBtn() {
        if (DebutDatePicker.getValue().equals(DataHolderFilm.getSelectedFilm().getAnnerdesortie())) {
            showErrorMessage(AlertText, "You didnt change the Debut Date");
        } else if (DebutDatePicker.getValue() == null) {
            showErrorMessage(AlertText, "Your new DebutDate is Empty");
        } else {
            FilmController.modifAnnerdesoritie(DataHolderFilm.getSelectedFilm(), DebutDatePicker.getValue());
            DataHolderFilm.getSelectedFilm().setAnnerdesortie(DebutDatePicker.getValue());
            DebutDateLabel.setText(DebutDatePicker.getValue().toString());
            showErrorMessage(AlertText, "Attribute changed successfully");
        }
    }

    public void onGenreBtn() {
        if (GenreSelector.getCheckModel().getCheckedItems().isEmpty()) {
            showErrorMessage(AlertText, "Film must have at least a genre");
        } else if (GenreSelector.getCheckModel().getCheckedItems().equals(DataHolderFilm.getSelectedFilm().getListegenre())) {
            showErrorMessage(AlertText, "You didn't change the genre");
        } else {
            FilmController.modiflistegenre(DataHolderFilm.getSelectedFilm(), GenreSelector.getCheckModel().getCheckedItems());
            DataHolderFilm.getSelectedFilm().setListegenre(GenreSelector.getCheckModel().getCheckedItems());
            showErrorMessage(AlertText, "Attribute changed successfully");
        }
    }

    public void onCountryBtn() {
        if (CountrySelector.getValue() == null) {
            showErrorMessage(AlertText, "New Film must have a country");
        } else if (CountrySelector.getValue().equals(DataHolderFilm.getSelectedFilm().getPaysorigine())) {
            showErrorMessage(AlertText, "You didn't change the country");
        } else {
            FilmController.modifpaysoregine(DataHolderFilm.getSelectedFilm(), CountrySelector.getValue());
            DataHolderFilm.getSelectedFilm().setPaysorigine(CountrySelector.getValue());
            CountryOfOrigin.setText(DataHolderFilm.getSelectedFilm().getPaysorigine());
            showErrorMessage(AlertText, "Attribute changed successfully");

        }
    }

    public void onLanguageBtn() {
        if (LanguageSelector.getValue() == null) {
            showErrorMessage(AlertText, "Film must have a language");
        } else if (LanguageSelector.getValue().equals(DataHolderFilm.getSelectedFilm().getLangue())) {
            showErrorMessage(AlertText, "You didn't change the language");
        } else {
            FilmController.modiflangues(DataHolderFilm.getSelectedFilm(), LanguageSelector.getValue());
            DataHolderFilm.getSelectedFilm().setLangue(LanguageSelector.getValue());
            Language.setText(DataHolderFilm.getSelectedFilm().getLangue());
            showErrorMessage(AlertText, "Attribute changed successfully");
        }
    }

    public void ChangeDescriptionBtn() {
        if (New_Description.getText().isEmpty()) {
            showErrorMessage(AlertText, "Film must have a description");
        } else if (isTextExceedingLength(New_Description, 150)) {
            showErrorMessage(AlertText, "Your film description is longer than the film");
        } else if (New_Description.getText().equals(DataHolderFilm.getSelectedFilm().getDescription())) {
            showErrorMessage(AlertText, "You didn't change the Description");
        } else {
            FilmController.modifdescription(DataHolderFilm.getSelectedFilm(), New_Description.getText());
            DataHolderFilm.getSelectedFilm().setDescription(New_Description.getText());
            Old_Description.setText(New_Description.getText());
            showErrorMessage(AlertText, "Attribute changed successfully");

        }
    }

    public void LoadSelectedGenres() {
        List<String> filmGenre = DataHolderFilm.getSelectedFilm().getListegenre();
        System.out.println("GENRES LIST" + filmGenre);
        for (String genre : filmGenre) {
            for (Genre genreName : genres) {
                if (genre.equals(genreName.getNom())) {
                    genreName.getSelect().setSelected(true);
                    break;
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        GenreSelector.getItems().addAll(genreNames);
        GenreSelector.setTitle("Genres");
        LoadSelectedGenres();

        CountrySelector.getItems().addAll(countries);

        LanguageSelector.getItems().addAll(languages);

        Title.setText(DataHolderFilm.getSelectedFilm().getNom());
        CountryOfOrigin.setText(DataHolderFilm.getSelectedFilm().getPaysorigine());
        Language.setText(DataHolderFilm.getSelectedFilm().getLangue());
        Thumbnail.setImage(new Image(DataHolderFilm.getSelectedFilm().getImg().toURI().toString()));
        Thumbnail.setFitWidth(240);
        Thumbnail.setFitHeight(135);
        Old_Description.setText(DataHolderFilm.getSelectedFilm().getDescription());
        DebutDateLabel.setText(DataHolderFilm.getSelectedFilm().getAnnerdesortie().toString());
        RatingLabel.setText(FilmController.getscorepourcantage(DataHolderFilm.getSelectedFilm()) + " %");
        ImageClipper(Thumbnail);
        IconSetter(BackBtn, "src/main/resources/Images/Design/BackButton.png", 70);


    }


}
