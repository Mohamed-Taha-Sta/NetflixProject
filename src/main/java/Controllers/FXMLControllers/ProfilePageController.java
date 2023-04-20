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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ProfilePageController implements Initializable {


    //LeftMenu Vars
    public ImageView Profile_Image;
    public Text ProfileName;
    public Button ProfileBtn;
    public Button NotfificationBtn;
    public Button returnBtn;


    //Profile Menu Anchor
    public TextField prenameField;
    public Button PrenameBtn;
    public TextField namefield;
    public Button NameBtn;
    public TextField mailfield;
    public Button MailBtn;
    public Button LogoutBtn;
    public Button PassBtn;
    public AnchorPane ProfileMenu;
    public Label NameLabel;
    public Label PrenameLable;
    public Label MailLabel;
    public DatePicker Birthdaypicker;
    public Button BirthdayBtn;
    public Label AlertText;
    public TableView GenreTable;
    public TableColumn GenreColumn;
    public TableColumn SelectedGenre;


    //Notification Menu Anchor
    public AnchorPane NotificationMenu;
    public ListView NotificationPane;


    //Password Menu
    public AnchorPane PassMenu;
    public TextField OldPass;
    public TextField newPass;
    public TextField PassConf;
    public Button confirmBtn;
    public Label passAlert;



    @FXML
    public void OnProfileImage(javafx.scene.input.MouseEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Image");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.jpeg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());

            int width = (int) image.getWidth();
            int height = (int) image.getHeight();
            if (width <= 512 && height <= 512) {
                Profile_Image.setImage(image);
                UserDAO.adding_Image(selectedFile);

            } else {
                // If the selected image does not have the required dimensions, display an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Image Size");
                alert.setContentText("Please select an image with dimensions of 512x512 pixels.");
                alert.showAndWait();
            }
        }
    }

    public void OnReturn() throws Exception {
        HelloApplication.SetRoot("HomePage");
    }

    public void OnloadProfile() throws Exception {
        System.out.println(DataHolder.getUser().getBirthday());
        ProfileName.setText(DataHolder.getUser().getName() + " " + DataHolder.getUser().getPrename());
        NameLabel.setText(DataHolder.getUser().getName());
        PrenameLable.setText(DataHolder.getUser().getPrename());
        MailLabel.setText(DataHolder.getUser().getMail());
        try {
            Birthdaypicker.setValue(DataHolder.getUser().getBirthday());
        } catch (Exception e) {

            System.out.println(e);
        }


    }

    public void OnProfilebtn() throws Exception {
        ProfileMenu.setVisible(true);
        NotificationMenu.setVisible(false);
        PassMenu.setVisible(false);
    }

    public void OnNotibtn() throws Exception {
        ProfileMenu.setVisible(false);
        NotificationMenu.setVisible(true);
        PassMenu.setVisible(false);
    }

    public void OnPassbtn() throws Exception {
        ProfileMenu.setVisible(false);
        NotificationMenu.setVisible(false);
        PassMenu.setVisible(true);
    }

    public void OnLogOutBtn() throws Exception {
        HelloApplication.SetRoot("LoginPage");
        DataHolder.setUser(null);
    }

    public void OnPrenameBtn() throws Exception {
        if (prenameField.getText().isEmpty()) {
            showErrorMessage(AlertText,"Your FirstName field is empty");
        } else {
            System.out.println(UserController.FirstName(prenameField.getText()));
            DataHolder.getUser().setPrename(prenameField.getText());
            PrenameLable.setText(prenameField.getText());
            ProfileName.setText(DataHolder.getUser().getName() + " " + DataHolder.getUser().getPrename());
        }
    }

    public void OnMailBtn() throws Exception {
        if (mailfield.getText().isEmpty()) {
            showErrorMessage(AlertText, "Your Mail field is empty");
        } else {
            System.out.println(UserController.Mail(mailfield.getText()));
            DataHolder.getUser().setMail(mailfield.getText());
            MailLabel.setText(mailfield.getText());
        }
    }

    public void OnNameBtn() throws Exception {
        if (namefield.getText().isEmpty() || namefield.getText().equals("")) {
            showErrorMessage( AlertText,"Your LastName field is empty");
        } else {
            System.out.println(UserController.LastName(namefield.getText()));
            DataHolder.getUser().setName(namefield.getText());
            NameLabel.setText(namefield.getText());
            ProfileName.setText(DataHolder.getUser().getName() + " " + DataHolder.getUser().getPrename());
        }
    }

    public void OnBirthdayBtn() throws Exception {
        if (Birthdaypicker.getValue().equals(DataHolder.getUser().getBirthday())) {
            showErrorMessage(AlertText, "You didnt change your birthday");
        } else {
            System.out.println(UserController.Birthday(Birthdaypicker.getValue()));
            DataHolder.getUser().setBirthday(Birthdaypicker.getValue());
        }
    }


    public void OnConfirm() throws Exception {
        if (OldPass.getText().isEmpty()) {
            showErrorMessage(passAlert, "Old Password Required!");
        } else if (newPass.getText().isEmpty()) {
            showErrorMessage(passAlert, "Your new Password is empty!");
        } else if (PassConf.getText().isEmpty()) {
            showErrorMessage(passAlert, "Please Confirm your Password!");
        } else if (!newPass.getText().equals(PassConf.getText())) {
            showErrorMessage(passAlert, "Your confirm password is wrong!");

        } else if (newPass.getText().equals(OldPass.getText())) {
            showErrorMessage(passAlert, "New Password already in use!");
        } else if (!OldPass.getText().equals(DataHolder.getUser().getPassword())) {
            showErrorMessage(passAlert,"Wrong Password!");
        } else {
            System.out.println(UserController.Password(newPass.getText()));
            showErrorMessage(passAlert,"Your password was changed Successfully!");
            PassConf.setText("");
            OldPass.setText("");
            newPass.setText("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final int IV_Size = 25;
        try {
            UserDAO.retrieve_Image((int) DataHolder.getUser().getID());
            File imageFile = DataHolder.getImage();
            System.out.println("image file: " + imageFile);
            if (imageFile != null) {
                Image profileImage = new Image(imageFile.toURI().toString());
                System.out.println("Profile image in profilepage: " + profileImage);
                Profile_Image.setImage(profileImage);
                Profile_Image = HomePageController.ImageClipper(Profile_Image);
            } else {
                System.out.println("No image found for user.");
            }
        } catch (Exception e) {
            System.out.println("image not found");
        }
        HomePageController.IconSetter(returnBtn, "src/main/resources/Images/HomePage/BackArrow.png", 25);
        try {
            OnloadProfile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void showErrorMessage(Label label, String message) {
        label.setText(message);
        label.setOpacity(1);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            label.setOpacity(0);
        }));
        timeline.play();
    }
}
