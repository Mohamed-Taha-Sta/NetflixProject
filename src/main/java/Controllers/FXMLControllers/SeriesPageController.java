package Controllers.FXMLControllers;

import Controllers.SerieController;
import Entities.Serie;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.*;
import static Utils.RepeatableFunction.IconSetter;

public class SeriesPageController implements Initializable {
    public static List<Serie> series;

    public VBox SeriesViewer = new VBox();
    public TextField searchBar;


    public Button homeButton;
    public Button seriesButoon;
    public Button filmButton;
    public Label welcome;
    public Button SearchButton;
    public  CheckComboBox<String> GenresSelector;
    public ComboBox<String> YearSelect;


    @FXML
    public void OnFilmClick() throws Exception {
        HelloApplication.SetRoot("FilmPage");
    }

    public void OnHomeClick() throws Exception {
        HelloApplication.SetRoot("HomePage");
    }

    public void handleImageClick(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        Serie selectedSerie = null;
        for (Serie s : series) {
            if (imageView.getImage().getUrl().equals(String.valueOf(s.getImg().toURI()))) {
                selectedSerie = s;
                break;
            }
        }
        if (selectedSerie != null) {
            try {
                DataHolderSeries.setSelectedSeries(selectedSerie);
                DataHolderSeries.setSelectedSeries(SerieController.GetSerieByID(DataHolderSeries.getSelectedSeries().getId()).get(0));
                SerieViewController.setPath("SeriesPage");
                HelloApplication.SetRoot("SerieView");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void ShowSeries(List<Serie> toShow) {

        if (toShow == null || toShow.isEmpty()) {
            System.out.println("Nothing to show");
        } else {
            HBox shelf = new HBox();
            shelf.setSpacing(20);
            shelf.setAlignment(Pos.CENTER_LEFT);
            int counter = 0;
            for (Serie s : toShow) {
                ImageView imgView = new ImageView();
                ImageSetter(imgView, s.getImg().toURI().toString(), 176, 99);
                ImageClipper(imgView);
                imgView.setCursor(Cursor.cursor("hand"));
                imgView.setOnMouseClicked(this::handleImageClick);
                shelf.getChildren().add(imgView);
                counter++;
                if (counter == 6) {
                    SeriesViewer.getChildren().add(shelf);
                    shelf = new HBox();
                    shelf.setSpacing(20);
                    shelf.setAlignment(Pos.CENTER_LEFT);
                    counter = 0;
                }

            }
            if (counter > 0) {
                SeriesViewer.getChildren().add(shelf);
            }
        }
    }

    public List<Serie> searchSeries(List<Serie> seriesList, String searchText, List<String> selectedGenres, String selectedYear) {
        if (searchText.isEmpty() && selectedGenres.isEmpty() && (selectedYear == null || selectedYear.equals("All"))) {
            return seriesList;
        }
        List<Serie> filteredList = new ArrayList<>();
        for (Serie serie : seriesList) {
            int serieYear = serie.getAnnerdesortie().getYear();
            int selectedYearInt = selectedYear == null || selectedYear.equals("All") ? -1 : Integer.parseInt(selectedYear);
            if ((searchText.isEmpty() || serie.getNom().toLowerCase().contains(searchText.toLowerCase())) &&
                    (selectedGenres.isEmpty() || new HashSet<>(serie.getListegenre()).containsAll(selectedGenres)) &&
                    (selectedYearInt == -1 || serieYear == selectedYearInt)) {
                filteredList.add(serie);
            }
        }
        return filteredList;
    }




    public void OnSearch(){
        List<Serie> filteredSeries;
        filteredSeries=searchSeries(series,searchBar.getText(),GenresSelector.getCheckModel().getCheckedItems(),YearSelect.getValue());
        SeriesViewer.getChildren().clear();
        ShowSeries(filteredSeries);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IconSetter(SearchButton,"src/main/resources/Images/HomePage/search.png",20);
        IconSetter(homeButton,"src/main/resources/Images/HomePage/HomeButton.png",40);
        IconSetter(seriesButoon,"src/main/resources/Images/HomePage/Series.png",40);
        IconSetter(filmButton,"src/main/resources/Images/HomePage/Movie.png",40);
        try {
            if ( series==null ||series.isEmpty() ){
                series=new ArrayList<>();
                series = SerieController.GetAllSeries();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

            GenresSelector.getItems().addAll(genreNames);


            yearList.add("All");
            YearSelect.getItems().addAll(yearList);
            YearSelect.setValue("All");


        OnSearch();

    }

    ObservableList<String> yearList = FXCollections.observableArrayList("2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024");

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
}
