package Controllers;

import Entities.Actor;
import com.example.netflixproject.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class RegisterPage implements Initializable {
    public DatePicker UserBirthday;
    public TextField UserPassword;
    public TextField UserEmail;
    public TextField UserPrename;
    public TextField UserName;
    public TableView<Actor> ActorTable = new TableView<>();
    @FXML
    public TableColumn<?, ?> ActorColumn=new TableColumn<>();

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
        initialize(ul, BN);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //ObservableList<String> data = observableArrayList("Tom Hanks", "Meryl Streep", "Leonardo DiCaprio", "Emma Stone", "Robert Downey Jr.", "Robert Downey Jr.", "Robert Downey Jr.");
        //these have problem dont touch

        Actor ac=new Actor(11,"Tom hanks","","dd","sssa");
        ObservableList<Actor> data = FXCollections.observableArrayList();
        data.add(ac);
        ActorColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ActorTable.setItems(data);
        //   ActorTable.setItems(data);
    }
}
