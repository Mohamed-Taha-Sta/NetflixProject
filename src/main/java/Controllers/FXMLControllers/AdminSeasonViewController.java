package Controllers.FXMLControllers;

import Controllers.EpisodeController;
import Controllers.ScoreEpisodeController;
import Controllers.ScoreSeasonController;
import Controllers.SeasonController;
import Entities.Episode;
import Utils.DataHolderEpisode;
import Utils.DataHolderSeason;
import com.example.netflixproject.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class AdminSeasonViewController implements Initializable {

    public Label SeasonTitle;
    public Label DebutDateLabel;
    public Label ScoreLabel;
    public TableView<Episode> EpisodesTable = new TableView<>();
    public TableColumn<Episode,String> EpisodeName;
    public Label NumVoters;

    public void onBack(ActionEvent actionEvent) throws Exception {
        DataHolderEpisode.setEpisodeOBList(null);
        //        DataHolderEpisode.setSelectedEpisode(null); //Try this
        HelloApplication.SetRoot("AdminSeriesView");
    }

    public void OnClickEpisode() throws Exception {

        Episode selectedEpisodes = EpisodesTable.getSelectionModel().getSelectedItem();
        if (selectedEpisodes == null)
        {
            System.out.println("Episode selected is null");
        } else
        {
            System.out.println("Selected Episode");
            DataHolderEpisode.setSelectedEpisode(EpisodeController.FindEpisodeID(selectedEpisodes.getID()).get(0));
            HelloApplication.SetRoot("AdminEpisodeView");
        }

    }

    public static ObservableList<Episode> episodeOBList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        EpisodeName.setCellValueFactory(new PropertyValueFactory<>("name")); //Try name

        if (DataHolderEpisode.getEpisodeOBList()==null || DataHolderEpisode.getEpisodeOBList().isEmpty()) {
            List<Episode> episodeList = null;
            try {
                episodeList = EpisodeController.FindEpisodeSeasonID(DataHolderSeason.getSelectedSeason().getID());
                episodeOBList = FXCollections.observableArrayList(episodeList);
                DataHolderEpisode.setEpisodeOBList(episodeOBList);
            } catch (Exception e) {
//                e.printStackTrace();
                System.out.println("No Episodes in seasons Passing");
                EpisodesTable.setItems(null);
            }
        }
        EpisodesTable.setItems(episodeOBList);
        SeasonTitle.setText(DataHolderSeason.getSelectedSeason().getName());

        try {
            ScoreLabel.setText(String.valueOf(SeasonController.StreamAverageScore(DataHolderSeason.getSelectedSeason()))+"%");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error printing rating");
            throw new RuntimeException(e);
        }

        DebutDateLabel.setText(DataHolderSeason.getSelectedSeason().getDebutDate().toString());

        try {
            NumVoters.setText(String.valueOf(ScoreSeasonController.GetNumberOfVotersSeason(DataHolderSeason.getSelectedSeason())));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }


    }
}
