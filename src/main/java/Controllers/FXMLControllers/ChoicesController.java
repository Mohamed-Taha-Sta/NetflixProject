package Controllers.FXMLControllers;

import Controllers.ActorController;
import DAO.UserDAO;
import Entities.Actor;
import Entities.Genre;
import Entities.User;
import Utils.DataHolder;
import com.example.netflixproject.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.GetGenres;

public class ChoicesController implements Initializable {

    public TableColumn<Actor, String> ActorNameColumn;
    public TableColumn<Actor, String> ActorPrenameColumn;
    public TextField ActorSearch;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date;

    public TableView<Actor> ActorTable = new TableView<>();
    public TableView<Genre> GenreTable = new TableView<>();
    @FXML
    //   public TableColumn<Actor, String> ActorColumn = new TableColumn<>();
    public TableColumn<Actor, CheckBox> SelectedActor = new TableColumn<>();
    public TableColumn<Genre, String> GenreColumn = new TableColumn<>();
    public TableColumn<Genre, CheckBox> SelectedGenre = new TableColumn<>();

    @FXML
    protected void OnReturnBtn() throws Exception {
        HelloApplication.SetRoot("RegisterPage");
    }

    @FXML
    protected void OnDone() throws Exception {
        ArrayList<Long> selectedActors = new ArrayList<>();
        ArrayList<String> selectedGenres = new ArrayList<>();
        for (Actor actor : actors) {
            if (actor.getSelect().isSelected()) {
                selectedActors.add(actor.getID());
            }
        }
        for (Genre genre : genres) {
            if (genre.getSelect().isSelected()) {
                selectedGenres.add(genre.getNom());
            }
        }
        date = LocalDate.parse(DataHolder.getBirthday(), formatter);
        User user = new User(DataHolder.getName(), DataHolder.getPrename(), DataHolder.getEmail(), DataHolder.getPassword(), date, selectedActors, selectedGenres);
        if (UserDAO.ajout_User(user) != -1) {
            HelloApplication.SetRoot("HomePage");
        } else {
            HelloApplication.SetRoot("RegisterPage");
        }

    }

    static ObservableList<Actor> actors;
    ObservableList<Genre> genres = GetGenres();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        actors = FXCollections.observableList(ActorController.GetAllActors(ActorSearch.getText()));

        ActorNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ActorPrenameColumn.setCellValueFactory(new PropertyValueFactory<>("Prename"));
        SelectedActor.setCellValueFactory(new PropertyValueFactory<>("select"));
        GenreColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        SelectedGenre.setCellValueFactory(new PropertyValueFactory<>("select"));
        ActorTable.setItems(actors);
        GenreTable.setItems(genres);

    }
}
