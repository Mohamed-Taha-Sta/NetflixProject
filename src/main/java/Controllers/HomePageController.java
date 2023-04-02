package Controllers;

import Entities.Episode;
import Entities.Resume;
import Entities.Synopsis;
import Entities.Text;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    public HBox ThumbnailViewer;
    public Button ProfileBtn;

    @FXML
    public void handleImageClick(javafx.scene.input.MouseEvent event) {
        ImageView clickedImage = (ImageView) event.getSource();
        System.out.println("Clicked image: " + clickedImage.getImage().getUrl());
    }

    @FXML
    public void OnProfileClick()throws Exception{
        HelloApplication.SetRoot("ProfilePage");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Episode> episodes = new ArrayList<>();
        Resume resume = new Text();
        ((Text) resume).setTexte("This is an Episode");

        File file = new File("src/main/java/Test/VideoTest.mp4");
        File imageFile = new File("src/main/java/Test/LionTest.jpeg");
        Resume resume2 = new Synopsis(file);
        Episode episode = new Episode(1, "EpisodeFamilyGuy", 5, LocalDate.of(2012, 10, 12),
                LocalDate.of(2012, 10, 10), resume, 1500, 50, 150, file, imageFile);
        Episode episode2 = new Episode(2, "FaresSEpisode", 2, LocalDate.of(2023, 12, 14),
                LocalDate.of(2023, 12, 15), resume2, 1500, 50, 150, file, imageFile);
        episodes.add(episode);
        episodes.add(episode2);
        for (int i = 1; i < 9; i++) {
            for (Episode epi : episodes) {
                ImageView imageView = null;
                try {
                    imageView = new ImageView(String.valueOf(epi.getImage().toURL()));
                    imageView.setFitHeight(150);
                    imageView.setFitWidth(100);
                    imageView.setOnMouseClicked(this::handleImageClick);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                ThumbnailViewer.getChildren().add(imageView);
            }
        }
    }
}
