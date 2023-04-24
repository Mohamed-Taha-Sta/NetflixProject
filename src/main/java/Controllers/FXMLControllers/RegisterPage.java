package Controllers.FXMLControllers;

import Controllers.ActorController;
import Controllers.ProducerController;
import Controllers.UserController;
import DAO.ProducerDAO;
import DAO.UserDAO;
import Entities.Producer;
import Entities.User;
import Utils.DataHolder;
import com.example.netflixproject.HelloApplication;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.isTextExceedingLength;
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
        if (UserName.getText().isEmpty() || UserPrename.getText().isEmpty() || UserEmail.getText().isEmpty() || UserPassword.getText().isEmpty()) {
            showErrorMessage("Must fill all the fields!");
        } else if (isTextExceedingLength(UserName, 50)) {
            showErrorMessage("Last Name field is too long");
        } else if (isTextExceedingLength(UserPrename, 50)) {
            showErrorMessage("First Name field is too long");
        } else if (isTextExceedingLength(UserEmail, 50)) {
            showErrorMessage("Email field is too long");
        } else if (isTextExceedingLength(UserPassword, 50)) {
            showErrorMessage("Password field is too long");
        } else if (!UserController.isEmail(UserEmail.getText())) {
            showErrorMessage("This email address is not recognized!");
        } else if (!UserController.check_Mail(UserEmail.getText())) {
            showErrorMessage("Another user with same mail exists!");
        } else if (!ProducerController.check_Mail(UserEmail.getText())) {
            showErrorMessage("Another producer with same mail exists!");
        } else if (!ActorController.check_Mail(UserEmail.getText())) {
            showErrorMessage("Another actor with same mail exists!");
        } else if (identity.getValue().equals("User") && UserBirthday.getValue() == null) {
            showErrorMessage("Must fill all the fields!");
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
            } else if (identity.getValue().equals("Admin")) {
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



