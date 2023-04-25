package Controllers.FXMLControllers;

import Controllers.Avis_serieController;
import Controllers.SeasonController;
import Controllers.SerieController;
import Entities.Season;
import Utils.DataHolderSeason;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ActorSeriesViewController implements Initializable {


    public ListView opinionList;
    public Label SeriesTitle;
    public Label ScoreLabel;
    public Label DebutDateLabel;
    public TableView<Season> SeasonTable = new TableView<>();
    public TableColumn<Season, String> SeasonName;

    public void onBack() throws Exception {
        DataHolderSeries.setSelectedSeries(null);
        DataHolderSeason.setSeasonObservableList(null);
        DataHolderSeason.setSelectedSeason(null);
        HelloApplication.SetRoot("ActorLandingPage");
    }


    public void OnClickSeason() throws Exception {

        Season selectedSeasons = SeasonTable.getSelectionModel().getSelectedItem();
        if (selectedSeasons != null) {
            DataHolderSeason.setSelectedSeason(SeasonController.FindSeasonID(selectedSeasons.getID()).get(0));
            HelloApplication.SetRoot("ActorSeasonView");
        }

    }


    public static ObservableList<Season> seasonOBList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SeasonName.setCellValueFactory(new PropertyValueFactory<>("name"));


        if (DataHolderSeason.getSeasonObservableList() == null || DataHolderSeason.getSeasonObservableList().isEmpty()) {

            List<Season> seasonList = null;
            try {
                seasonList = SeasonController.FindSeasonSerieID(DataHolderSeries.getSelectedSeries().getId());
                seasonOBList = FXCollections.observableArrayList(seasonList);
                DataHolderSeason.setSeasonObservableList(seasonOBList);
            } catch (Exception e) {
                System.out.println("passing");
            }

        }

        SeasonTable.setItems(seasonOBList);


        opinionList.setPlaceholder(new Label("No opinions for this series"));

        SeriesTitle.setText(DataHolderSeries.getSelectedSeries().getNom());

        try {
            ScoreLabel.setText(String.valueOf(SerieController.StreamAverageScore(DataHolderSeries.getSelectedSeries())) + "%");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error printing rating");
            throw new RuntimeException(e);
        }

        DebutDateLabel.setText(DataHolderSeries.getSelectedSeries().getAnnerdesortie().toString());

        List<String> opnions = Avis_serieController.FindAvisAllSerie(DataHolderSeries.getSelectedSeries());

        opinionList.getItems().addAll(opnions);

        opinionList.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);

                } else {

                    // set the width's
                    setMinWidth(getListView().getWidth());
                    setMaxWidth(getListView().getWidth());
                    setPrefWidth(getListView().getWidth());

                    // allow wrapping
                    setWrapText(true);

                    setText(item.toString());


                }
            }
        });

    }
}
