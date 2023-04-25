package Controllers.FXMLControllers;

import Controllers.ScoreSeriesController;
import Controllers.SeasonController;
import Controllers.SerieController;
import Entities.Season;
import Utils.DataHolderSeason;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AdminSeriesViewController implements Initializable {

    public Label SeriesTitle;
    public Label ScoreLabel;
    public Label DebutDateLabel;
    public TableView<Season> SeasonTable = new TableView<>();
    public TableColumn<Season,String> SeasonName;
    public Label NumberOfVoters;

    public void onBack() throws Exception {
        DataHolderSeries.setSelectedSeries(null);
        DataHolderSeason.setSeasonObservableList(null);
        DataHolderSeason.setSelectedSeason(null);
        HelloApplication.SetRoot("AdminLandingPage");
    }



    public void OnClickSeason() throws Exception {

        Season selectedSeasons = SeasonTable.getSelectionModel().getSelectedItem();
        if (selectedSeasons != null)
        {
            DataHolderSeason.setSelectedSeason(SeasonController.FindSeasonID(selectedSeasons.getID()).get(0));
            HelloApplication.SetRoot("AdminSeasonView");
        }

    }

    public static ObservableList<Season> seasonOBList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SeasonName.setCellValueFactory(new PropertyValueFactory<>("name"));

        if (DataHolderSeason.getSeasonObservableList()==null || DataHolderSeason.getSeasonObservableList().isEmpty()) {

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


        SeriesTitle.setText(DataHolderSeries.getSelectedSeries().getNom());
//        NumberOfVoters.setText();

        try {
            ScoreLabel.setText(SerieController.StreamAverageScore(DataHolderSeries.getSelectedSeries()) +"%");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error printing rating");
            throw new RuntimeException(e);
        }

        DebutDateLabel.setText(DataHolderSeries.getSelectedSeries().getAnnerdesortie().toString());

        List<Season> seasonList = null;
        try {
            seasonList = SeasonController.FindSeasonSerieID(DataHolderSeries.getSelectedSeries().getId());
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }


        if (seasonList!=null)
            NumberOfVoters.setText(String.valueOf(ScoreSeriesController.GetNumberOfVotersSeason(seasonList)));



    }




















}
