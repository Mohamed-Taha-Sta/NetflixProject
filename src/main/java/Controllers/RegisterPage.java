package Controllers;

import Entities.Actor;
import com.example.netflixproject.HelloApplication;
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
    public TableView<String> ActorTable = new TableView<>();
    public TableColumn<Actor, String> ActorColumn;

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
        String[] actors = {"Tom Hanks", "Meryl Streep", "Leonardo DiCaprio", "Emma Stone", "Robert Downey Jr.", "Robert Downey Jr.", "Robert Downey Jr."};
        ObservableList<String> data = observableArrayList();
        data.addAll(actors);
        //these have problem dont touch
//        ActorColumn.setCellValueFactory(new PropertyValueFactory<>(""));
//        ActorTable.setItems(data);
    }
}
