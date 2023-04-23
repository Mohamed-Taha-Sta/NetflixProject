package Controllers.FXMLControllers;

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
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

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
    public TableColumn<Film,String> MovieName;
    public TableView<Serie> SeriesTable = new TableView<>();
    public TableColumn<Film,String> SeriesName;


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



    public void OnProfilebtn()  {
        ProfileMenu.setVisible(true);
        PassMenu.setVisible(false);
    }


    public void OnPassbtn()  {
        ProfileMenu.setVisible(false);
        PassMenu.setVisible(true);
    }


    public void OnClickMovie() throws Exception {

        Film selectedMovie = MoviesTable.getSelectionModel().getSelectedItem();
        if (selectedMovie == null)
        {
            MovieAlertText.setText("No movie was selected");
        }
        else
        {
            DataHolderFilm.setSelectedFilm(selectedMovie);
            HelloApplication.SetRoot("ProducerFilmView");
        }
    }


    public void OnClickSeries() throws Exception {

        Serie selectedSeries = SeriesTable.getSelectionModel().getSelectedItem();
        if (selectedSeries == null)
        {
            SeriesAlertText.setText("No Series was selected");
        }
        else
        {
            DataHolderSeries.setSelectedSeries(SerieController.GetSerieByID(selectedSeries.getId()).get(0));
            HelloApplication.SetRoot("ProducerSeriesView");
        }

    }


    public void OnLogOutBtn() throws Exception {
        HelloApplication.SetRoot("LoginPage");
        DataHolder.setUser(null);
    }

    public void OnPrenameBtn() {
        if (prenameField.getText().isEmpty()) {
            showErrorMessage(AlertText,"Your FirstName field is empty");
        } else {
            ProducerController.modifprenom(DataHolder.getProducer().getId(),prenameField.getText());
            DataHolder.getProducer().setPrenom(prenameField.getText());
            PrenameLable.setText(prenameField.getText());
            ProfileName.setText(DataHolder.getProducer().getNom() + " " + DataHolder.getProducer().getPrenom());
        }
    }


    public void OnMailBtn()  {
        if (mailfield.getText().isEmpty()) {
            showErrorMessage(AlertText, "Your Mail field is empty");
        } else {
            ProducerController.modifmail(DataHolder.getProducer().getId(),mailfield.getText());
            DataHolder.getProducer().setEmail(mailfield.getText());
            MailLabel.setText(mailfield.getText());
        }
    }


    public void OnNameBtn()  {
        if (namefield.getText().isEmpty() || namefield.getText().equals("")) {
            showErrorMessage( AlertText,"Your LastName field is empty");
        } else {
            ProducerController.modifnom(DataHolder.getProducer().getId(),namefield.getText());
            DataHolder.getProducer().setNom(namefield.getText());
            NameLabel.setText(namefield.getText());
            ProfileName.setText(DataHolder.getProducer().getNom() + " " + DataHolder.getProducer().getPrenom());
        }
    }

    public void OnConfirm()  {
        if (OldPass.getText().isEmpty()) {
            showErrorMessage(passAlert, "Old Password Required!");
        } else if (newPass.getText().isEmpty()) {
            showErrorMessage(passAlert, "Your new Password is empty!");
        } else if (PassConf.getText().isEmpty()) {
            showErrorMessage(passAlert, "Please Confirm your Password!");
        } else if (!newPass.getText().equals(PassConf.getText())) {
            showErrorMessage(passAlert, "Your confirm password is wrong!");

        } else if (newPass.getText().equals(OldPass.getText())) {
            showErrorMessage(passAlert, "New Password already in use!");
        } else if (!OldPass.getText().equals(DataHolder.getProducer().getpassword())) {
            showErrorMessage(passAlert,"Wrong Password!");
        } else {
            ProducerController.modifpassword(DataHolder.getProducer().getId(),newPass.getText());
            showErrorMessage(passAlert,"Your password was changed Successfully!");
            PassConf.setText("");
            OldPass.setText("");
            newPass.setText("");
        }
    }



    public ProducerLandingPageController() throws SQLException, IOException {
    }

    public static ObservableList<Serie> Series ;
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

        if (DataHolderSeries.getSeries()==null || DataHolderSeries.getSeries().isEmpty()) {

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

        if (DataHolderFilm.getFilms()==null || DataHolderFilm.getFilms().isEmpty()) {

            List<Film> filmList = null;
            filmList = FilmController.FindByproducer(DataHolder.getProducer());
            System.out.println(filmList);
            Films = FXCollections.observableArrayList(filmList);

            DataHolderFilm.setFilms(Films);
        }

        MoviesTable.setItems(Films);
        SeriesTable.setItems(Series);

//
//        if ((Series == null )||(Series.isEmpty())||(!Series.equals(DataHolderSeries.getSeries()))) {
//                Series = DataHolderSeries.getSeries();
//        }
//
//        if ((Films == null)||(Films.isEmpty())||(!Films.equals(DataHolderFilm.getFilms()))) {
//            Films = FXCollections.observableList(FilmController.FindByproducer(DataHolder.getProducer()));
//        }






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
