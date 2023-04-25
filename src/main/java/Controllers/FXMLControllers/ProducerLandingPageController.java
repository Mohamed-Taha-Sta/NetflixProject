package Controllers.FXMLControllers;

import Controllers.*;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.isTextExceedingLength;
import static Utils.RepeatableFunction.showErrorMessage;

public class ProducerLandingPageController implements Initializable {

    //LeftMenu Vars
    public ImageView Profile_Image;
    public Text ProfileName;
    public Button ProfileBtn;

    //Profile Menu Anchor
    public TextField prenameField;
    public Button PrenameBtn;
    public TextField namefield;
    public AnchorPane ProfileMenu;

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
    public TableColumn<Film, String> MovieName;
    public TableView<Serie> SeriesTable = new TableView<>();
    public TableColumn<Film, String> SeriesName;


    //Password Menu
    public AnchorPane PassMenu;
    public TextField OldPass;
    public TextField newPass;
    public TextField PassConf;
    public Button confirmBtn;
    public Label passAlert;
    public Label SeriesAlertText;
    public Label MovieAlertText;

    public void OnAddSeries() throws Exception {
        HelloApplication.SetRoot("AddSeries");
    }

    public void OnAddMovie() throws Exception {
        HelloApplication.SetRoot("AddFilm");
    }


    public void OnProfilebtn() {
        ProfileMenu.setVisible(true);
        PassMenu.setVisible(false);
    }


    public void OnPassbtn() {
        ProfileMenu.setVisible(false);
        PassMenu.setVisible(true);
    }


    public void OnClickMovie() throws Exception {

        Film selectedMovie = MoviesTable.getSelectionModel().getSelectedItem();
        if (selectedMovie == null) {
            MovieAlertText.setText("No movie was selected");
        } else {
            DataHolderFilm.setSelectedFilm(selectedMovie);
            HelloApplication.SetRoot("ProducerFilmView");
        }
    }


    public void OnClickSeries() throws Exception {

        Serie selectedSeries = SeriesTable.getSelectionModel().getSelectedItem();
        if (selectedSeries == null) {
            SeriesAlertText.setText("No Series was selected");
        } else {
            DataHolderSeries.setSelectedSeries(SerieController.GetSerieByID(selectedSeries.getId()).get(0));
            HelloApplication.SetRoot("ProducerSeriesView");
        }

    }


    public void OnLogOutBtn() throws Exception {
        HelloApplication.SetRoot("LoginPage");
        DataHolder.setProducer(null);
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

    public void OnPrenameBtn() {
        if (prenameField.getText().isEmpty()) {
            showErrorMessage(AlertText, "Your FirstName field is empty");
        } else if (isTextExceedingLength(prenameField, 50)) {
            showErrorMessage(AlertText, "First Name field is too long");
        } else {
            ProducerController.modifprenom(DataHolder.getProducer().getId(), prenameField.getText());
            DataHolder.getProducer().setPrenom(prenameField.getText());
            PrenameLable.setText(prenameField.getText());
            ProfileName.setText(DataHolder.getProducer().getNom() + " " + DataHolder.getProducer().getPrenom());
        }
    }


    public void OnMailBtn(ActionEvent actionEvent) {
        if (mailfield.getText().isEmpty()) {
            showErrorMessage(AlertText, "Your Mail field is empty");
        } else if (isTextExceedingLength(mailfield,50)) {
            showErrorMessage(AlertText,"Mail field is too long");
        } else {
            ProducerController.modifmail(DataHolder.getProducer().getId(),mailfield.getText());
            DataHolder.getProducer().setEmail(mailfield.getText());
            MailLabel.setText(mailfield.getText());
        }
    }


    public void OnNameBtn() {
        if (namefield.getText().isEmpty() || namefield.getText().equals("")) {
            showErrorMessage(AlertText, "Your LastName field is empty");
        } else if (isTextExceedingLength(namefield, 50)) {
            showErrorMessage(AlertText, "Last Name field is too long");
        } else {
            ProducerController.modifnom(DataHolder.getProducer().getId(), namefield.getText());
            DataHolder.getProducer().setNom(namefield.getText());
            NameLabel.setText(namefield.getText());
            ProfileName.setText(DataHolder.getProducer().getNom() + " " + DataHolder.getProducer().getPrenom());
        }
    }

    public void OnConfirm() {
        if (OldPass.getText().isEmpty()) {
            showErrorMessage(passAlert, "Old Password Required!");
        } else if (isTextExceedingLength(OldPass,50)) {
            showErrorMessage(passAlert,"Your old password is too long");
        } else if (newPass.getText().isEmpty()) {
            showErrorMessage(passAlert, "Your new Password is empty!");
        } else if (isTextExceedingLength(newPass,50)) {
            showErrorMessage(passAlert,"Your new pass is too long");
        } else if (PassConf.getText().isEmpty()) {
            showErrorMessage(passAlert, "Please Confirm your Password!");
        } else if (isTextExceedingLength(PassConf,50)) {
            showErrorMessage(AlertText,"your confirmation pass is too long");
        } else if (isTextExceedingLength(OldPass, 50) || isTextExceedingLength(newPass, 50) || isTextExceedingLength(PassConf, 50)) {
            showErrorMessage(passAlert, "Password field is too long");
        } else if (!newPass.getText().equals(PassConf.getText())) {
            showErrorMessage(passAlert, "Your confirm password is wrong!");
        } else if (newPass.getText().equals(OldPass.getText())) {
            showErrorMessage(passAlert, "New Password already in use!");
        } else if (!OldPass.getText().equals(DataHolder.getProducer().getpassword())) {
            showErrorMessage(passAlert, "Wrong Password!");
        } else {
            ProducerController.modifpassword(DataHolder.getProducer().getId(), newPass.getText());
            showErrorMessage(passAlert, "Your password was changed Successfully!");
            PassConf.setText("");
            OldPass.setText("");
            newPass.setText("");
        }
    }


    public ProducerLandingPageController() throws SQLException, IOException {
    }

    public static ObservableList<Serie> Series;
    public static ObservableList<Film> Films;


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

        SeriesName.setCellValueFactory(new PropertyValueFactory<>("nom"));

        if (DataHolderSeries.getSeries() == null || DataHolderSeries.getSeries().isEmpty()) {

            List<Serie> seriesList = null;
            try {
                seriesList = SerieController.GetSeriesByProducer(DataHolder.getProducer());
                System.out.println(seriesList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Series = FXCollections.observableArrayList(seriesList);

            DataHolderSeries.setSeries(Series);
        }

        if (DataHolderFilm.getFilms() == null || DataHolderFilm.getFilms().isEmpty()) {

            List<Film> filmList = null;
            filmList = FilmController.FindByproducer(DataHolder.getProducer());
            System.out.println(filmList);
            Films = FXCollections.observableArrayList(filmList);

            DataHolderFilm.setFilms(Films);
        }

        MoviesTable.setItems(Films);
        SeriesTable.setItems(Series);

    }




}
