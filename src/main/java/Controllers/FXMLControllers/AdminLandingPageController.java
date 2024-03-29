package Controllers.FXMLControllers;

import Controllers.*;
import Entities.Film;
import Entities.Season;
import Entities.Serie;
import Services.SerieService;
import Utils.*;
import com.example.netflixproject.HelloApplication;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static Utils.RepeatableFunction.isTextExceedingLength;
import static Utils.RepeatableFunction.showErrorMessage;

public class AdminLandingPageController implements Initializable {

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
    public TableColumn<Film, String> MovieName;
    public TableColumn<Film, String> MovieScore;
    public TableColumn<Film, String> MovieViews;
    public Label NameLabel;
    public TableView<Serie> SeriesTable = new TableView<>();
    public TableColumn<Serie, String> SeriesName;
    public TableColumn<Serie, String> numSaisons;
    public TableColumn<Serie, String> numEp;
    public TableColumn<Serie, Double> Score;
    public Label SeriesAlertText;
    public TextField OldPass;
    public TextField newPass;
    public TextField PassConf;
    public Label passAlert;
    public Button confirmBtn;
    public AnchorPane PassMenu;
    public Label MovieAlertText;

    private static Set<Serie> serieSetAll = GetSeriesContainer();


    public void OnProfilebtn() {
        ProfileMenu.setVisible(true);
        PassMenu.setVisible(false);
    }

    public void OnPassbtn() {
        ProfileMenu.setVisible(false);
        PassMenu.setVisible(true);
    }

