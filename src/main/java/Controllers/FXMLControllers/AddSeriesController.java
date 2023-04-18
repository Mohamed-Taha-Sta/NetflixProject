package Controllers.FXMLControllers;

import Entities.Genre;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class AddSeriesController implements Initializable {


    public CheckComboBox<String> GenreSelector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> genreNames = observableArrayList(
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


        GenreSelector.getItems().addAll(genreNames);
        GenreSelector.setTitle("Genres");

    }
}
