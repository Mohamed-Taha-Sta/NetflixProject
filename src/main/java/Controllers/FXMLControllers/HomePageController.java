package Controllers.FXMLControllers;

import Controllers.SerieController;
import Entities.*;
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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    public HBox ThumbnailViewer;
    public Button ProfileBtn;
    public ImageView TopWatched;
    public AnchorPane ImageAnchor = new AnchorPane();
    public Button homeButton;
    public Button seriesButoon;
    public Button filmButton;

    private boolean homeSelected = true;
    private boolean SeriesSeleced = false;

    private boolean filmsSelected = false;


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
    public void OnProfileClick() throws Exception {
        HelloApplication.SetRoot("ProfilePage");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String imagePath = "file:src/main/resources/Images/battlefield-2042-kaleidoscope-map-pc-games-xbox-series-x-3440x1440-6956.jpg";
        TopWatched.setImage(new Image(imagePath));
        TopWatched.setCursor(Cursor.cursor("hand"));

        List<Serie> serie = new ArrayList<>();


        try {
            serie = SerieController.GetSerieByName("The Witcher");
            serie.addAll(SerieController.GetSerieByName("breaking bad"));
            serie.addAll(SerieController.GetSerieByName("Dark"));
            serie.addAll(SerieController.GetSerieByName("The Witcher"));
            System.out.println(serie);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Serie s : serie) {
            ImageView imageView = null;
            try {
                imageView = new ImageView(String.valueOf(s.getImg().toURL()));
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

