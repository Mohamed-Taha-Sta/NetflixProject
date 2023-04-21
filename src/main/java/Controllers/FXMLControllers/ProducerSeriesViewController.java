package Controllers.FXMLControllers;

import Controllers.SerieController;
import Utils.DataHolder;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.IconSetter;
import static Utils.RepeatableFunction.ImageClipper;

public class ProducerSeriesViewController implements Initializable {

    public Button homeButton;
    public Button seriesButoon;
    public Button filmButton;
    public Label SerieName;
    public Label RatingLabel;
    public Label DirectLabel;
    public Label dateLabel;
    public Label genreLabel;
    public TextArea Description;
    public ImageView Thumbnail;
    public Button ThumbUp;
    public Button ThumbDown;
    public Button BackBtn;
    public Button SynopsisBtn;

    public void onBack() throws Exception {
        HelloApplication.SetRoot("ProducerLandingPageController");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image img=new Image(new File("src/main/resources/Images/theWitcher.jpg").toURI().toString());

        SerieName.setText(DataHolderSeries.getSelectedSeries().getNom());
        DirectLabel.setText(DataHolder.getProducer().getNom());
        Thumbnail.setImage(new Image(DataHolderSeries.getSelectedSeries().getImg().toURI().toString()));
        Thumbnail.setFitWidth(240);
        Thumbnail.setFitHeight(135);
        Description.setText(DataHolderSeries.getSelectedSeries().getDescription());
        dateLabel.setText(DataHolderSeries.getDebutDate().toString());
        genreLabel.setText(DataHolderSeries.getGenreList().toString());
        try {
            RatingLabel.setText(String.valueOf(SerieController.StreamAverageScore(DataHolderSeries.getSelectedSeries())));
        } catch (SQLException e) {
            System.out.println("SQL Error getting the Rating");
        } catch (IOException e) {
            System.out.println("IO Error getting the Rating");
        }
        IconSetter(homeButton,"src/main/resources/Images/HomePage/HomeButton.png",40);
        IconSetter(seriesButoon,"src/main/resources/Images/HomePage/Series.png",40);
        IconSetter(filmButton,"src/main/resources/Images/HomePage/Movie.png",40);
        ImageClipper(Thumbnail);
        IconSetter(ThumbUp,"src/main/resources/Images/Design/ThumbUp.png",40);
        IconSetter(ThumbDown,"src/main/resources/Images/Design/ThumbDown.png",40);
        IconSetter(BackBtn,"src/main/resources/Images/Design/BackButton.png",70);


    }



}
