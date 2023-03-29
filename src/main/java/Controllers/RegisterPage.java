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
    public TableView<Actor> ActorTable = new TableView<Actor>();
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

        ObservableList<Actor> data = observableArrayList();
        //these have problem dont touch

        Actor ac=new Actor(11,"Tom hanks","","dd","sssa");
        Actor ac1=new Actor(12,"Jr","","dd","sssa");
        Actor ac2=new Actor(13,"Emm","","dd","sssa");
        ArrayList<Actor> acto=new ArrayList<>();
        acto.add(ac);
        acto.add(ac1);
        acto.add(ac2);
        data.addAll(acto);
        ActorColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ActorTable.setItems(data);

    }
}
