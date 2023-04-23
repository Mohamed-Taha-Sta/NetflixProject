package Controllers.FXMLControllers;

import Controllers.SeasonController;
import javafx.event.EventHandler;
import Controllers.AdminController;
import Controllers.FilmController;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

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
    public TableColumn<Film,String> MovieName;
    public TableColumn<Film,String> MovieScore;
    public Label NameLabel;
    public TableView<Serie> SeriesTable = new TableView<>();
    public TableColumn<Film,String> SeriesName;
    public TableColumn<Film,String> numSaisons;
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

    private static Set<Serie> serieSetAll = GetSeriesContainer();



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
        DataHolder.setAdmin(null);
    }

    public void OnPrenameBtn(ActionEvent actionEvent) {
        if (prenameField.getText().isEmpty()) {
            showMessage(AlertText,"Your FirstName field is empty");
        } else {
            AdminController.modifprenom(DataHolder.getAdmin().getID(),prenameField.getText());
            DataHolder.getAdmin().setPrename(prenameField.getText());
            PrenameLable.setText(prenameField.getText());
            ProfileName.setText(DataHolder.getAdmin().getName() + " " + DataHolder.getAdmin().getPrename());
        }
    }

    public void OnNameBtn(ActionEvent actionEvent) {

        if (namefield.getText().isEmpty() || namefield.getText().equals("")) {
            showMessage( AlertText,"Your LastName field is empty");
        } else {
            AdminController.modifnom(DataHolder.getAdmin().getID(),namefield.getText());
            DataHolder.getAdmin().setName(namefield.getText());
            NameLabel.setText(namefield.getText());
            ProfileName.setText(DataHolder.getAdmin().getName() + " " + DataHolder.getAdmin().getPrename());
        }

    }

    public void OnMailBtn(ActionEvent actionEvent) {
        if (mailfield.getText().isEmpty()) {
            showMessage(AlertText, "Your Mail field is empty");
        } else {
            AdminController.modifmail(DataHolder.getAdmin().getID(),mailfield.getText());
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
        } else if (!OldPass.getText().equals(DataHolder.getAdmin().getPassword())) {
            showMessage(passAlert,"Wrong Password!");
        } else {
            AdminController.modifpass(DataHolder.getAdmin().getID(),newPass.getText());
            showMessage(passAlert,"Your password was changed Successfully!");
            PassConf.setText("");
            OldPass.setText("");
            newPass.setText("");
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
            HelloApplication.SetRoot("AdminFilmView");
        }
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
            HelloApplication.SetRoot("AdminSeriesView");
        }

    }

    ObservableList<String> yearList = FXCollections.observableArrayList
            ("All", "2010", "2011", "2012", "2013", "2014", "2015", "2016",
            "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024");

    public void OnloadProfile() throws Exception {

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
        }else {
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

        SeriesName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        numSaisons.setCellValueFactory(new PropertyValueFactory<>("SeasonNumber"));


        if (DataHolderSeries.getSeries()==null || DataHolderSeries.getSeries().isEmpty()) {
            List<Serie> uniqueSeries = new ArrayList<>(serieSetAll);
            for (Serie serie:uniqueSeries)
            {
                try {
                    serie.setSeasonNumber(SeasonController.StreamSpecificSeasons(serie.getId()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            Series = FXCollections.observableArrayList(uniqueSeries);
            DataHolderSeries.setSeries(Series);
        }



        if(DataHolderFilm.getFilms()==null || DataHolderFilm.getFilms().isEmpty()) {

            List<Film> filmList = FilmController.GetAllFilms();
            Set<Film> filmSet = new HashSet<>(filmList);
            List<Film> uniqueFilms = new ArrayList<>(filmSet);
            Films = FXCollections.observableArrayList(uniqueFilms);

            DataHolderFilm.setFilms(Films);
        }

        MoviesTable.setItems(Films);
        SeriesTable.setItems(Series);

        YearSelect.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    OnChangeOnYear(new ArrayList<>(serieSetAll));
                }
            }
        });



    }
}
