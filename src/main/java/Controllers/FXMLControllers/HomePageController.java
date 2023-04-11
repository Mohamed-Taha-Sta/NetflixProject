package Controllers.FXMLControllers;

import Entities.Episode;
import Entities.Resume;
import Entities.Synopsis;
import Entities.Text;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.awt.geom.Rectangle2D;
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
    public ImageView TopWatched;
    public AnchorPane ImageAnchor=new AnchorPane();
    public Button homeButton;
    public Button seriesButoon;
    public Button filmButton;

    private boolean homeSelected=true;
    private boolean SeriesSeleced=false;

    private boolean filmsSelected=false;



    @FXML

    public void handleImageClick(javafx.scene.input.MouseEvent event) {
        try {
            ImageView clickedImage = (ImageView) event.getSource();
            System.out.println("Clicked image: " + clickedImage.getImage().getUrl());
            HelloApplication.SetRoot("VideoPlayer");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void OnProfileClick()throws Exception{
        HelloApplication.SetRoot("ProfilePage");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String imagePath = "file:src/main/resources/Images/battlefield-2042-kaleidoscope-map-pc-games-xbox-series-x-3440x1440-6956.jpg";
        TopWatched.setImage(new Image(imagePath));
        TopWatched.setCursor(Cursor.cursor("hand"));

        List<Episode> episodes = new ArrayList<>();
        Resume resume = new Text();
        ((Text) resume).setTexte("This is an Episode");

        File file = new File("src/main/java/Test/VideoTest.mp4");
        File imageFile = new File("src/main/resources/Images/battlefield-2042-kaleidoscope-map-pc-games-xbox-series-x-3440x1440-6956.jpg");
        Resume resume2 = new Synopsis(file);
        Episode episode = new Episode(1, "EpisodeFamilyGuy", 5, LocalDate.of(2012, 10, 12),
                LocalDate.of(2012, 10, 10), resume, file, imageFile);
        Episode episode2 = new Episode(2, "FaresSEpisode", 2, LocalDate.of(2023, 12, 14),
                LocalDate.of(2023, 12, 15), resume2, file, imageFile);
        episodes.add(episode);
        episodes.add(episode2);
        for (int i = 1; i < 8; i++) {
            for (Episode epi : episodes) {
                ImageView imageView = null;
                try {
                    imageView = new ImageView(String.valueOf(epi.getImage().toURL()));
                    imageView.setCursor(Cursor.cursor("hand"));
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(150);
                    imageView.setOnMouseClicked(this::handleImageClick);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                ThumbnailViewer.getChildren().add(imageView);
            }
        }


    }
}
