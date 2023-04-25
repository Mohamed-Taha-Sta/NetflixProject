package Controllers.FXMLControllers;

import Controllers.Avis_EpisodeController;
import Controllers.ScoreEpisodeController;
import Controllers.VuesEpisodeController;
import Utils.DataHolder;
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
import org.controlsfx.control.Rating;

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
    public Button watchBtn;
    public Button SubmitBtn;
    public Button DeleteBtn;
    public Rating ratings;
    public ImageView Preview;

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
        EpisodeOpinion.setEditable(true);
    }

    public void OnSubmit(){
        if(EpisodeOpinion.isEditable()){
            if(Avis_EpisodeController.Avis_Exist(DataHolderEpisode.getSelectedEpisode(), DataHolder.getUser())){
                Avis_EpisodeController.modif_avis(DataHolderEpisode.getSelectedEpisode(), DataHolder.getUser(),EpisodeOpinion.getText());
                EpisodeOpinion.setEditable(false);
            }
            else {
                Avis_EpisodeController.add_avis(DataHolderEpisode.getSelectedEpisode(), DataHolder.getUser(),EpisodeOpinion.getText());
                EpisodeOpinion.setEditable(false);
            }
        }
    }

    public void OnDelete(){
        EpisodeOpinion.setText("");
        Avis_EpisodeController.delete_avis(DataHolderEpisode.getSelectedEpisode(), DataHolder.getUser());
        EpisodeOpinion.setEditable(false);

    }

    public void InfoSetter(){
        EpisodeName.setText(DataHolderEpisode.getSelectedEpisode().getName());
        DirectLabel.setText(prod.getNom() + " " + prod.getPrenom());
        dateLabel.setText(DataHolderEpisode.getSelectedEpisode().getPremiereDate().format(formatter));
        Description.setText(DataHolderEpisode.getSelectedEpisode().getDescription());
        ImageSetter(Thumbnail,DataHolderEpisode.getSelectedEpisode().getImage().toURI().toString(),240,135);
        ImageClipper(Thumbnail);
        ImageSetter(Preview,DataHolderEpisode.getSelectedEpisode().getImage().toURI().toString(),480,270);
        ImageClipper(Preview);
        if(Avis_EpisodeController.Avis_Exist(DataHolderEpisode.getSelectedEpisode(), DataHolder.getUser())){
           EpisodeOpinion.setText(Avis_EpisodeController.FIND_avis(DataHolderEpisode.getSelectedEpisode(), DataHolder.getUser()));
        }
        if(ScoreEpisodeController.Score_Exist(DataHolderEpisode.getSelectedEpisode(), DataHolder.getUser())){
            ratings.setRating(ScoreEpisodeController.RetrieveUserScore(DataHolderEpisode.getSelectedEpisode(), DataHolder.getUser()));
            ratings.setUpdateOnHover(false);
        }

    }
    public void OnRating(){
        if(ScoreEpisodeController.Score_Exist(DataHolderEpisode.getSelectedEpisode(),DataHolder.getUser())){
            if(ratings.getRating()>5.0){
                ratings.setRating(5.0);
            }
            ScoreEpisodeController.Update_Score(DataHolderEpisode.getSelectedEpisode(), DataHolder.getUser(),ratings.getRating());
        }
        else {
            if(ratings.getRating()>5.0){
                ratings.setRating(5.0);
            }
            ScoreEpisodeController.Add_Score(DataHolderEpisode.getSelectedEpisode(), DataHolder.getUser(),ratings.getRating());
            ratings.setUpdateOnHover(false);
        }
    }

    public void OnWatch() throws Exception{
        VideoPlayerController.SetPath(DataHolderEpisode.getSelectedEpisode().getMedia().getPath());
        if(!VuesEpisodeController.Vue_Exist(DataHolderEpisode.getSelectedEpisode(),DataHolder.getUser())){
            VuesEpisodeController.Add_Vues(DataHolderEpisode.getSelectedEpisode(),DataHolder.getUser());
        }
        VideoPlayerController.setPageName("EpisodeView");
        HelloApplication.SetRoot("VideoPlayer");
    }

    public void OnHomeClick() throws Exception {
        prod=null;
        HelloApplication.SetRoot("HomePage");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InfoSetter();
        IconSetter(homeButton, "src/main/resources/Images/HomePage/HomeButton.png", 40);
        IconSetter(seriesButoon, "src/main/resources/Images/HomePage/Series.png", 40);
        IconSetter(filmButton, "src/main/resources/Images/HomePage/Movie.png", 40);
        IconSetter(BackBtn, "src/main/resources/Images/Design/BackButton.png", 70);
    }
}
