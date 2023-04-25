package Controllers.FXMLControllers;

import Controllers.Avis_EpisodeController;
import Controllers.SeasonController;
import Utils.DataHolderEpisode;
import Utils.DataHolderSeason;
import com.example.netflixproject.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ActorEpisodeViewController implements Initializable {

    public ListView opinionList;
    public Label EpisodeTitle;
    public Label ScoreLabel;
    public Label DebutDateLabel;

    public void onBack() throws Exception {
        DataHolderSeason.setSelectedSeason(SeasonController.FindSeasonID(DataHolderEpisode.getSelectedEpisode().getSeasonParentID()).get(0));
//      DataHolderEpisode.setEpisodeOBList(SeasonController.FindEpisodeSeasonID());
        DataHolderEpisode.setSelectedEpisode(null);
        HelloApplication.SetRoot("ActorSeasonView");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        opinionList.setPlaceholder(new Label("No opinions for this episode"));

        EpisodeTitle.setText(DataHolderEpisode.getSelectedEpisode().getName());

        ScoreLabel.setText(DataHolderEpisode.getSelectedEpisode().getScore() + "%");

        DebutDateLabel.setText(DataHolderEpisode.getSelectedEpisode().getDebutDate().toString());

        List<String> opnions = Avis_EpisodeController.FindAll(DataHolderEpisode.getSelectedEpisode());

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
