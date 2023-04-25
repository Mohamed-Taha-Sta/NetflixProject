package Controllers.FXMLControllers;

import Controllers.Avis_FilmController;
import Controllers.ProducerController;
import Controllers.ScoreFilmController;
import Controllers.VuesFilmController;
import Entities.Film;
import Entities.Producer;
import Utils.DataHolder;
import Utils.DataHolderFilm;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

import java.net.URL;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.*;
import static Utils.RepeatableFunction.IconSetter;

public class FilmViewController implements Initializable {
    static String path="HomePage";
    static Producer prod;
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
    public Label FilmName;
    @FXML
    public Label DirectLabel;
    @FXML
    public Button SynopsisBtn;
    @FXML
    public Label dateLabel;
    @FXML
    public TextArea Description;
    @FXML
    public Label genreLabel;
    @FXML
    public TextArea FilmOpinion;
    @FXML
    public Button EditBtn;
    @FXML
    public Button SaveBtn;
    @FXML
    public Button DeleteBtn;
    @FXML
    public VBox SeasonView;
    public Button WatchBtn;
    public Rating ratings;


    public static void setPath(String path) {
        FilmViewController.path = path;
    }


    public void OnSynopsis() throws Exception {
        VideoPlayerController.SetPath(DataHolderFilm.getSelectedFilm().getSynopsis().getPath());
        VideoPlayerController.setPageName("FilmView");
        HelloApplication.SetRoot("VideoPlayer");
    }

    public void OnWatch() throws Exception {
        VideoPlayerController.SetPath(DataHolderFilm.getSelectedFilm().getFilm().getPath());
        if(!VuesFilmController.Vue_Exist(DataHolderFilm.getSelectedFilm(),DataHolder.getUser())){
            VuesFilmController.Add_Vues(DataHolderFilm.getSelectedFilm(),DataHolder.getUser());
        }
        VideoPlayerController.setPageName("FilmView");
        HelloApplication.SetRoot("VideoPlayer");
    }



    public void OnBack() throws Exception {
        DataHolderFilm.setSelectedFilm(null);
        HelloApplication.SetRoot(path);
    }
    @FXML
    public void OnFilmClick() throws Exception {
        HelloApplication.SetRoot("FilmPage");
    }

    public void OnSeriesClick() throws Exception {
        HelloApplication.SetRoot("SeriesPage");
    }

    public void OnHomeClick() throws Exception {
        HelloApplication.SetRoot("HomePage");
    }

    public void OnEdit(){
        FilmOpinion.setEditable(true);
    }

    public void OnSubmit(){
        if(FilmOpinion.isEditable()) {

            if (Avis_FilmController.Avis_Exist(DataHolderFilm.getSelectedFilm(), DataHolder.getUser())) {
                Avis_FilmController.modif_avis(DataHolderFilm.getSelectedFilm(), DataHolder.getUser(), FilmOpinion.getText());
                FilmOpinion.setEditable(false);
            } else {
                Avis_FilmController.add_avis(DataHolderFilm.getSelectedFilm(), DataHolder.getUser(), FilmOpinion.getText());
                FilmOpinion.setEditable(false);
            }
        }
    }

    public void OnDelete(){
        FilmOpinion.setText("");
        Avis_FilmController.delete_avis(DataHolderFilm.getSelectedFilm(),DataHolder.getUser());
        FilmOpinion.setEditable(false);
    }

    public void InfoSetter(){

        FilmName.setText(DataHolderFilm.getSelectedFilm().getNom());
        DirectLabel.setText(prod.getNom()+" "+prod.getPrenom());
        dateLabel.setText(DataHolderFilm.getSelectedFilm().getAnnerdesortie().format(formatter));
        genreLabel.setText((DataHolderFilm.getSelectedFilm().getListegenre()).toString());
        ImageSetter(Thumbnail, DataHolderFilm.getSelectedFilm().getImg().toURI().toString(), 240, 135);
        ImageClipper(Thumbnail);
        Description.setText(DataHolderFilm.getSelectedFilm().getDescription());
        if(Avis_FilmController.Avis_Exist(DataHolderFilm.getSelectedFilm(), DataHolder.getUser())){
            FilmOpinion.setText(Avis_FilmController.FIND_avis(DataHolderFilm.getSelectedFilm(), DataHolder.getUser()));
        }
        if(ScoreFilmController.Score_Exist(DataHolderFilm.getSelectedFilm(),DataHolder.getUser())){
            ratings.setRating(ScoreFilmController.RetrieveUserScore(DataHolderFilm.getSelectedFilm(), DataHolder.getUser()));
            ratings.setUpdateOnHover(false);
        }
    }

    public void OnRating(){
        if(ScoreFilmController.Score_Exist(DataHolderFilm.getSelectedFilm(), DataHolder.getUser())){
            ScoreFilmController.Update_Score(DataHolderFilm.getSelectedFilm(), DataHolder.getUser(),ratings.getRating());
            System.out.println("changed rating ");

        }
        else{
            ScoreFilmController.Add_Score(DataHolderFilm.getSelectedFilm(), DataHolder.getUser(),ratings.getRating());
            System.out.println("changed rating ");
            ratings.setUpdateOnHover(false);
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IconSetter(homeButton, "src/main/resources/Images/HomePage/HomeButton.png", 40);
        IconSetter(seriesButoon, "src/main/resources/Images/HomePage/Series.png", 40);
        IconSetter(filmButton, "src/main/resources/Images/HomePage/Movie.png", 40);
        IconSetter(BackBtn, "src/main/resources/Images/Design/BackButton.png", 70);

        if(prod==null){
            prod= ProducerController.getProdByID(DataHolderFilm.getSelectedFilm().getId_realisateur());
        }
        InfoSetter();
    }
}
