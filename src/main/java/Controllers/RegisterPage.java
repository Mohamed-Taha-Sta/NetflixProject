package Controllers;

import Entities.Actor;
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
    public TableView<Actor> ActorTable=new TableView<>();
    @FXML
    public TableColumn<Actor, String> ActorColumn=new TableColumn<>();
    public TableColumn<Actor,CheckBox> SelectedActor=new TableColumn<>();
    public TableColumn GenreColumn;
    public TableColumn SelectedGenre;

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
    protected void OnReturnBtn() throws Exception{
        HelloApplication.SetRoot("RegisterPage");
    }

    @FXML
    protected void OnDone() throws Exception{
        List<Actor> selectedActors = new ArrayList<>();

        for (Actor actor : data) {
            if (actor.getSelect().isSelected()) {
                selectedActors.add(actor);
            }
        }

        System.out.printf(selectedActors.toString());
    }
    ObservableList<Actor> data = observableArrayList(new Actor(11,"Tom hanks","","dd","sssa"),new Actor(12,"Jr","","dd","sssa"),new Actor(13,"Emm","","dd","sssa"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        ActorColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        SelectedActor.setCellValueFactory(new PropertyValueFactory<>("select"));
        ActorTable.setItems(data);

    }
}
