package Controllers;

import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginPage {

    public AnchorPane loginpage;
    @FXML
    private Button login;
    @FXML
    private Button signup;

    @FXML
    private TextField Mail;
    @FXML
    private TextField Password;

    @FXML
    protected void onSignUp() throws Exception{
        HelloApplication.SetRoot("RegisterPage");
    }

}
