package Controllers.FXMLControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class SerieViewController implements Initializable {
    public Button homeButton;
    public Button seriesButoon;
    public Button filmButton;
    public Label SerieName;
    public Label DirectLabel;
    public ImageView Thumbnail;
    public Button ThumbUp;
    public Button ThumbDown;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HomePageController.IconSetter(ThumbUp,"src/main/resources/Images/Design/ThumbUp.png",40);
        HomePageController.IconSetter(ThumbDown,"src/main/resources/Images/Design/ThumbDown.png",40);
    }
}
