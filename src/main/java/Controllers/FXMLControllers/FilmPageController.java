package Controllers.FXMLControllers;

import com.example.netflixproject.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FilmPageController implements Initializable  {

    public TextField searchBar;


    public Button homeButton;
    public Button seriesButoon;
    public Button filmButton;
    public Button SearchButton;
    public CheckComboBox<String> GenresSelector;


    @FXML
    public void OnHomeClick()throws Exception{
        HelloApplication.SetRoot("HomePage");
    }

    public void OnSeriesClick()throws Exception{
        HelloApplication.SetRoot("SeriesPage");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PageSetter(GenresSelector, SearchButton, homeButton, seriesButoon, filmButton);


    }

    public static void PageSetter(CheckComboBox<String> genresSelector, Button searchButton, Button homeButton, Button seriesButoon, Button filmButton) {
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
        genresSelector.getItems().addAll(genreNames);
        HomePageController.IconSetter(searchButton,"src/main/resources/Images/HomePage/search.png",20);
        HomePageController.IconSetter(homeButton,"src/main/resources/Images/HomePage/HomeButton.png",30);
        HomePageController.IconSetter(seriesButoon,"src/main/resources/Images/HomePage/Series.png",40);
        HomePageController.IconSetter(filmButton,"src/main/resources/Images/HomePage/Movie.png",45);
    }
}
