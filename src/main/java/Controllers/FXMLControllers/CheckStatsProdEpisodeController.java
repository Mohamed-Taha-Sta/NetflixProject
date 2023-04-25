package Controllers.FXMLControllers;

import Controllers.Avis_EpisodeController;
import Controllers.ScoreEpisodeController;
import Utils.DataHolderEpisode;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CheckStatsProdEpisodeController implements Initializable {



    public Label EpisodeTitle;
    public Label ScoreLabel;
    public Label DebutDateLabel;

    @FXML
    private ListView<String> opinionList;


    public void onBack() throws Exception {
        HelloApplication.SetRoot("ProducerEpisodeView");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        opinionList.setPlaceholder(new Label("No opinions for this episode"));

        EpisodeTitle.setText(DataHolderEpisode.getSelectedEpisode().getName());
        ScoreLabel.setText(ScoreEpisodeController.GetEpisodeScore(DataHolderEpisode.getSelectedEpisode()) + "%");
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
