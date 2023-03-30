package Controllers;

import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class LoginPage {

    public AnchorPane loginpage;
    public Text AlertText;
    public TextField mail;
    @FXML
    private Button login;
    @FXML
    private Button signup;


    @FXML
    private TextField Password;
    @FXML
    protected  void onSignIn() throws Exception{
        if(mail.getText().isEmpty()||Password.getText().isEmpty()){
            AlertText.setOpacity(1);
        }
        HelloApplication.SetRoot("HomePage");

    }

    @FXML
    protected void onSignUp() throws Exception{

        HelloApplication.SetRoot("RegisterPage");
    }

}
