package Controllers.FXMLControllers;

import Controllers.EpisodeController;
import Entities.Episode;
import Utils.DataHolderEpisode;
import Utils.DataHolderSeason;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static Controllers.FXMLControllers.SerieViewController.prod;
import static Utils.RepeatableFunction.*;

public class SeasonViewController implements Initializable {
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
    public Label SeasonName;
    @FXML
    public Label DirectLabel;
    @FXML
    public Label dateLabel;
    @FXML
    public Label genreLabel;
    @FXML
    public Button SynopsisBtn;
    @FXML
    public TextArea Description;
    @FXML
    public TextArea SeasonOpinion;
    @FXML
    public Button EditBtn;
    @FXML
    public Button SaveBtn;
    @FXML
    public Button Cancelbtn;
    @FXML
    public VBox EpisodeView;

    List<Episode> episodes;
    public void OnBack() throws Exception {
        HelloApplication.SetRoot("SerieView");
    }
    @FXML
    public void OnFilmClick() throws Exception {
        HelloApplication.SetRoot("FilmPage");
    }

    public void OnSeriesClick() throws Exception {
        HelloApplication.SetRoot("SeriesPage");
    }

    public void OnSynopsisClick()throws Exception{
        VideoPlayerController.SetPath(DataHolderSeason.getSelectedSeason().getSynopsis().getPath());
        VideoPlayerController.setPageName("SeasonView");
        HelloApplication.SetRoot("VideoPlayer");
    }

    public void InfoSetter() throws SQLException, IOException {
        SeasonName.setText(DataHolderSeries.getSelectedSeries().getNom()+" "+ DataHolderSeason.getSelectedSeason().getName());
        Description.setText(DataHolderSeason.getSelectedSeason().getDescription());
        dateLabel.setText(DataHolderSeason.getSelectedSeason().getDebutDate().format(formatter));
        genreLabel.setText(String.valueOf(RetrieveEpisodes().size()));

        DirectLabel.setText(prod.getNom() + " " + prod.getPrenom());
        ImageSetter(Thumbnail,DataHolderSeason.getSelectedSeason().getThumbnail().toURI().toString(),240,135);
        //SeasonOpinion.setText(whatever Im calling);
    }



    public List<Episode> RetrieveEpisodes() {
        try {
            return EpisodeController.FindEpisodeSeasonID(DataHolderSeason.getSelectedSeason().getID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

    }


    public void handleImageClick(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        Episode selectedEpisode = null;
        for (Episode s : episodes) {
            if (imageView.getImage().getUrl().equals(String.valueOf(s.getImage().toURI()))) {
                selectedEpisode = s;
                break;
            }
        }
        if (selectedEpisode != null) {
            try {
                DataHolderEpisode.setSelectedEpisode(EpisodeController.FindEpisodeID(selectedEpisode.getID()).get(0));
                System.out.println(selectedEpisode);
                HelloApplication.SetRoot("EpisodeView");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        episodes=RetrieveEpisodes();
        try {
            InfoSetter();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("retrieved episodes" + episodes);
        List<Episode> episodes=RetrieveEpisodes();
        VBox episodeHolder = new VBox();
        HBox episode2 = new HBox();
        int episodeNbr = 0;
        episode2.setSpacing(10);
        episode2.setAlignment(Pos.CENTER);
        for (Episode s : episodes) {
            episodeHolder.setSpacing(10);
            episodeHolder.setAlignment(Pos.CENTER);
            Label episodeLabel = new Label(s.getName());
            ImageView imgView = new ImageView();
            ImageSetter(imgView, s.getImage().toURI().toString(), 160, 90);
            ImageClipper(imgView);
            episodeLabel.setCursor(Cursor.cursor("hand"));
            imgView.setCursor(Cursor.cursor("hand"));
            imgView.setOnMouseClicked(this::handleImageClick);
            episodeHolder.getChildren().addAll(imgView, episodeLabel);
            episode2.getChildren().add(episodeHolder);
            episodeHolder = new VBox();
            episodeNbr++;
            if (episodeNbr == 3) {
                EpisodeView.getChildren().add(episode2);
                episodeNbr = 0;
                episode2 = new HBox();
                episode2.setSpacing(10);
                episode2.setAlignment(Pos.CENTER);
            }
        }

        if (episodeNbr > 0) {
            EpisodeView.getChildren().add(episode2);
        }

        IconSetter(homeButton, "src/main/resources/Images/HomePage/HomeButton.png", 40);
        IconSetter(seriesButoon, "src/main/resources/Images/HomePage/Series.png", 40);
        IconSetter(filmButton, "src/main/resources/Images/HomePage/Movie.png", 40);
        ImageClipper(Thumbnail);
        IconSetter(BackBtn, "src/main/resources/Images/Design/BackButton.png", 70);
    }

    public void OnEdit(){
        if(!SeasonOpinion.isEditable()){
            SeasonOpinion.setEditable(true);
        }
    }

    public void OnSave(){
        if(SeasonOpinion.isEditable()){
            SeasonOpinion.setEditable(false);
            System.out.println(SeasonOpinion.getText());
        }
    }

    public void OnCancel(){
        if(SeasonOpinion.isEditable()){
            SeasonOpinion.setEditable(false);
            System.out.println(SeasonOpinion.getText());
        }

    }
}
