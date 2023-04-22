package Controllers.FXMLControllers;

import Utils.DataHolderEpisode;
import Utils.DataHolderSeason;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

import static Controllers.FXMLControllers.SerieViewController.prod;
import static Utils.RepeatableFunction.*;
import static Utils.RepeatableFunction.IconSetter;

public class EpisodeViewController implements Initializable {
    @FXML
    public Button homeButton;
    @FXML
    public Button seriesButoon;
    @FXML
    public Button filmButton;
    @FXML
    public Button BackBtn;
    @FXML
    public ImageView Thumbnail;
    @FXML
    public Label EpisodeName;
    @FXML
    public Label DirectLabel;
    @FXML
    public Label dateLabel;
    @FXML
    public Button ThumbUp;
    @FXML
    public Button ThumbDown;
    @FXML
    public Button SynopsisBtn;
    @FXML
    public TextArea Description;
    @FXML
    public TextArea EpisodeOpinion;
    @FXML
    public Button EditBtn;
    @FXML
    public Button SaveBtn;
    @FXML
    public Button Cancelbtn;

    @FXML
    public Button watchBtn;

    public void OnBack() throws Exception {
        HelloApplication.SetRoot("SeasonView");
    }
    @FXML
    public void OnFilmClick() throws Exception {
        HelloApplication.SetRoot("FilmPage");
    }

    public void OnSeriesClick() throws Exception {
        HelloApplication.SetRoot("SeriesPage");
    }

    public void OnSynopsisClick()throws Exception{
        VideoPlayerController.SetPath(DataHolderEpisode.getSelectedEpisode().getSynopsis().getPath());
        VideoPlayerController.setPageName("EpisodeView");
        HelloApplication.SetRoot("VideoPlayer");
    }

    public void OnEdit(){
        if(!EpisodeOpinion.isEditable()){
            EpisodeOpinion.setEditable(true);
        }
    }

    public void OnSave(){
        if(EpisodeOpinion.isEditable()){
            EpisodeOpinion.setEditable(false);
            System.out.println(EpisodeOpinion.getText());
        }
    }

    public void OnCancel(){
        if(EpisodeOpinion
                .isEditable()){
            EpisodeOpinion.setEditable(false);
            System.out.println(EpisodeOpinion.getText());
        }

    }

    public void InfoSetter(){
        EpisodeName.setText(DataHolderSeries.getSelectedSeries().getNom()+" "+ DataHolderSeason.getSelectedSeason().getName()+" "+ DataHolderEpisode.getSelectedEpisode().getName());
        DirectLabel.setText(prod.getNom() + " " + prod.getPrenom());
        dateLabel.setText(DataHolderEpisode.getSelectedEpisode().getPremiereDate().format(formatter));
        Description.setText(DataHolderEpisode.getSelectedEpisode().getDescription());
        ImageSetter(Thumbnail,DataHolderEpisode.getSelectedEpisode().getImage().toURI().toString(),240,135);

    }

    public void OnWatch() throws Exception{
        VideoPlayerController.SetPath(DataHolderEpisode.getSelectedEpisode().getMedia().getPath());
        VideoPlayerController.setPageName("EpisodeView");
        HelloApplication.SetRoot("VideoPlayer");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InfoSetter();
        IconSetter(homeButton, "src/main/resources/Images/HomePage/HomeButton.png", 40);
        IconSetter(seriesButoon, "src/main/resources/Images/HomePage/Series.png", 40);
        IconSetter(filmButton, "src/main/resources/Images/HomePage/Movie.png", 40);
        ImageClipper(Thumbnail);
        IconSetter(BackBtn, "src/main/resources/Images/Design/BackButton.png", 70);
        IconSetter(ThumbUp,"src/main/resources/Images/Design/ThumbUp.png",40);
        IconSetter(ThumbDown,"src/main/resources/Images/Design/ThumbDown.png",40);
    }
}
