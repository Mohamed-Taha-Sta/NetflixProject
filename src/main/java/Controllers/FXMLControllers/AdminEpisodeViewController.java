package Controllers.FXMLControllers;

import Controllers.ScoreEpisodeController;
import Controllers.SeasonController;
import Utils.DataHolderEpisode;
import Utils.DataHolderSeason;
import com.example.netflixproject.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminEpisodeViewController implements Initializable {

    public Label EpisodeTitle;
    public Label ScoreLabel;
    public Label DebutDateLabel;
    public Label NumVoters;

    public void onBack(ActionEvent actionEvent) throws Exception {
        DataHolderSeason.setSelectedSeason(SeasonController.FindSeasonID(DataHolderEpisode.getSelectedEpisode().getSeasonParentID()).get(0));
//        DataHolderEpisode.setEpisodeOBList(SeasonController.FindEpisodeSeasonID());
        DataHolderEpisode.setSelectedEpisode(null);
        HelloApplication.SetRoot("AdminSeasonView");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EpisodeTitle.setText(DataHolderEpisode.getSelectedEpisode().getName());

        ScoreLabel.setText(String.valueOf(DataHolderEpisode.getSelectedEpisode().getScore())+"%");

        DebutDateLabel.setText(DataHolderEpisode.getSelectedEpisode().getDebutDate().toString());
        NumVoters.setText(String.valueOf(ScoreEpisodeController.GetNumberVotesEpisode(DataHolderEpisode.getSelectedEpisode())));

    }
}
