package Controllers.FXMLControllers;

import Controllers.SerieController;
import DAO.UserDAO;
import Entities.Serie;
import Utils.DataHolder;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.IconSetter;
import static Utils.RepeatableFunction.ImageClipper;

public class HomePageController implements Initializable {

    public HBox ThumbnailViewer;
 

    public Button homeButton;
    public Button seriesButoon;
    public Button filmButton;
    public Label welcome;
    public Button NotificationButton;
    public ImageView ProfileBtn;


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

    @FXML
    public void OnFilmClick()throws Exception{
        HelloApplication.SetRoot("FilmPage");
    }

    public void OnSeriesClick()throws Exception{
        HelloApplication.SetRoot("SeriesPage");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final int IV_Size = 40;
        List<Serie> serie;
        if(DataHolder.getUser()!=null){
            welcome.setText("Welcome Back "+ DataHolder.getUser().getPrename()+"!");
        }
        IconSetter(NotificationButton,"src/main/resources/Images/HomePage/Notification.png",IV_Size);
        IconSetter(homeButton,"src/main/resources/Images/HomePage/HomeButton.png",40);
        IconSetter(seriesButoon,"src/main/resources/Images/HomePage/Series.png",40);
        IconSetter(filmButton,"src/main/resources/Images/HomePage/Movie.png",40);
        try {
            UserDAO.retrieve_Image((int) DataHolder.getUser().getID());
            File imageFile = DataHolder.getImage();
            System.out.println("image file: "+imageFile);
            if (imageFile != null) {
                Image profileImage = new Image(imageFile.toURI().toString());
                System.out.println("Profile image in profilepage: "+profileImage);

                ProfileBtn.setImage(profileImage);
                ImageClipper(ProfileBtn);
            } else {
                System.out.println("No image found for user.");
            }
        } catch (Exception e) {
            System.out.println("image not found");
        }

        try {
            serie = SerieController.GetSerieByName("The Witcher");
            serie.addAll(SerieController.GetSerieByName("breaking bad"));
            serie.addAll(SerieController.GetSerieByName("Dark"));
            serie.addAll(SerieController.GetSerieByName("The Witcher"));
            System.out.println(serie);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        for (Serie s : serie) {
            ImageView imageView ;
            imageView = new ImageView(String.valueOf(s.getImg().toURI()));
            imageView.setCursor(Cursor.cursor("hand"));
            imageView.setFitHeight(100);
            imageView.setFitWidth(150);
            ImageClipper(imageView);
            imageView.setOnMouseClicked(this::handleImageClick);
            ThumbnailViewer.getChildren().add(imageView);
        }
    }



}

