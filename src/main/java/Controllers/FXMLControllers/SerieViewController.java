package Controllers.FXMLControllers;

import Controllers.ProducerController;
import Entities.Producer;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.*;

public class SerieViewController implements Initializable {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
    public Label SerieName2;
    public TextArea Description;
    public Label dateLabel;
    public Label genreLabel;

    public void OnBack() throws Exception {
        HelloApplication.SetRoot("HomePage");
    }

    @FXML
    public void OnFilmClick() throws Exception {
        HelloApplication.SetRoot("FilmPage");
    }

    public void OnSeriesClick() throws Exception {
        HelloApplication.SetRoot("SeriesPage");
    }

    public void OnSynopsisClick()throws Exception{
        VideoPlayerController.SetPath(DataHolderSeries.getSelectedSeries().getSynopsis().getPath());
        HelloApplication.SetRoot("VideoPlayer");
    }

    public void InfoSetter() {
        SerieName.setText(DataHolderSeries.getSelectedSeries().getNom());
        Producer prod=ProducerController.getProdByID(DataHolderSeries.getSelectedSeries().getID_PROD());
        DirectLabel.setText(prod.getNom()+" "+prod.getPrenom() );
        ImageSetter(Thumbnail, DataHolderSeries.getSelectedSeries().getImg().toURI().toString(), 240, 135);
        Description.setText(DataHolderSeries.getSelectedSeries().getDescription());
        dateLabel.setText(DataHolderSeries.getSelectedSeries().getAnnerdesortie().format(formatter));
        genreLabel.setText(DataHolderSeries.getSelectedSeries().getListegenre().toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        IconSetter(homeButton, "src/main/resources/Images/HomePage/HomeButton.png", 40);
        IconSetter(seriesButoon, "src/main/resources/Images/HomePage/Series.png", 40);
        IconSetter(filmButton, "src/main/resources/Images/HomePage/Movie.png", 40);
        ImageClipper(Thumbnail);
        IconSetter(ThumbUp, "src/main/resources/Images/Design/ThumbUp.png", 40);
        IconSetter(ThumbDown, "src/main/resources/Images/Design/ThumbDown.png", 40);
        IconSetter(BackBtn, "src/main/resources/Images/Design/BackButton.png", 70);
        InfoSetter();

    }
}
