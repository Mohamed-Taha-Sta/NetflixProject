package Controllers.FXMLControllers;

import Controllers.ActorController;
import Controllers.FilmController;
import Controllers.SerieController;
import Entities.Film;
import Entities.Serie;
import Utils.*;
import com.example.netflixproject.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

import static Utils.RepeatableFunction.isTextExceedingLength;
import static Utils.RepeatableFunction.showErrorMessage;

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
        DataHolder.setImage(null);
        DataHolder.setUserType(null);
        DataHolder.setEmail(null);
        DataHolder.setPassword(null);
        DataHolderSeries.setSelectedSeries(null);
        DataHolderSeries.setSeries(null);
        DataHolderSeries.setMainActorsList(null);
        DataHolderSeries.setSuppActorsList(null);
        DataHolderSeason.setSelectedSeason(null);
        DataHolderSeason.setPreviousPage(null);
        DataHolderSeason.setSeasonObservableList(null);
        DataHolderEpisode.setEpisodeOBList(null);
        DataHolderEpisode.setPreviousPage(null);
        DataHolderEpisode.setSelectedEpisode(null);
        DataHolderFilm.setSelectedFilm(null);
        DataHolderFilm.setCountryOfOrigin(null);
        DataHolderFilm.setMainActorsList(null);
        DataHolderFilm.setFilms(null);
        DataHolderFilm.setAllTheActors(null);
    }

    public void OnPrenameBtn(ActionEvent actionEvent) {
        if (prenameField.getText().isEmpty()) {
            showErrorMessage(AlertText,"Your FirstName field is empty");
        } else if (isTextExceedingLength(prenameField,50)) {
            showErrorMessage(AlertText,"FirstName field is too long");
        } else {
            ActorController.modifprenom(DataHolder.getActor().getID(),prenameField.getText());
            DataHolder.getActor().setPrename(prenameField.getText());
            PrenameLable.setText(prenameField.getText());
            ProfileName.setText(DataHolder.getActor().getName() + " " + DataHolder.getActor().getPrename());
        }
    }

    public void OnNameBtn(ActionEvent actionEvent) {
        if (namefield.getText().isEmpty() || namefield.getText().equals("")) {
            showErrorMessage( AlertText,"Your LastName field is empty");
        } else if (isTextExceedingLength(namefield,50)) {
            showErrorMessage(AlertText,"LastName field is too long");
        } else{
            ActorController.modifnom(DataHolder.getActor().getID(),namefield.getText());
            DataHolder.getActor().setName(namefield.getText());
            NameLabel.setText(namefield.getText());
            ProfileName.setText(DataHolder.getActor().getName() + " " + DataHolder.getActor().getPrename());
        }

    }

    public void OnMailBtn(ActionEvent actionEvent) {
        if (mailfield.getText().isEmpty()) {
            showErrorMessage(AlertText, "Your Mail field is empty");
        } else if (isTextExceedingLength(mailfield,50)) {
            showErrorMessage(AlertText,"Mail field is too long");
        } else {
            ActorController.modifmail(DataHolder.getActor().getID(),mailfield.getText());
            DataHolder.getActor().setMail(mailfield.getText());
            MailLabel.setText(mailfield.getText());
        }
    }

    public void OnConfirm()  {
        if (OldPass.getText().isEmpty()) {
            showErrorMessage(passAlert, "Old Password Required!");
        } else if (newPass.getText().isEmpty()) {
            showErrorMessage(passAlert, "Your new Password is empty!");
        } else if (PassConf.getText().isEmpty()) {
            showErrorMessage(passAlert, "Please Confirm your Password!");
        } else if (isTextExceedingLength(OldPass,50)||isTextExceedingLength(newPass,50)||isTextExceedingLength(PassConf,50)) {
            showErrorMessage(passAlert, "Password is too long");
        } else if (!newPass.getText().equals(PassConf.getText())) {
            showErrorMessage(passAlert, "Your confirm password is wrong!");
        } else if (newPass.getText().equals(OldPass.getText())) {
            showErrorMessage(passAlert, "New Password already in use!");
        } else if (!OldPass.getText().equals(DataHolder.getActor().getPassword())) {
            showErrorMessage(passAlert,"Wrong Password!");
        } else {
            ActorController.modifpassword(DataHolder.getActor().getID(),newPass.getText());
            showErrorMessage(passAlert,"Your password was changed Successfully!");
            PassConf.setText("");
            OldPass.setText("");
            newPass.setText("");
        }
    }




    public void OnClickSeries() throws Exception {

        Serie selectedSeries = SeriesTable.getSelectionModel().getSelectedItem();
        if (selectedSeries == null)
        {
            showErrorMessage(AlertText,"No Series was selected");
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
            showErrorMessage(MovieAlertText,"No movie was selected");
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
            Set<Serie> serieSet = new HashSet<>(seriesList);
            List<Serie> uniqueSeries = new ArrayList<>(serieSet);
            Series = FXCollections.observableArrayList(uniqueSeries);
            DataHolderSeries.setSeries(Series);
        }

        if(DataHolderFilm.getFilms()==null || DataHolderFilm.getFilms().isEmpty()) {

            List<Film> filmList = FilmController.FindByActor(DataHolder.getActor());
            Set<Film> filmSet = new HashSet<>(filmList);
            List<Film> uniqueFilms = new ArrayList<>(filmSet);
            Films = FXCollections.observableArrayList(uniqueFilms);

            DataHolderFilm.setFilms(Films);
        }

        MoviesTable.setItems(Films);
        SeriesTable.setItems(Series);





    }

}
