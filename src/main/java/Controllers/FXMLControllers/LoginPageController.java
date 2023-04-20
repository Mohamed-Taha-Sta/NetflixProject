package Controllers.FXMLControllers;

import Controllers.ActorController;
import Controllers.ProducerController;
import Controllers.UserController;
import DAO.ProducerDAO;
import DAO.UserDAO;
import Utils.DataHolder;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

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
            AlertText.setText("Must fill all fields");
            AlertText.setOpacity(1);
        } else if (UserController.authenticate(mail.getText(),Password.getText())){
            DataHolder.setUserType("User");
            HelloApplication.SetRoot("HomePage");
        } else if (ActorController.authenticate(mail.getText(),Password.getText())) {
            DataHolder.setUserType("Actor");
//            HelloApplication.SetRoot("ActorDashboard");
        } else if (ProducerController.authenticate(mail.getText(),Password.getText())) {
            DataHolder.setUserType("Producer");
//            HelloApplication.SetRoot("ProducerDashBoard");

        } else {
            AlertText.setText("Email or password invalid");
            AlertText.setOpacity(1);
        }


    }

    @FXML
    protected void onSignUp() throws Exception{

        HelloApplication.SetRoot("RegisterPage");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
