package Controllers.FXMLControllers;

import Controllers.ActorController;
import Controllers.AdminController;
import Controllers.ProducerController;
import Controllers.UserController;
import Utils.DataHolder;
import com.example.netflixproject.HelloApplication;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.isTextExceedingLength;

public class LoginPageController implements Initializable {

    public Text AlertText;
    public TextField mail;


    @FXML
    private TextField Password;

    @FXML
    protected void onSignIn() throws Exception {

        if (mail.getText().isEmpty() || Password.getText().isEmpty()) {
            showErrorMessage("Must fill all fields");
        } else if (isTextExceedingLength(mail, 50)) {
            showErrorMessage("Mail field is too long");
        } else if (isTextExceedingLength(Password, 50)) {
            showErrorMessage("Password field is too long");
        } else if (UserController.authenticate(mail.getText(), Password.getText())) {
            DataHolder.setUserType("User");
            HelloApplication.SetRoot("HomePage");
        } else if (ActorController.authenticate(mail.getText(), Password.getText())) {
            DataHolder.setUserType("Actor");
            System.out.println("Logged as Actor");
            HelloApplication.SetRoot("ActorLandingPage");
        } else if (ProducerController.authenticate(mail.getText(), Password.getText())) {
            DataHolder.setUserType("Producer");
            System.out.println("Logged as Producer");
            HelloApplication.SetRoot("ProducerLandingPage");

        } else if (AdminController.authenticate(mail.getText(), Password.getText())) {
            DataHolder.setUserType("Admin");
            System.out.println("Logged as Admin");
            HelloApplication.SetRoot("AdminLandingPage");

        } else {
            AlertText.setText("Email or password invalid");
            AlertText.setOpacity(1);
        }


    }

    @FXML
    protected void onSignUp() throws Exception {

        HelloApplication.SetRoot("RegisterPage");
    }

    private void showErrorMessage(String message) {
        AlertText.setText(message);
        AlertText.setOpacity(1);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            AlertText.setOpacity(0);
        }));
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mail.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    onSignIn();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Password.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    onSignIn();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
