package Controllers.FXMLControllers;

import Controllers.FilmController;
import Controllers.SerieController;
import Entities.Film;
import Entities.Serie;
import Utils.DataHolderFilm;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.*;
import static Utils.RepeatableFunction.IconSetter;

public class FilmPageController implements Initializable {
    @FXML
    public Button homeButton;
    @FXML
    public Button seriesButoon;
    @FXML
    public Button filmButton;
    @FXML
    public Label welcome;
    @FXML
    public TextField searchBar;
    @FXML
    public Button SearchButton;
    @FXML
    public CheckComboBox<String> GenresSelector;
    @FXML
    public ComboBox<String> YearSelect;
    @FXML
    public VBox FilmsViewer;

    public static List<Film> films;

    public void OnHomeClick() throws Exception {
        HelloApplication.SetRoot("HomePage");
    }

    public void OnSeriesClick() throws Exception {
        HelloApplication.SetRoot("SeriesPage");
    }

    public void handleImageClick(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        Film selectedFilm = null;
        for (Film s : films) {
            if (imageView.getImage().getUrl().equals(String.valueOf(s.getImg().toURI()))) {
                selectedFilm = s;
                break;
            }
        }
        if (selectedFilm != null) {
            try {
                DataHolderFilm.setSelectedFilm(selectedFilm);
                FilmViewController.setPath("FilmPage");
                DataHolderFilm.setSelectedFilm(FilmController.FindByID(DataHolderFilm.getSelectedFilm().getId()).get(0));
                HelloApplication.SetRoot("FilmView");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void ShowFilms(List<Film> toShow) {

        if (toShow == null || toShow.isEmpty()) {
            System.out.println("Nothing to show");
        } else {
            HBox shelf = new HBox();
            shelf.setSpacing(20);
            shelf.setAlignment(Pos.CENTER_LEFT);
            int counter = 0;
            for (Film f : toShow) {
                ImageView imgView = new ImageView();
                ImageSetter(imgView, f.getImg().toURI().toString(), 176, 99);
                ImageClipper(imgView);
                imgView.setCursor(Cursor.cursor("hand"));
                imgView.setOnMouseClicked(this::handleImageClick);
                shelf.getChildren().add(imgView);
                counter++;
                if (counter == 6) {
                    FilmsViewer.getChildren().add(shelf);
                    shelf = new HBox();
                    shelf.setSpacing(20);
                    shelf.setAlignment(Pos.CENTER_LEFT);
                    counter = 0;
                }

            }
            if (counter > 0) {
                FilmsViewer.getChildren().add(shelf);
            }
        }
    }

    public List<Film> searchSeries(List<Film> filmsList, String searchText, List<String> selectedGenres, String selectedYear) {
        if (searchText.isEmpty() && selectedGenres.isEmpty() && (selectedYear == null || selectedYear.equals("All"))) {
            return filmsList;
        }
        List<Film> filteredList = new ArrayList<>();
        for (Film film : filmsList) {
            int filmYear = film.getAnnerdesortie().getYear();
            int selectedYearInt = selectedYear == null || selectedYear.equals("All") ? -1 : Integer.parseInt(selectedYear);
            if ((searchText.isEmpty() || film.getNom().toLowerCase().contains(searchText.toLowerCase())) &&
                    (selectedGenres.isEmpty() || new HashSet<>(film.getListegenre()).containsAll(selectedGenres)) &&
                    (selectedYearInt == -1 || filmYear == selectedYearInt)) {
                filteredList.add(film);
            }
        }
        return filteredList;
    }
    public void OnSearch(){
        List<Film> filteredFilms;
        filteredFilms=searchSeries(films,searchBar.getText(),GenresSelector.getCheckModel().getCheckedItems(),YearSelect.getValue());
        FilmsViewer.getChildren().clear();
        ShowFilms(filteredFilms);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IconSetter(SearchButton, "src/main/resources/Images/HomePage/search.png", 20);
        IconSetter(homeButton, "src/main/resources/Images/HomePage/HomeButton.png", 40);
        IconSetter(seriesButoon, "src/main/resources/Images/HomePage/Series.png", 40);
        IconSetter(filmButton, "src/main/resources/Images/HomePage/Movie.png", 40);

        GenresSelector.getItems().addAll(genreNames);
        YearSelect.getItems().addAll(yearList);

        if (films == null || films.isEmpty()) {
            films = new ArrayList<>();
            films = FilmController.GetAllFilms();
        }
        OnSearch();


    }


    ObservableList<String> yearList = FXCollections.observableArrayList("All", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024");

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
