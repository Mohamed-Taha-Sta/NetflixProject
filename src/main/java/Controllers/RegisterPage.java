package Controllers;

import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class RegisterPage {
    @FXML
    private Button SignUp;

    @FXML
    private Button login;

    @FXML
    protected void OnSignIn() throws Exception{
        HelloApplication.SetRoot("LoginPage");
    }

}
