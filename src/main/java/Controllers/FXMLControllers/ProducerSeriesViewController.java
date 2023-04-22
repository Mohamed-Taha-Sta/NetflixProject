package Controllers.FXMLControllers;

import Controllers.SeasonController;
import Controllers.SerieController;
import Controllers.UserController;
import Entities.Season;
import Entities.Serie;
import Utils.DataHolder;
import Utils.DataHolderSeason;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.*;

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
    private List<Season> seasonList;
    public Label RatingLabel;
    public Label DirectLabel;
    public Label dateLabel;
    public Label genreLabel;
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
                DataHolderSeason.setSelectedSeason(selectedSeason);
//                HelloApplication.SetRoot("SerView");
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
            showErrorMessage(AlertText,"Your Series Title field is empty");
        } else {
            SerieController.modifnom(DataHolderSeries.getSelectedSeries(),SeriesName.getText());
            DataHolderSeries.getSelectedSeries().setNom(SeriesName.getText());
            Title.setText(SeriesName.getText());
        }
    }

    public void OnDebutDateBtn() throws SQLException {
        if (DebutDatePicker.getValue().equals(DataHolderSeries.getSelectedSeries().getAnnerdesortie())) {
            showErrorMessage(AlertText, "You didnt change the Debut Date");
        } else {
            SerieController.modifAnnerdesoritie(DataHolderSeries.getSelectedSeries(),DebutDatePicker.getValue());
            DataHolderSeries.getSelectedSeries().setAnnerdesortie(DebutDatePicker.getValue());

        }
    }

    public void onGenreBtn() throws SQLException {
        if (GenreSelector.getCheckModel().getCheckedItems() == null) {
            showErrorMessage(AlertText,"Series must have at least a genre");
        }else
        {
            SerieController.modiflistegenre(DataHolderSeries.getSelectedSeries(),GenreSelector.getCheckModel().getCheckedItems());
            DataHolderSeries.getSelectedSeries().setListegenre(GenreSelector.getCheckModel().getCheckedItems());
        }
    }

    public void onCountryBtn() throws SQLException {
        if (CountrySelector.getValue()==null) {
            showErrorMessage(AlertText,"Series must have a country");
        }else
        {
            SerieController.modifpaysoregine(DataHolderSeries.getSelectedSeries(), (String) CountrySelector.getValue());
            DataHolderSeries.getSelectedSeries().setPaysorigine((String) CountrySelector.getValue());
            CountryOfOrigin.setText(DataHolderSeries.getSelectedSeries().getPaysorigine());
        }
    }

    public void onLanguageBtn() throws SQLException {
        if (LanguageSelector.getValue()==null) {
            showErrorMessage(AlertText,"Series must have a language");
        }else
        {
            SerieController.modiflangues(DataHolderSeries.getSelectedSeries(), (String) LanguageSelector.getValue());
            DataHolderSeries.getSelectedSeries().setLangue((String) LanguageSelector.getValue());
            Language.setText(DataHolderSeries.getSelectedSeries().getLangue());
        }
    }


    public void ChangeDescriptionBtn() throws SQLException {
        if (New_Description.getText().isEmpty()) {
            showErrorMessage(AlertText,"Series must have a description");
        } else
        {
            SerieController.modifdescription(DataHolderSeries.getSelectedSeries(),New_Description.getText() );
            DataHolderSeries.getSelectedSeries().setDescription(New_Description.getText() );
            Old_Description.setText(New_Description.getText());
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

        SeasonViewer.setSpacing(15);

        Image img=new Image(new File("src/main/resources/Images/theWitcher.jpg").toURI().toString());

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


    private void showErrorMessage(Label label, String message) {
        label.setText(message);
        label.setOpacity(1);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            label.setOpacity(0);
        }));
        timeline.play();
    }



}
