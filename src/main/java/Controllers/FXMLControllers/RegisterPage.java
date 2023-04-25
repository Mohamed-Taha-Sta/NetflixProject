package Controllers.FXMLControllers;

import Controllers.ActorController;
import Controllers.ProducerController;
import Controllers.UserController;
import Entities.Actor;
import Entities.Producer;
import Utils.DataHolder;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.isTextExceedingLength;
import static Utils.RepeatableFunction.showErrorMessage;

public class RegisterPage implements Initializable {


    public ComboBox<String> identity;

    public DatePicker UserBirthday;
    public TextField UserPassword;
    public TextField UserEmail;
    public TextField UserPrename;
    public TextField UserName;
    public Label AlertText;


    @FXML
    protected void OnSignIn() throws Exception {
        HelloApplication.SetRoot("LoginPage");
    }


    @FXML
    protected void OnSignUp() throws Exception {
        if (UserName.getText().isEmpty() || UserPrename.getText().isEmpty() || UserEmail.getText().isEmpty() || UserPassword.getText().isEmpty()) {
            showErrorMessage(AlertText, "Must fill all the fields!");
        } else if (isTextExceedingLength(UserName, 50)) {
            showErrorMessage(AlertText,"Last Name field is too long");
        } else if (isTextExceedingLength(UserPrename, 50)) {
            showErrorMessage(AlertText,"First Name field is too long");
        } else if (isTextExceedingLength(UserEmail, 50)) {
            showErrorMessage(AlertText, "Email field is too long");
        } else if (isTextExceedingLength(UserPassword, 50)) {
            showErrorMessage(AlertText, "Password field is too long");
        } else if (!UserController.isEmail(UserEmail.getText())) {
            showErrorMessage(AlertText,"This email address is not recognized!");
        } else if (!UserController.check_Mail(UserEmail.getText())) {
            showErrorMessage(AlertText,"Another user with same mail exists!");
        } else if (!ProducerController.check_Mail(UserEmail.getText())) {
            showErrorMessage(AlertText,"Another producer with same mail exists!");
        } else if (!ActorController.check_Mail(UserEmail.getText())) {
            showErrorMessage(AlertText,"Another actor with same mail exists!");
        } else if (identity.getValue().equals("User") && UserBirthday.getValue() == null) {
            showErrorMessage(AlertText,"Must fill all the fields!");
        } else {
            DataHolder.setName(UserName.getText());
            DataHolder.setPrename(UserPrename.getText());
            DataHolder.setEmail(UserEmail.getText());
            if (identity.getValue().equals("User")) {
                DataHolder.setBirthday(String.valueOf(UserBirthday.getValue()));
            }
            DataHolder.setPassword(UserPassword.getText());
            if (identity.getValue().equals("Producer")) {
                long ID_PROD = ProducerController.createprod(new Producer(DataHolder.getName(), DataHolder.getPrename(), DataHolder.getEmail(), DataHolder.getPassword()));
                DataHolder.setProducer(new Producer(ID_PROD, DataHolder.getName(), DataHolder.getPrename(), DataHolder.getEmail(), DataHolder.getPassword()));
                HelloApplication.SetRoot("ProducerLandingPage");
            } else if (identity.getValue().equals("User")) {
                HelloApplication.SetRoot("ChoicesMenu");
            }else if (identity.getValue().equals("Actor")) {
                Actor actor=new Actor(DataHolder.getName(), DataHolder.getPrename(), DataHolder.getEmail(), DataHolder.getPassword());
                ActorController.Add_Actor(actor);
                DataHolder.setActor(actor);
                HelloApplication.SetRoot("ActorLandingPage");
            }
            else if (identity.getValue().equals("Admin")) {
                System.out.println("FINISH THE ADMIN");
            }

        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        identity.getItems().addAll("User", "Producer", "Actor");
        identity.setValue("User");
        identity.valueProperty().addListener((observable, oldValue, newValue) -> {
            UserBirthday.setVisible(newValue.equals("User"));
        });

        identity.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    OnSignUp();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        UserBirthday.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    OnSignUp();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        UserPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    OnSignUp();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        UserEmail.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    OnSignUp();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        UserPrename.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    OnSignUp();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        UserName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    OnSignUp();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}



