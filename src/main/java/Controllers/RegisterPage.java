package Controllers;

import DAO.UserDAO;
import Utils.DataHolder;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class RegisterPage {


    public Text Alerttext;
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
        if( UserName.getText().isEmpty() || UserPrename.getText().isEmpty() || UserEmail.getText().isEmpty() || UserBirthday.getValue() == null || UserPassword.getText().isEmpty()){
            Alerttext.setText("Must fill all the fields");
            Alerttext.setOpacity(1);
        }
        else if(!UserDAO.check_Mail(UserEmail.getText())){
            Alerttext.setText("another user with same mail exists");
            Alerttext.setOpacity(1);
        }
        else {
            DataHolder.setName(UserName.getText());
            DataHolder.setPrename(UserPrename.getText());
            DataHolder.setEmail(UserEmail.getText());
            DataHolder.setBirthday(String.valueOf(UserBirthday.getValue()));
            DataHolder.setPassword(UserPassword.getText());
            HelloApplication.SetRoot("ChoicesMenu");
        }

    }


}


