package Controllers.FXMLControllers;

import DAO.UserDAO;
import Utils.DataHolder;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfilePageController implements Initializable {

    public ImageView Profile_Image;
    public Text ProfileName;
    public VBox ProfileBox;
    public TextField prenameField;
    public Button PrenameBtn;
    public TextField namefield;
    public Button NameBtn;
    public TextField mailfield;
    public Button MailBtn;
    public Button ProfileBtn;
    public Button NotfificationBtn;
    public Button returnBtn;

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

    public void OnReturn()throws Exception{
        HelloApplication.SetRoot("HomePage");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final int IV_Size = 25;
        try {
            UserDAO.retrieve_Image((int) DataHolder.getUser().getID());
            File imageFile = DataHolder.getImage();
            System.out.println("image file: "+imageFile);
            if (imageFile != null) {
                Image profileImage = new Image(imageFile.toURI().toString());
                System.out.println("Profile image in profilepage: "+profileImage);
                Profile_Image.setImage(profileImage);
            } else {
                System.out.println("No image found for user.");
            }
        } catch (Exception e) {
            System.out.println("image not found");
        }
        Image returnIm=new Image(new File("src/main/resources/Images/HomePage/BackArrow.png").toURI().toString());
        ImageView returnView=new ImageView(returnIm);
        returnView.setFitWidth(IV_Size);
        returnView.setFitHeight(IV_Size);
        returnBtn.setGraphic(returnView);

        ProfileName.setText(DataHolder.getUser().getName() + " " + DataHolder.getUser().getPrename());
    }
}
