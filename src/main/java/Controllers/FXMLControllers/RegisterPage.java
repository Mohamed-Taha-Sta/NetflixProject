package Controllers.FXMLControllers;

import Controllers.UserController;
import DAO.UserDAO;
import Utils.DataHolder;
import com.example.netflixproject.HelloApplication;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class RegisterPage implements Initializable {


    public Text Alerttext;
    public ComboBox<String> identity;

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

    private void showErrorMessage(String message) {
        Alerttext.setText(message);
        Alerttext.setOpacity(1);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            Alerttext.setOpacity(0);
        }));
        timeline.play();
    }

    @FXML
    protected void OnSignUp() throws Exception {
        if (UserName.getText().isEmpty() || UserPrename.getText().isEmpty() || UserEmail.getText().isEmpty() || UserBirthday.getValue() == null || UserPassword.getText().isEmpty()) {
            showErrorMessage("Must fill all the fields!");
        } else if (!UserController.isEmail(UserEmail.getText())) {
            showErrorMessage("This email address is not recognized!");
        } else if (!UserDAO.check_Mail(UserEmail.getText())) {
            showErrorMessage("Another user with same mail exists");
        } else {
            DataHolder.setName(UserName.getText());
            DataHolder.setPrename(UserPrename.getText());
            DataHolder.setEmail(UserEmail.getText());
            DataHolder.setBirthday(String.valueOf(UserBirthday.getValue()));
            DataHolder.setPassword(UserPassword.getText());
            HelloApplication.SetRoot("ChoicesMenu");
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        identity.getItems().addAll("User", "Producer", "Actor");
        identity.setValue("User");
        identity.valueProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.equals("User")) {
                UserBirthday.setVisible(true);
            } else {
                UserBirthday.setVisible(false);
            }
        });

    }
}


