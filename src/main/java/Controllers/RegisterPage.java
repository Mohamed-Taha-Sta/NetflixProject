package Controllers;

import Entities.Actor;
import Entities.Genre;
import Entities.User;
import com.example.netflixproject.HelloApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class RegisterPage implements Initializable {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    LocalDate date;
    User user;
     String Name;
    String prename;

    String Email;
    String Birthday;
    String Password;


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
        Name= UserName.getText();
        System.out.printf(Name);
        prename=UserPrename.getText();
        Email=UserEmail.getText();
        Birthday=String.valueOf(UserBirthday);
        Password=UserPassword.getText();
        HelloApplication.SetRoot("ChoicesMenu");
    }

    @FXML
    protected void OnReturnBtn() throws Exception {
        HelloApplication.SetRoot("RegisterPage");
    }

    @FXML
    protected void OnDone() throws Exception {
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
        try {
            date = LocalDate.parse(Birthday, formatter);
        }catch (Exception e){
            System.out.printf(String.valueOf(e));
        }
        System.out.println("name"+Name);
        user=new User(11,Name,prename,date ,selectedActors,selectedGenres,Password,Email);
        user.setName(Name);
        user.setPrename(prename);
        user.setMail(Email);
        user.setPassword(Password);
        System.out.println("Selected Actors: "+selectedActors);
        System.out.println("Selected Genres: "+selectedGenres);
        System.out.println("User: "+user);
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
