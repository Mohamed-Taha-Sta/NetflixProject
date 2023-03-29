package Controllers;

import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterPage implements Initializable {
    public DatePicker UserBirthday;
    public TextField UserPassword;
    public TextField UserEmail;
    public TextField UserPrename;
    public TextField UserName;
    public ListView<String> ActorsList=new ListView<>();
    @FXML
    private Button SignUp;

    @FXML
    private Button login;
    ResourceBundle BN;
    URL ul;
    @FXML
    protected void OnSignIn() throws Exception{
        HelloApplication.SetRoot("LoginPage");
    }
    @FXML
    protected void OnSignUp() throws Exception{
        HelloApplication.SetRoot("ChoicesMenu");
        initialize(ul,BN);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] actors = {"Tom Hanks", "Meryl Streep", "Leonardo DiCaprio", "Emma Stone", "Robert Downey Jr.","Robert Downey Jr.","Robert Downey Jr."};
        ActorsList.getItems().addAll(actors);
    }
}
