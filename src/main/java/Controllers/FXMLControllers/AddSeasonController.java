package Controllers.FXMLControllers;

import Utils.DataHolderSeason;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;

public class AddSeasonController {

    public TextField Name;
    public Text AlertText;
    public TextField Thumbnail;
    public TextField Synopsis;


    @FXML
    protected  void onAdd() throws Exception{
        if(Name.getText().isEmpty()){
            AlertText.setText("Season must have a name");
            AlertText.setOpacity(1);
        }
        else if(Name.getText().length()<1){
            AlertText.setText("Season must have a valid name");
            AlertText.setOpacity(1);
        }else{
            DataHolderSeason.setName(Name.getText());
            HelloApplication.SetRoot("AddEpisode");
        }
    }

    @FXML
    public void ThumbnailSelect(javafx.scene.input.MouseEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Thumbnail");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.jpeg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());

            int width = (int) image.getWidth();
            int height = (int) image.getHeight();
            if (width <= 1920 && height <= 1080) {
                DataHolderSeason.setThumbnail(selectedFile);

            } else {
                // If the selected image does not have the required dimensions, display an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Image Size");
                alert.setContentText("Please select an image with dimensions of 1920*1080 pixels.");
                alert.showAndWait();
            }
        }
    }


    @FXML
    public void SynopsisSelect(javafx.scene.input.MouseEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Synopsis");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter videoFilter = new FileChooser.ExtensionFilter("MP4 videos", "*.mp4");
        fileChooser.getExtensionFilters().add(videoFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (extension.equals("mp4")) {
                DataHolderSeason.setSynopsis(selectedFile);

            } else {
                // If the selected image does not have the required dimensions, display an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Image Size");
                alert.setContentText("Please select an image with dimensions of 1920*1080 pixels.");
                alert.showAndWait();
            }
        }
    }







}
