package Controllers.FXMLControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.IconSetter;
import static Utils.RepeatableFunction.ImageClipper;

public class SerieViewController implements Initializable {
    public Button homeButton;
    public Button seriesButoon;
    public Button filmButton;
    public Label SerieName;
    public Label DirectLabel;
    public ImageView Thumbnail;
    public Button ThumbUp;
    public Button ThumbDown;
    public Button BackBtn;
    public Button SynopsisBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image img=new Image(new File("src/main/resources/Images/theWitcher.jpg").toURI().toString());
        Thumbnail.setImage(img);
        Thumbnail.setFitWidth(240);
        Thumbnail.setFitHeight(135);
        IconSetter(homeButton,"src/main/resources/Images/HomePage/HomeButton.png",40);
        IconSetter(seriesButoon,"src/main/resources/Images/HomePage/Series.png",40);
        IconSetter(filmButton,"src/main/resources/Images/HomePage/Movie.png",40);
        ImageClipper(Thumbnail);
        IconSetter(ThumbUp,"src/main/resources/Images/Design/ThumbUp.png",40);
        IconSetter(ThumbDown,"src/main/resources/Images/Design/ThumbDown.png",40);
        IconSetter(BackBtn,"src/main/resources/Images/Design/BackButton.png",70);


    }
}
