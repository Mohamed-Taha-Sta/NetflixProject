package Controllers.FXMLControllers;

import Controllers.FilmController;
import Controllers.SerieController;
import Entities.Film;
import Entities.Serie;
import Utils.DataHolder;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class ProducerLandingPageController implements Initializable {

    //LeftMenu Vars
    public ImageView Profile_Image;
    public Text ProfileName;
    public Button ProfileBtn;

    //Profile Menu Anchor
    public TextField prenameField;
    public Button PrenameBtn;
    public TextField namefield;
    public Button NameBtn;
    public TextField mailfield;
    public Button MailBtn;
    public Button LogoutBtn;
    public Button AddSeries;
    public Button AddMovie;
    public Label NameLabel;
    public Label PrenameLable;
    public Label MailLabel;
    public Label AlertText;

    public TableView<Film> MoviesTable = new TableView<>();
    public TableColumn<Film,String> MovieName;
    public TableColumn<Film,Long> MovieScore;
    public TableView<Serie> SeriesTable = new TableView<>();
    public TableColumn<Film,String> SeriesName;
    public TableColumn<Film,String> SeriesDescription;


    //Password Menu
    public AnchorPane PassMenu;
    public TextField OldPass;
    public TextField newPass;
    public TextField PassConf;
    public Button confirmBtn;
    public Label passAlert;
    public Label GenreAlertText;
    public Label ActorsAlertText;

    public ProducerLandingPageController() throws SQLException, IOException {
    }
    ObservableList<Serie> Series = FXCollections.observableList(SerieController.GetSeriesByProducer(DataHolder.getProducer()));
    ObservableList<Film> Films = FXCollections.observableList(FilmController.FindByproducer(DataHolder.getProducer()));


    public void OnloadProfile() throws Exception {

        ProfileName.setText(DataHolder.getProducer().getNom() + " " + DataHolder.getProducer().getPrenom());
        NameLabel.setText(DataHolder.getProducer().getNom());
        PrenameLable.setText(DataHolder.getProducer().getPrenom());
        MailLabel.setText(DataHolder.getProducer().getEmail());


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            OnloadProfile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        MovieName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        MovieScore.setCellValueFactory(new PropertyValueFactory<>("Score"));

//        SeriesName.setCellValueFactory(new PropertyValueFactory<>("name"));
//        SeriesDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));

        MoviesTable.setItems(Films);
        SeriesTable.setItems(Series);
    }


    private void showErrorMessage(Label label, String message) {
        label.setText(message);
        label.setOpacity(1);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            label.setOpacity(0);
        }));
        timeline.play();
    }


}
