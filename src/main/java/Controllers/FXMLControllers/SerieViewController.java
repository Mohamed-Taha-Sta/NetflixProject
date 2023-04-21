package Controllers.FXMLControllers;

import Controllers.ProducerController;
import Controllers.SeasonController;
import Entities.Producer;
import Entities.Season;
import Entities.Serie;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.*;

public class SerieViewController implements Initializable {
    public VBox SeasonView;
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
        VideoPlayerController.setPageName("SerieView");
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

    public List<Season> RetrieveSeasons()throws Exception{
        return SeasonController.FindSeasonSerieID(DataHolderSeries.getSelectedSeries().getId());
    }

    public void ScrollItems(List<?> items){

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Season> seasons;
        try {
            seasons = RetrieveSeasons();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        VBox seasonHolder = new VBox();
        HBox season2 = new HBox();
        int seasonNbr = 0;
        season2.setSpacing(10);
        season2.setAlignment(Pos.CENTER);
        for (Season s : seasons) {
            seasonHolder.setSpacing(10);
            System.out.println(s.getName());
            Label seasonLabel = new Label(s.getName());
            System.out.println(seasonLabel);
            ImageView imgView = new ImageView();
            ImageSetter(imgView, s.getThumbnail().toURI().toString(), 160, 90);
            ImageClipper(imgView);
            seasonHolder.getChildren().addAll(imgView, seasonLabel);
            season2.getChildren().add(seasonHolder);

            seasonHolder = new VBox();
            seasonNbr++;
            if (seasonNbr == 2) {
                SeasonView.getChildren().add(season2);
                seasonNbr = 0;
                season2 = new HBox();
                season2.setSpacing(10);
                season2.setAlignment(Pos.CENTER);
            }
        }

        if (seasonNbr > 0) {
            SeasonView.getChildren().add(season2);
        }
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
