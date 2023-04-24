package Controllers.FXMLControllers;

import Controllers.SeasonController;
import Controllers.SerieController;
import Entities.Genre;
import Entities.Season;
import Utils.DataHolderSeason;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.controlsfx.control.CheckComboBox;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.*;
import static javafx.collections.FXCollections.observableArrayList;

public class ProducerSeriesViewController implements Initializable {

    public Button homeButton;
    public Button seriesButoon;
    public Button filmButton;
    public Label AlertText;
    public CheckComboBox GenreSelector;
    public ComboBox CountrySelector;
    public ComboBox LanguageSelector;
    public TextField SeriesName;
    public Label Title;
    public DatePicker DebutDatePicker;
    public TextArea Old_Description;
    public TextArea New_Description;
    public Label CountryOfOrigin;
    public Label Language;
    public Label DebutDateLabel;
    private List<Season> seasonList;
    public Label RatingLabel;

    public TextArea Description;
    public ImageView Thumbnail;
    public Button BackBtn;
    public HBox SeasonViewer = new HBox();
    public Button SynopsisBtn;


//    public void OnDebutDateBtn(ActionEvent actionEvent) {
//    }
//
//    public void OnSeriesNameBtn(ActionEvent actionEvent) {
//    }

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


    @FXML
    public void handleImageClick(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        Season selectedSeason = null;
        for (Season s : seasonList) {
            if (imageView.getImage().getUrl().equals(String.valueOf(s.getThumbnail().toURI()))) {
                selectedSeason = s;
                break;
            }
        }
        if (selectedSeason != null) {
            try {
                DataHolderSeason.setSelectedSeason(SeasonController.FindSeasonID(selectedSeason.getID()).get(0));
                HelloApplication.SetRoot("ProducerSeasonView");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void onBack() throws Exception {
        HelloApplication.SetRoot("ProducerLandingPage");
    }

    public void onAddSeason() throws Exception {
        DataHolderSeason.setPreviousPage("ProducerSeriesView");
        DataHolderSeries.setIDSerie(DataHolderSeries.getSelectedSeries().getId());
        HelloApplication.SetRoot("AddSeason");

    }


    public void OnSeriesNameBtn() throws SQLException {
        if (SeriesName.getText().isEmpty()) {
            showMessage(AlertText,"Your New Series Title field is empty");
        } else if (isTextExceedingLength(SeriesName,50)) {
            showMessage(AlertText,"Your New Series Title field is Toooo long");
        } else {
            SerieController.modifnom(DataHolderSeries.getSelectedSeries(),SeriesName.getText());
            DataHolderSeries.getSelectedSeries().setNom(SeriesName.getText());
            Title.setText(SeriesName.getText());
            showMessage(AlertText,"Attribute changed successfully");

        }
    }

    //JJJJEEEEEEEESSSSSSSEEEEEEEERRRRR LOOOOOOOOOK into it

    public void LoadSelectedGenres(){
        List<String> seriesGenre= DataHolderSeries.getSelectedSeries().getListegenre();
        System.out.println("GENRES LIST"+seriesGenre);
        for(String genre:seriesGenre){
            for(Genre genreName:genres){
                if(genre.equals(genreName.getNom())){
                    genreName.getSelect().setSelected(true);
                    break;
                }
            }
        }
    }

    public void SeriesOverviewBtn() throws Exception {
        HelloApplication.SetRoot("CheckStatsProdSerie");
    }


    public void OnDebutDateBtn() throws SQLException {
        if (DebutDatePicker.getValue().equals(DataHolderSeries.getSelectedSeries().getAnnerdesortie())) {
            showMessage(AlertText, "You didnt change the Debut Date");
        } else if (DebutDatePicker.getValue()==null) {
            showMessage(AlertText, "Your new DebutDate is Empty");
        } else {
            System.out.println(DebutDatePicker.getValue());
            SerieController.modifAnnerdesoritie(DataHolderSeries.getSelectedSeries(),DebutDatePicker.getValue());
            DataHolderSeries.getSelectedSeries().setAnnerdesortie(DebutDatePicker.getValue());
            DebutDateLabel.setText(DebutDatePicker.getValue().toString());
            showMessage(AlertText,"Attribute changed successfully");

        }
    }

    public void onGenreBtn() throws SQLException {
        if (GenreSelector.getCheckModel().getCheckedItems().isEmpty()) {
            showMessage(AlertText,"Series must have at least a genre");
        } else if (GenreSelector.getCheckModel().getCheckedItems().equals(DataHolderSeries.getSelectedSeries().getListegenre())) {
            showMessage(AlertText,"You didn't change the genre");
        } else {
            SerieController.modiflistegenre(DataHolderSeries.getSelectedSeries(),GenreSelector.getCheckModel().getCheckedItems());
            DataHolderSeries.getSelectedSeries().setListegenre(GenreSelector.getCheckModel().getCheckedItems());
            showMessage(AlertText,"Attribute changed successfully");

        }
    }

    public void onCountryBtn() throws SQLException {
        if (CountrySelector.getValue()==null) {
            showMessage(AlertText,"New Series must have a country");
        } else if (CountrySelector.getValue().equals(DataHolderSeries.getSelectedSeries().getPaysorigine())) {
            showMessage(AlertText,"You didn't change the country");
        } else {
            SerieController.modifpaysoregine(DataHolderSeries.getSelectedSeries(), (String) CountrySelector.getValue());
            DataHolderSeries.getSelectedSeries().setPaysorigine((String) CountrySelector.getValue());
            CountryOfOrigin.setText(DataHolderSeries.getSelectedSeries().getPaysorigine());
            showMessage(AlertText,"Attribute changed successfully");

        }
    }

    public void onLanguageBtn() throws SQLException {
        if (LanguageSelector.getValue()==null) {
            showMessage(AlertText,"Series must have a language");
        } else if (LanguageSelector.getValue().equals(DataHolderSeries.getSelectedSeries().getLangue())) {
            showMessage(AlertText,"You didn't change the language");
        } else {
            SerieController.modiflangues(DataHolderSeries.getSelectedSeries(), (String) LanguageSelector.getValue());
            DataHolderSeries.getSelectedSeries().setLangue((String) LanguageSelector.getValue());
            Language.setText(DataHolderSeries.getSelectedSeries().getLangue());
            showMessage(AlertText,"Attribute changed successfully");

        }
    }


    public void ChangeDescriptionBtn() throws SQLException {
        if (New_Description.getText().isEmpty()) {
            showMessage(AlertText,"Series must have a description");
        } else if (isTextExceedingLength(New_Description,150)) {
            showMessage(AlertText,"Your Description is longer than series");
        } else if (New_Description.getText().equals(DataHolderSeries.getDescription())) {
            showMessage(AlertText,"You didn't change the Description");
        } else {
            SerieController.modifdescription(DataHolderSeries.getSelectedSeries(),New_Description.getText() );
            DataHolderSeries.getSelectedSeries().setDescription(New_Description.getText() );
            Old_Description.setText(New_Description.getText());
            showMessage(AlertText,"Attribute changed successfully");

        }
    }

    public void onEditSerieImg() throws SQLException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a Series Thumbnail");
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
                SerieController.modifimg(DataHolderSeries.getSelectedSeries(),selectedFile);
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
                SerieController.ModifSynopsisSerie(DataHolderSeries.getSelectedSeries(),selectedFile);
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
        LoadSelectedGenres();

        CountrySelector.getItems().addAll(countries);

        LanguageSelector.getItems().addAll(languages);

        SeasonViewer.setSpacing(15);

        try {
            seasonList = SeasonController.FindSeasonSerieID(DataHolderSeries.getSelectedSeries().getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Season season : seasonList)
        {
            ImageView imageView = new ImageView();
//            imageView.setFitHeight(100);
//            imageView.setFitWidth(150);
            System.out.println(season.getThumbnail().toURI());
            ImageSetter(imageView,season.getThumbnail().toURI().toString(),150,100);
            ImageClipper(imageView);
            imageView.setOnMouseClicked(this::handleImageClick);
            SeasonViewer.getChildren().add(imageView);
        }

        Title.setText(DataHolderSeries.getSelectedSeries().getNom());
        CountryOfOrigin.setText(DataHolderSeries.getSelectedSeries().getPaysorigine());
        Language.setText(DataHolderSeries.getSelectedSeries().getLangue());
        Thumbnail.setImage(new Image(DataHolderSeries.getSelectedSeries().getImg().toURI().toString()));
        Thumbnail.setFitWidth(240);
        Thumbnail.setFitHeight(135);
        Old_Description.setText(DataHolderSeries.getSelectedSeries().getDescription());
        DebutDateLabel.setText(DataHolderSeries.getSelectedSeries().getAnnerdesortie().toString());
        try {
            RatingLabel.setText(String.valueOf(SerieController.StreamAverageScore(DataHolderSeries.getSelectedSeries()))+" %");
        } catch (SQLException e) {
            System.out.println("SQL Error getting the Rating");
        } catch (IOException e) {
            System.out.println("IO Error getting the Rating");
        }
        ImageClipper(Thumbnail);
        IconSetter(BackBtn,"src/main/resources/Images/Design/BackButton.png",70);


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
