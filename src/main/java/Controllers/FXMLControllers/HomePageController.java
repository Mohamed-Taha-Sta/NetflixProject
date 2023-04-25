package Controllers.FXMLControllers;

import Controllers.FilmController;
import Controllers.SerieController;
import DAO.UserDAO;
import Entities.Content;
import Entities.Film;
import Entities.Serie;
import Utils.DataHolder;
import Utils.DataHolderFilm;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static Controllers.FXMLControllers.ProfilePageController.setNoti;
import static Utils.RepeatableFunction.*;

public class HomePageController implements Initializable {

    public static List<Content> latestStuff;
    public static List<Serie> series;

    public static List<Serie> getSeries() {
        return series;
    }

    static List<Film> films;
    static List<Content> sFByGENRE;

    static List<Content> items;

    public HBox ThumbnailViewer;
    public Button homeButton;
    public Button seriesButoon;
    public Button filmButton;
    public Label welcome;

    public ImageView ProfileBtn;

    public HBox PrefrencesViewer;
    public Button NotiBtn;

    public void handleImageClick(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        Serie selectedSerie = null;
        Film selectedFilm = null;
        for (Serie s : series) {
            if (imageView.getImage().getUrl().equals(String.valueOf(s.getImg().toURI()))) {
                selectedSerie = s;
                break;
            }
        }

        for (Film s : films) {
            if (imageView.getImage().getUrl().equals(String.valueOf(s.getImg().toURI()))) {
                selectedFilm = s;
                break;
            }
        }
        if (selectedSerie != null) {
            try {
                DataHolderSeries.setSelectedSeries(selectedSerie);
                DataHolderSeries.setSelectedSeries(SerieController.GetSerieByID(DataHolderSeries.getSelectedSeries().getId()).get(0));
                HelloApplication.SetRoot("SerieView");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (selectedFilm != null) {
            try {
                DataHolderFilm.setSelectedFilm(selectedFilm);
                DataHolderFilm.setSelectedFilm(FilmController.FindByID(DataHolderFilm.getSelectedFilm().getId()).get(0));
                FilmViewController.setPath("HomePage");
                HelloApplication.SetRoot("FilmView");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void OnNotification() throws Exception {
        setNoti(true);
        HelloApplication.SetRoot("ProfilePage");
    }


    @FXML
    public void OnProfileClick() throws Exception {
        HelloApplication.SetRoot("ProfilePage");
    }

    @FXML
    public void OnFilmClick() throws Exception {
        HelloApplication.SetRoot("FilmPage");
    }

    public void OnSeriesClick() throws Exception {
        HelloApplication.SetRoot("SeriesPage");
    }

    public void SetUserImage() {
        UserDAO.retrieve_Image((int) DataHolder.getUser().getID());
        File imageFile = DataHolder.getImage();
        if (imageFile != null) {
            Image profileImage = new Image(imageFile.toURI().toString());

            ProfileBtn.setImage(profileImage);
            ImageClipper(ProfileBtn);
        } else {
            System.out.println("No image found for user.");
        }
    }

    public SearchableComboBox<Content> searchBar;

    public void navigateToPage(Content selectedItem) throws Exception {
        if (selectedItem instanceof Serie selectedSerie) {
            DataHolderSeries.setSelectedSeries(selectedSerie);
            HelloApplication.SetRoot("SerieView");
        } else if (selectedItem instanceof Film selectedFilm) {
            DataHolderFilm.setSelectedFilm(selectedFilm);
            HelloApplication.SetRoot("FilmView");
        }
    }

    public void searchBarInit(List<Serie> series, List<Film> films) {
        List<Content> items = new ArrayList<>();
        items.addAll(series);
        items.addAll(films);
        searchBar.setConverter(new StringConverter<>() {
            @Override
            public String toString(Content item) {
                if (item == null) {
                    return "";
                } else if (item instanceof Serie) {
                    return item.getNom();
                } else if (item instanceof Film) {
                    return item.getNom();
                } else {
                    return item.toString();
                }
            }

            @Override
            public Content fromString(String string) {
                return null;
            }
        });
        searchBar.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Content item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    if (item instanceof Serie) {
                        setText(item.getNom());
                    } else if (item instanceof Film) {
                        setText(item.getNom());
                    }
                }
            }
        });
        searchBar.setItems(FXCollections.observableArrayList(items));
        searchBar.setOnAction(event -> {
            Content selectedItem = searchBar.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                try {
                    navigateToPage(selectedItem);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public List<Content> LatestInit() {
        List<Serie> latestSeries = new ArrayList<>();
        List<Film> latestFilms = new ArrayList<>();
        List<Content> items = new ArrayList<>();

        try {
            latestSeries = SerieController.getMostRecentSeries(3);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        try {
            latestFilms = FilmController.getMostRecentFilm(3);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        items.addAll(latestSeries);
        items.addAll(latestFilms);
        Collections.shuffle(items);
        return items;
    }


    public List<Content> Prefrences() {
        List<Serie> prefSeries = new ArrayList<>();
        List<Film> prefFilms = new ArrayList<>();
        List<Content> items = new ArrayList<>();
        try {
            prefSeries = SerieController.searchSeriesOR(DataHolder.getUser().getGenreList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            prefFilms = FilmController.searchFilm(DataHolder.getUser().getGenreList());
            System.out.println("filmPrefere: " + prefFilms);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        items.addAll(prefFilms);
        items.addAll(prefSeries);

        Collections.shuffle(items);
        return items;
    }


    public void LoadAllSeries() {
        if (series == null) {
            try {
                series = new ArrayList<>();
                series = SerieController.GetAllSeries();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);

            }
        }
    }

    public void LoadAllFilms() {
        if (films == null || films.isEmpty()) {
            films = new ArrayList<>();
            films = FilmController.GetAllFilms();
        } else {
            System.out.println("films already loaded");
        }
    }

    public void LoadLatestReleases() {
        Collections.shuffle(latestStuff);
        for (Content c : latestStuff) {
            ImageView imgView = new ImageView();
            ImageSetter(imgView, c.getImg().toURI().toString(), 176, 99);
            ImageClipper(imgView);
            imgView.setCursor(Cursor.cursor("hand"));
            imgView.setOnMouseClicked(this::handleImageClick);
            ThumbnailViewer.getChildren().add(imgView);
        }
    }


    public void LoadPreferredReleases() {
        if (!sFByGENRE.isEmpty()) {
            Collections.shuffle(sFByGENRE);
            int num = 0;
            for (Content c : sFByGENRE) {
                ImageView imgView = new ImageView();
                ImageSetter(imgView, c.getImg().toURI().toString(), 176, 99);
                ImageClipper(imgView);
                imgView.setCursor(Cursor.cursor("hand"));
                imgView.setOnMouseClicked(this::handleImageClick);
                PrefrencesViewer.getChildren().add(imgView);
                num++;
                if (num == 6) {
                    break;
                }
            }
        } else {
            Collections.shuffle(latestStuff);
            for (Content c : latestStuff) {
                ImageView imgView = new ImageView();
                ImageSetter(imgView, c.getImg().toURI().toString(), 176, 99);
                ImageClipper(imgView);
                imgView.setCursor(Cursor.cursor("hand"));
                imgView.setOnMouseClicked(this::handleImageClick);
                PrefrencesViewer.getChildren().add(imgView);
            }
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set user image and welcoming text
        SetUserImage();
        if (DataHolder.getUser() != null) {
            welcome.setText("Welcome Back " + DataHolder.getUser().getPrename() + "!");
        }
        //Icon Sizes
        final int IV_Size = 40;
        IconSetter(NotiBtn, "src/main/resources/Images/HomePage/Notification.png", IV_Size);
        IconSetter(homeButton, "src/main/resources/Images/HomePage/HomeButton.png", IV_Size);
        IconSetter(seriesButoon, "src/main/resources/Images/HomePage/Series.png", IV_Size);
        IconSetter(filmButton, "src/main/resources/Images/HomePage/Movie.png", IV_Size);


        //load latest Releases bar
        if (latestStuff == null) {
            latestStuff = new ArrayList<>();
            latestStuff.addAll(LatestInit());
        }
        LoadLatestReleases();

        //load Preferred Releases
        if (sFByGENRE == null) {
            sFByGENRE = new ArrayList<>();
            sFByGENRE.addAll(Prefrences());
        }
        LoadPreferredReleases();

        //SETTING UP SEARCH bar
        LoadAllSeries();
        LoadAllFilms();
        if (items == null || items.isEmpty()) {
            items = new ArrayList<>();
            items.addAll(series);
            items.addAll(films);
        }

        searchBarInit(series, films);


    }
}

