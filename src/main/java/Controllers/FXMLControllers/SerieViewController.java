package Controllers.FXMLControllers;

import Controllers.Avis_serieController;
import Controllers.ProducerController;
import Controllers.SeasonController;
import Entities.Producer;
import Entities.Season;
import Utils.DataHolder;
import Utils.DataHolderFilm;
import Utils.DataHolderSeason;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.*;

public class SerieViewController implements Initializable {
    public VBox SeasonView;
    public TextArea SerieOpinion;
    public Button EditBtn;
    public Button SaveBtn;

    public Button homeButton;
    public Button seriesButoon;
    public Button filmButton;
    public Label SerieName;
    public Label DirectLabel;
    public ImageView Thumbnail;
    public Button BackBtn;
    public Button SynopsisBtn;

    public TextArea Description;
    public Label dateLabel;
    public Label genreLabel;
    public Button DeleteBtn;

    static String path = "HomePage";
    public Button SubmitBtn;


    public static void setPath(String path) {
        SerieViewController.path = path;
    }

    List<Season> seasons;


    static Producer prod = ProducerController.getProdByID(DataHolderSeries.getSelectedSeries().getID_PROD());

    public void OnBack() throws Exception {
        DataHolderSeries.setSelectedSeries(null);
        HelloApplication.SetRoot(path);
    }

    @FXML
    public void OnFilmClick() throws Exception {
        HelloApplication.SetRoot("FilmPage");
    }

    public void OnSeriesClick() throws Exception {
        HelloApplication.SetRoot("SeriesPage");
    }

    public void OnSynopsisClick() throws Exception {
        VideoPlayerController.SetPath(DataHolderSeries.getSelectedSeries().getSynopsis().getPath());
        VideoPlayerController.setPageName("SerieView");
        HelloApplication.SetRoot("VideoPlayer");
    }

    public void InfoSetter() {
        SerieName.setText(DataHolderSeries.getSelectedSeries().getNom());
        DirectLabel.setText(prod.getNom() + " " + prod.getPrenom());
        ImageSetter(Thumbnail, DataHolderSeries.getSelectedSeries().getImg().toURI().toString(), 240, 135);
        System.out.println(DataHolderSeries.getSelectedSeries().getDescription());
        Description.setText(DataHolderSeries.getSelectedSeries().getDescription());
        dateLabel.setText(DataHolderSeries.getSelectedSeries().getAnnerdesortie().format(formatter));
        genreLabel.setText(DataHolderSeries.getSelectedSeries().getListegenre().toString());
        if (Avis_serieController.Avis_Exist(DataHolderSeries.getSelectedSeries(), DataHolder.getUser())) {
            SerieOpinion.setText(Avis_serieController.FIND_avis(DataHolderSeries.getSelectedSeries(), DataHolder.getUser()));
        }
    }

    public List<Season> RetrieveSeasons() {
        try {
            return SeasonController.FindSeasonSerieID(DataHolderSeries.getSelectedSeries().getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public void OnEdit() {
        SerieOpinion.setEditable(true);
    }

    public void OnSubmit() {
        if (SerieOpinion.isEditable()) {
            if (Avis_serieController.Avis_Exist(DataHolderSeries.getSelectedSeries(), DataHolder.getUser())) {
                Avis_serieController.modif_avis(DataHolderSeries.getSelectedSeries(), DataHolder.getUser(), SerieOpinion.getText());
                SerieOpinion.setEditable(false);
            } else {
                SerieOpinion.setEditable(false);
                Avis_serieController.add_avis(DataHolderSeries.getSelectedSeries(), DataHolder.getUser(), SerieOpinion.getText());
            }

        }

    }

    public void OnDelete() {
        SerieOpinion.setText("");
        Avis_serieController.delete_avis(DataHolderSeries.getSelectedSeries(), DataHolder.getUser());
        SerieOpinion.setEditable(false);
    }

    public void handleImageClick(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        Season selectedSeason = null;
        for (Season s : seasons) {
            if (imageView.getImage().getUrl().equals(String.valueOf(s.getThumbnail().toURI()))) {
                selectedSeason = s;
                break;
            }
        }
        if (selectedSeason != null) {
            try {
                DataHolderSeason.setSelectedSeason(SeasonController.FindSeasonID(selectedSeason.getID()).get(0));
                System.out.println(selectedSeason);
                HelloApplication.SetRoot("SeasonView");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void OnHomeClick() throws Exception {
        HelloApplication.SetRoot("HomePage");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        seasons = RetrieveSeasons();
        VBox seasonHolder = new VBox();
        HBox season2 = new HBox();
        int seasonNbr = 0;
        season2.setSpacing(10);
        season2.setAlignment(Pos.CENTER);
        for (Season s : seasons) {
            seasonHolder.setSpacing(10);
            seasonHolder.setAlignment(Pos.CENTER);
            Label seasonLabel = new Label(s.getName());
            ImageView imgView = new ImageView();
            ImageSetter(imgView, s.getThumbnail().toURI().toString(), 160, 90);
            ImageClipper(imgView);
            seasonLabel.setCursor(Cursor.cursor("hand"));
            imgView.setCursor(Cursor.cursor("hand"));
            seasonLabel.setOnMouseClicked(this::handleImageClick);
            imgView.setOnMouseClicked(this::handleImageClick);
            seasonHolder.getChildren().addAll(imgView, seasonLabel);
            season2.getChildren().add(seasonHolder);
            seasonHolder = new VBox();
            seasonNbr++;
            if (seasonNbr == 3) {
                SeasonView.getChildren().add(season2);
                seasonNbr = 0;
                season2 = new HBox();
                season2.setSpacing(10);
                season2.setAlignment(Pos.CENTER);
            }
        }

        if (seasonNbr > 0) {
            SeasonView.getChildren().add(season2);
        }
        IconSetter(homeButton, "src/main/resources/Images/HomePage/HomeButton.png", 40);
        IconSetter(seriesButoon, "src/main/resources/Images/HomePage/Series.png", 40);
        IconSetter(filmButton, "src/main/resources/Images/HomePage/Movie.png", 40);
        ImageClipper(Thumbnail);
        IconSetter(BackBtn, "src/main/resources/Images/Design/BackButton.png", 70);
        InfoSetter();

    }
}