    public void OnLogOutBtn() throws Exception {
        HelloApplication.SetRoot("LoginPage");
        DataHolder.setImage(null);
        DataHolder.setAdmin(null);
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

    public void OnNameBtn() {
        if (namefield.getText().isEmpty() || namefield.getText().equals("")) {
            showErrorMessage(AlertText, "Your LastName field is empty");
        } else if (isTextExceedingLength(namefield, 50)) {
            showErrorMessage(AlertText, "Last Name field is too long");
        } else {
            AdminController.modifnom(DataHolder.getAdmin().getID(), namefield.getText());
            DataHolder.getAdmin().setName(namefield.getText());
            NameLabel.setText(namefield.getText());
            ProfileName.setText(DataHolder.getAdmin().getName() + " " + DataHolder.getAdmin().getPrename());
        }
    }

    public void OnPrenameBtn() {
        if (prenameField.getText().isEmpty()) {
            showErrorMessage(AlertText, "Your FirstName field is empty");
        } else if (isTextExceedingLength(prenameField, 50)) {
            showErrorMessage(AlertText, "First Name field is too long");
        } else {
            AdminController.modifprenom(DataHolder.getAdmin().getID(), prenameField.getText());
            DataHolder.getAdmin().setPrename(prenameField.getText());
            PrenameLable.setText(prenameField.getText());
            ProfileName.setText(DataHolder.getAdmin().getName() + " " + DataHolder.getAdmin().getPrename());
        }
    }

    public void OnMailBtn() {
        if (mailfield.getText().isEmpty()) {
            showErrorMessage(AlertText, "Your Mail field is empty");
        } else if (isTextExceedingLength(mailfield, 50)) {
            showErrorMessage(AlertText, "Mail field is too long");
        } else {
            AdminController.modifmail(DataHolder.getAdmin().getID(), mailfield.getText());
            DataHolder.getAdmin().setMail(mailfield.getText());
            MailLabel.setText(mailfield.getText());
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


    public void OnConfirm() {
        if (OldPass.getText().isEmpty()) {
            showMessage(passAlert, "Old Password Required!");
        } else if (newPass.getText().isEmpty()) {
            showMessage(passAlert, "Your new Password is empty!");
        } else if (PassConf.getText().isEmpty()) {
            showMessage(passAlert, "Please Confirm your Password!");
        } else if (isTextExceedingLength(OldPass, 50) || isTextExceedingLength(newPass, 50) || isTextExceedingLength(PassConf, 50)) {
            showMessage(passAlert, "Password is too long");
        } else if (!newPass.getText().equals(PassConf.getText())) {
            showMessage(passAlert, "Your confirm password is wrong!");
        } else if (newPass.getText().equals(OldPass.getText())) {
            showMessage(passAlert, "New Password already in use!");
        } else if (!OldPass.getText().equals(DataHolder.getAdmin().getPassword())) {
            showMessage(passAlert, "Wrong Password!");
        } else {
            AdminController.modifpass(DataHolder.getAdmin().getID(), newPass.getText());
            showMessage(passAlert, "Your password was changed Successfully!");
            PassConf.setText("");
            OldPass.setText("");
            newPass.setText("");
        }
    }


    public void OnClickSeries() throws Exception {

        Serie selectedSeries = SeriesTable.getSelectionModel().getSelectedItem();
        if (selectedSeries == null) {
            showMessage(AlertText, "No Series was selected");
        } else {
            DataHolderSeries.setSelectedSeries(SerieController.GetSerieByID(selectedSeries.getId()).get(0));
            HelloApplication.SetRoot("AdminSeriesView");
        }

    }

    ObservableList<String> yearList = FXCollections.observableArrayList
            ("All", "2010", "2011", "2012", "2013", "2014", "2015", "2016",
                    "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024");

    public void OnloadProfile() {

        ProfileName.setText(DataHolder.getAdmin().getName() + " " + DataHolder.getAdmin().getPrename());
        NameLabel.setText(DataHolder.getAdmin().getName());
        PrenameLable.setText(DataHolder.getAdmin().getPrename());
        MailLabel.setText(DataHolder.getAdmin().getMail());

    }

    public static ObservableList<Serie> Series;
    public static ObservableList<Film> Films;
    public ComboBox<String> YearSelect;

    public void OnChangeOnYear(List<Serie> uniqueSeries) {
        SeriesTable.getItems().clear();
        if (YearSelect.getValue() != "All") {
            SeriesTable.getItems().addAll(SerieController.streamYear(uniqueSeries, LocalDate.of(Integer.parseInt(YearSelect.getValue()), 1, 1)));
            DataHolderSeries.setSeries(Series);
        } else {
            SeriesTable.getItems().addAll(new ArrayList<>(serieSetAll));
        }
    }

    private static Set<Serie> GetSeriesContainer() {
        List<Serie> seriesList = null;
        try {
            seriesList = SerieController.GetAllSeries();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return new HashSet<>(seriesList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            OnloadProfile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        YearSelect.getItems().addAll(yearList);
        YearSelect.setValue("All");

        MovieName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        MovieScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        MovieViews.setCellValueFactory(new PropertyValueFactory<>("VueNbr"));

        SeriesName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        numSaisons.setCellValueFactory(new PropertyValueFactory<>("SeasonNumber"));
        numEp.setCellValueFactory(new PropertyValueFactory<>("EpisodeNumber"));
        Score.setCellValueFactory(new PropertyValueFactory<>("Score"));


        if (DataHolderSeries.getSeries() == null || DataHolderSeries.getSeries().isEmpty()) {
            List<Serie> uniqueSeries = new ArrayList<>(serieSetAll);
            for (Serie serie : uniqueSeries) {
                try {
                    serie.setScore(SerieService.StreamAverageScore(serie));
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    serie.setSeasonNumber(SeasonController.StreamSpecificSeasons(serie.getId()));
                    List<Season> listSeason = SeasonController.FindSeasonSerieID(serie.getId());
                    for (Season season : listSeason) {
                        serie.setEpisodeNumber(EpisodeController.StreamSpecificEpisodes(season.getID()));

                    }
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
            Series = FXCollections.observableArrayList(uniqueSeries);
            DataHolderSeries.setSeries(Series);
        }


        if (DataHolderFilm.getFilms() == null || DataHolderFilm.getFilms().isEmpty()) {

            List<Film> filmList = FilmController.GetAllFilms();
            Set<Film> filmSet = new HashSet<>(filmList);
            List<Film> uniqueFilms = new ArrayList<>(filmSet);
            for (Film film : uniqueFilms)
                film.setVueNbr(VuesFilmController.GetFilmVues(film));
            Films = FXCollections.observableArrayList(uniqueFilms);

            DataHolderFilm.setFilms(Films);
        }

        MoviesTable.setItems(Films);
        SeriesTable.setItems(Series);
        SeriesTable.getSortOrder().add(Score);
        Score.setSortType(TableColumn.SortType.DESCENDING);

        SeriesTable.sort();

        YearSelect.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                OnChangeOnYear(new ArrayList<>(serieSetAll));
                SeriesTable.sort();
            }
        });

    }
}
