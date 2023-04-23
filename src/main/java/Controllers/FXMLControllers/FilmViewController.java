package Controllers.FXMLControllers;

import Controllers.Avis_FilmController;
import Controllers.ProducerController;
import Entities.Film;
import Entities.Producer;
import Utils.DataHolder;
import Utils.DataHolderFilm;
import Utils.DataHolderSeries;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.ImageSetter;
import static Utils.RepeatableFunction.formatter;

public class FilmViewController implements Initializable {
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
    @FXML
    public Label Actors;;
    public Button thumbUp;
    public Button thumbDown;

    public Button WatchBtn;
    static Producer prod= ProducerController.getProdByID(DataHolderFilm.getSelectedFilm().getId_realisateur());



    public void InfoSetter(){
        FilmName.setText(DataHolderFilm.getSelectedFilm().getNom());
        DirectLabel.setText(prod.getNom()+" "+prod.getPrenom());
        dateLabel.setText(DataHolderFilm.getSelectedFilm().getAnnerdesortie().format(formatter));
        genreLabel.setText((DataHolderFilm.getSelectedFilm().getListegenre()).toString());
        Actors.setText(DataHolderFilm.getSelectedFilm().getActeur().toString());
        ImageSetter(Thumbnail, DataHolderFilm.getSelectedFilm().getImg().toURI().toString(), 240, 135);
        Description.setText(DataHolderFilm.getSelectedFilm().getDescription());
        if(Avis_FilmController.Avis_Exist(DataHolderFilm.getSelectedFilm(), DataHolder.getUser())){
            FilmOpinion.setText(Avis_FilmController.FIND_avis(DataHolderFilm.getSelectedFilm(), DataHolder.getUser()));
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
