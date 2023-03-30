package Controllers;

import Entities.Actor;
import Entities.Genre;
import Entities.User;
import Utils.DataHolder;
import com.example.netflixproject.HelloApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class ChoicesController implements Initializable {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date;

    private User user;
    public TableView<Actor> ActorTable = new TableView<>();
    public TableView<Genre> GenreTable=new TableView<>();
    @FXML
    public TableColumn<Actor, String> ActorColumn = new TableColumn<>();
    public TableColumn<Actor, CheckBox> SelectedActor = new TableColumn<>();
    public TableColumn<Genre,String> GenreColumn=new TableColumn<>();
    public TableColumn<Genre,CheckBox> SelectedGenre=new TableColumn<>();

    @FXML
    protected void OnReturnBtn() throws Exception {
        HelloApplication.SetRoot("RegisterPage");
    }

    @FXML
    protected void OnDone() throws Exception{
        ArrayList<Long> selectedActors = new ArrayList<>();
        ArrayList<String> selectedGenres = new ArrayList<>();
        for (Actor actor : data) {
            if (actor.getSelect().isSelected()) {
                selectedActors.add(actor.getID());
            }
        }
        for (Genre genre: data2){
            if(genre.getSelect().isSelected()){
                selectedGenres.add(genre.getNom());
            }
        }
        date=LocalDate.parse(DataHolder.getBirthday(),formatter);
        user=new User(DataHolder.getName(),DataHolder.getPrename(),date,selectedActors,selectedGenres,DataHolder.getPassword(),DataHolder.getEmail());
        System.out.println("Selected Actors: "+selectedActors);
        System.out.println("Selected Genres: "+selectedGenres);
        System.out.println(user);
        HelloApplication.SetRoot("HomePage");
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
