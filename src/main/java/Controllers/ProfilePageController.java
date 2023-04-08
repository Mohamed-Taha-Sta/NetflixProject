package Controllers;

import DAO.UserDAO;
import Utils.DataHolder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfilePageController implements Initializable {

    public ImageView Profile_Image;
    public Text ProfileName;

    @FXML
    public void OnProfile(javafx.scene.input.MouseEvent event) throws Exception {
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        ProfileName.setText(DataHolder.getUser().getName() + " " + DataHolder.getUser().getPrename());
    }
}
