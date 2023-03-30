package Controllers;

import Entities.Episode;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    public HBox ThumbnailViewer;
    private List<Episode> episodes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Episode episode : episodes) {
            ImageView imageView = new ImageView(episode.getImage());
            ThumbnailViewer.getChildren().add(imageView);
        }
    }
}
