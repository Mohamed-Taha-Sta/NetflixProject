package Controllers.FXMLControllers;

import Controllers.ActorController;
import Controllers.FilmController;
import Controllers.ProducerController;
import Controllers.SerieController;
import Entities.Film;
import Entities.Serie;
import Utils.DataHolder;
import Utils.DataHolderFilm;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ActorLandingPageController implements Initializable {


    public Button LogoutBtn;
    public Label PrenameLable;
    public Button PrenameBtn;
    public TextField prenameField;
    public AnchorPane ProfileMenu;
    public Button PassBtn;
    public Button ProfileBtn;
    public Text ProfileName;
    public TextField namefield;
    public Button NameBtn;
    public Label MailLabel;
    public TextField mailfield;
    public Button MailBtn;
    public Label AlertText;
    public TableView<Film> MoviesTable = new TableView<>();
    public TableColumn<Film,String> MovieName;
    public Label NameLabel;
    public TableView<Serie> SeriesTable = new TableView<>();
    public TableColumn<Film,String> SeriesName;
    public Label SeriesAlertText;
    public Button AddMovie;
    public Button AddSeries;
    public TextField OldPass;
    public TextField newPass;
    public TextField PassConf;
    public Label passAlert;
    public Button confirmBtn;
    public AnchorPane PassMenu;
    public Label MovieAlertText;

    public void OnProfilebtn(ActionEvent actionEvent) {
        ProfileMenu.setVisible(true);
        PassMenu.setVisible(false);
    }

    public void OnPassbtn(ActionEvent actionEvent) {
        ProfileMenu.setVisible(false);
        PassMenu.setVisible(true);
    }

    public void OnLogOutBtn(ActionEvent actionEvent) throws Exception {
        HelloApplication.SetRoot("LoginPage");
        DataHolder.setActor(null);
    }

    public void OnPrenameBtn(ActionEvent actionEvent) {
        if (prenameField.getText().isEmpty()) {
            showMessage(AlertText,"Your FirstName field is empty");
        } else {
            ActorController.modifprenom(DataHolder.getActor().getID(),prenameField.getText());
            DataHolder.getActor().setPrename(prenameField.getText());
            PrenameLable.setText(prenameField.getText());
            ProfileName.setText(DataHolder.getActor().getName() + " " + DataHolder.getActor().getPrename());
        }
    }

    public void OnNameBtn(ActionEvent actionEvent) {
        if (namefield.getText().isEmpty() || namefield.getText().equals("")) {
            showMessage( AlertText,"Your LastName field is empty");
        } else {
            ActorController.modifnom(DataHolder.getActor().getID(),namefield.getText());
            DataHolder.getActor().setName(namefield.getText());
            NameLabel.setText(namefield.getText());
            ProfileName.setText(DataHolder.getActor().getName() + " " + DataHolder.getActor().getPrename());
        }

    }

    public void OnMailBtn(ActionEvent actionEvent) {
        if (mailfield.getText().isEmpty()) {
            showMessage(AlertText, "Your Mail field is empty");
        } else {
            ActorController.modifmail(DataHolder.getActor().getID(),mailfield.getText());
            DataHolder.getActor().setMail(mailfield.getText());
            MailLabel.setText(mailfield.getText());
        }
    }


    public void OnConfirm(ActionEvent actionEvent) {

        if (OldPass.getText().isEmpty()) {
            showMessage(passAlert, "Old Password Required!");
        } else if (newPass.getText().isEmpty()) {
            showMessage(passAlert, "Your new Password is empty!");
        } else if (PassConf.getText().isEmpty()) {
            showMessage(passAlert, "Please Confirm your Password!");
        } else if (!newPass.getText().equals(PassConf.getText())) {
            showMessage(passAlert, "Your confirm password is wrong!");

        } else if (newPass.getText().equals(OldPass.getText())) {
            showMessage(passAlert, "New Password already in use!");
        } else if (!OldPass.getText().equals(DataHolder.getActor().getPassword())) {
            showMessage(passAlert,"Wrong Password!");
        } else {
            ActorController.modifpassword(DataHolder.getActor().getID(),newPass.getText());
            showMessage(passAlert,"Your password was changed Successfully!");
            PassConf.setText("");
            OldPass.setText("");
            newPass.setText("");
        }

    }



    private void showMessage(Label label, String message) {
        label.setText(message);
        label.setOpacity(1);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            label.setOpacity(0);
        }));
        timeline.play();
    }


    public void OnClickSeries() throws Exception {

        Serie selectedSeries = SeriesTable.getSelectionModel().getSelectedItem();
        if (selectedSeries == null)
        {
            showMessage(AlertText,"No Series was selected");
        }
        else
        {
            DataHolderSeries.setSelectedSeries(SerieController.GetSerieByID(selectedSeries.getId()).get(0));
            HelloApplication.SetRoot("ActorSeriesView");
        }

    }


    public void OnClickMovie(MouseEvent mouseEvent) throws Exception {
        Film selectedMovie = MoviesTable.getSelectionModel().getSelectedItem();
        if (selectedMovie == null)
        {
            showMessage(MovieAlertText,"No movie was selected");
        }
        else
        {
            DataHolderFilm.setSelectedFilm(selectedMovie);
            HelloApplication.SetRoot("ActorFilmView");
        }
    }



    public void OnloadProfile() throws Exception {

        ProfileName.setText(DataHolder.getActor().getName() + " " + DataHolder.getActor().getPrename());
        NameLabel.setText(DataHolder.getActor().getName());
        PrenameLable.setText(DataHolder.getActor().getPrename());
        MailLabel.setText(DataHolder.getActor().getMail());


    }
    public static ObservableList<Serie> Series;
    public static ObservableList<Film> Films;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        try {
            OnloadProfile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        MovieName.setCellValueFactory(new PropertyValueFactory<>("nom"));

        SeriesName.setCellValueFactory(new PropertyValueFactory<>("nom"));

        if (DataHolderSeries.getSeries()==null || DataHolderSeries.getSeries().isEmpty()) {

            List<Serie> seriesList = SerieController.FindSeriesByActor(DataHolder.getActor());
            Series = FXCollections.observableArrayList(seriesList);

            DataHolderSeries.setSeries(Series);
        }

        if(DataHolderFilm.getFilms()==null || DataHolderFilm.getFilms().isEmpty()) {

            List<Film> filmList = null;
            filmList = FilmController.FindByActor(DataHolder.getActor());
            Films = FXCollections.observableArrayList(filmList);

            DataHolderFilm.setFilms(Films);
        }

        MoviesTable.setItems(Films);
        SeriesTable.setItems(Series);





    }

}
