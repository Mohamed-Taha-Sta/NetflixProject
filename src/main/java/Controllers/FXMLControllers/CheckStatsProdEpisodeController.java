package Controllers.FXMLControllers;

import Controllers.Avis_EpisodeController;
import Controllers.Avis_SaisonController;
import Controllers.EpisodeController;
import Controllers.SeasonController;
import Utils.DataHolderEpisode;
import Utils.DataHolderSeason;
import com.example.netflixproject.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CheckStatsProdEpisodeController implements Initializable {



    public Label EpisodeTitle;
    public Label ScoreLabel;
    public Label DebutDateLabel;

    @FXML
    private ListView<String> opinionList;


    public void onBack(ActionEvent actionEvent) throws Exception {
        HelloApplication.SetRoot("ProducerEpisodeView");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        opinionList.setPlaceholder(new Label("No opinions for this episode"));

        EpisodeTitle.setText(DataHolderEpisode.getSelectedEpisode().getName());
        try {
            ScoreLabel.setText(String.valueOf(EpisodeController.getScoreEpisode(DataHolderEpisode.getSelectedEpisode()) +"%"));
        } catch (SQLException e) {
            System.out.println("Error printing rating");
            throw new RuntimeException(e);
        }
        DebutDateLabel.setText(DataHolderEpisode.getSelectedEpisode().getDebutDate().toString());

        List<String> opnions = Avis_EpisodeController.FindAll(DataHolderEpisode.getSelectedEpisode());
        opinionList.getItems().addAll(opnions);

        opinionList.setCellFactory(param -> new ListCell<String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item==null) {
                    setGraphic(null);
                    setText(null);
                    // other stuff to do...

                }else{

                    // set the width's
                    setMinWidth(param.getWidth());
                    setMaxWidth(param.getWidth());
                    setPrefWidth(param.getWidth());

                    // allow wrapping
                    setWrapText(true);

                    setText(item.toString());


                }
            }
        });
    }

}
