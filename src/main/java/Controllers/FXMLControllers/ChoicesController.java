package Controllers.FXMLControllers;

import DAO.UserDAO;
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

import static javafx.collections.FXCollections.checkedObservableList;
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
        user=new User(DataHolder.getName(),DataHolder.getPrename(),DataHolder.getEmail(),DataHolder.getPassword(),date,selectedActors,selectedGenres);
        System.out.println("Selected Actors: "+selectedActors);
        System.out.println("Selected Genres: "+selectedGenres);
        System.out.println(user);
        if(UserDAO.ajout_User(user)){
            HelloApplication.SetRoot("HomePage");
        }
        else{
            HelloApplication.SetRoot("RegisterPage");
        }

    }

    ObservableList<Actor> data = observableArrayList(new Actor(11, "Tom hanks", "", "dd", "sssa"), new Actor(12, "Jr", "", "dd", "sssa"), new Actor(13, "Emm", "", "dd", "sssa"));
    ObservableList<Genre> data2 = observableArrayList(
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
