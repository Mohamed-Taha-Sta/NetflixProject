package Controllers.FXMLControllers;

import Controllers.ScoreEpisodeController;
import Controllers.SeasonController;
import Controllers.VuesEpisodeController;
import Utils.DataHolderEpisode;
import Utils.DataHolderSeason;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminEpisodeViewController implements Initializable {

    public Label EpisodeTitle;
    public Label ScoreLabel;
    public Label DebutDateLabel;
    public Label NumVoters;
    public Label NumViews;

    public void onBack() throws Exception {
        DataHolderSeason.setSelectedSeason(SeasonController.FindSeasonID(DataHolderEpisode.getSelectedEpisode().getSeasonParentID()).get(0));
        DataHolderEpisode.setSelectedEpisode(null);
        HelloApplication.SetRoot("AdminSeasonView");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EpisodeTitle.setText(DataHolderEpisode.getSelectedEpisode().getName());

        ScoreLabel.setText(DataHolderEpisode.getSelectedEpisode().getScore() + "%");
        NumViews.setText(String.valueOf(VuesEpisodeController.GetEpisodeVues(DataHolderEpisode.getSelectedEpisode())));
        DebutDateLabel.setText(DataHolderEpisode.getSelectedEpisode().getDebutDate().toString());
        NumVoters.setText(String.valueOf(ScoreEpisodeController.GetNumberVotesEpisode(DataHolderEpisode.getSelectedEpisode())));

    }
}
