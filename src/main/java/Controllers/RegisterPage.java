package Controllers;

import Entities.Actor;
import Entities.Genre;
import com.example.netflixproject.HelloApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class RegisterPage implements Initializable {
    public DatePicker UserBirthday;
    public TextField UserPassword;
    public TextField UserEmail;
    public TextField UserPrename;
    public TextField UserName;
    public TableView<Actor> ActorTable = new TableView<>();
    public TableView<Genre> GenreTable=new TableView<>();
    @FXML
    public TableColumn<Actor, String> ActorColumn = new TableColumn<>();
    public TableColumn<Actor, CheckBox> SelectedActor = new TableColumn<>();
    public TableColumn<Genre,String> GenreColumn=new TableColumn<>();
    public TableColumn<Genre,CheckBox> SelectedGenre=new TableColumn<>();


    @FXML
    private Button SignUp;

    @FXML
    private Button login;

    ResourceBundle BN;
    URL ul;

    @FXML
    protected void OnSignIn() throws Exception {
        HelloApplication.SetRoot("LoginPage");
    }

    @FXML
    protected void OnSignUp() throws Exception {
        HelloApplication.SetRoot("ChoicesMenu");
    }

    @FXML
    protected void OnReturnBtn() throws Exception {
        HelloApplication.SetRoot("RegisterPage");
    }

    @FXML
    protected void OnDone() throws Exception {
        List<Actor> selectedActors = new ArrayList<>();
        List<Genre> selectedGenres = new ArrayList<>();
        for (Actor actor : data) {
            if (actor.getSelect().isSelected()) {
                selectedActors.add(actor);
            }
        }
        for (Genre genre: data2){
            if(genre.getSelect().isSelected()){
                selectedGenres.add(genre);
            }
        }

        System.out.println("Selected Actors: "+selectedActors);
        System.out.println("Selected Genres: "+selectedGenres);
    }

    ObservableList<Actor> data = observableArrayList(new Actor(11, "Tom hanks", "", "dd", "sssa"), new Actor(12, "Jr", "", "dd", "sssa"), new Actor(13, "Emm", "", "dd", "sssa"));
    ObservableList<Genre> data2 = observableArrayList(
            new Genre("Action"),
            new Genre("Adventure"),
            new Genre("Comedy"),
            new Genre("Drama"),
            new Genre("Fantasy"),
            new Genre("Horror"),
            new Genre("Mystery"),
            new Genre("Romance"),
            new Genre("Science Fiction"),
            new Genre("Thriller")
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ActorColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        SelectedActor.setCellValueFactory(new PropertyValueFactory<>("select"));
        GenreColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        SelectedGenre.setCellValueFactory(new PropertyValueFactory<>("select"));
        ActorTable.setItems(data);
        GenreTable.setItems(data2);

    }
}
