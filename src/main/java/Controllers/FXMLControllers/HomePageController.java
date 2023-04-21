package Controllers.FXMLControllers;

import Controllers.SerieController;
import DAO.UserDAO;
import Entities.Content;
import Entities.Film;
import Entities.Serie;
import Utils.DataHolder;
import com.example.netflixproject.HelloApplication;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public void SetUserImage(){
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
    }
    public SearchableComboBox<Content> searchBar;

    public void searchBarInit(List<Serie> series, List<Film> films){
        List<Content> items=new ArrayList<>();
        items.addAll( series);
        items.addAll( films);
        searchBar.setConverter(new StringConverter<>() {
            @Override
            public String toString(Content item) {
                if (item == null) {
                    return "";
                } else if (item instanceof Serie) {
                    return ((Serie) item).getNom();
                } else if (item instanceof Film) {
                    return ((Film) item).getNom();
                } else {
                    return item.toString();
                }
            }
            @Override
            public Content fromString(String string) {
                // We don't need this method for our implementation, so we return null
                return null;
            }
        });
        searchBar.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Content item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    if (item instanceof Serie) {
                        setText(((Serie) item).getNom());
                    } else if (item instanceof Film) {
                        setText(((Film) item).getNom());
                    }
                }
            }
        });
        searchBar.setItems(FXCollections.observableArrayList(items));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SetUserImage();
        final int IV_Size = 40;
        List<Serie> serie=new ArrayList<>();
        List<Film> films=new ArrayList<>();
        if(DataHolder.getUser()!=null){
            welcome.setText("Welcome Back "+ DataHolder.getUser().getPrename()+"!");
        }
        IconSetter(NotificationButton,"src/main/resources/Images/HomePage/Notification.png",IV_Size);
        IconSetter(homeButton,"src/main/resources/Images/HomePage/HomeButton.png",IV_Size);
        IconSetter(seriesButoon,"src/main/resources/Images/HomePage/Series.png",IV_Size);
        IconSetter(filmButton,"src/main/resources/Images/HomePage/Movie.png",IV_Size);

        try {
            serie=SerieController.GetAllSeries();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        searchBarInit(serie,films);
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

