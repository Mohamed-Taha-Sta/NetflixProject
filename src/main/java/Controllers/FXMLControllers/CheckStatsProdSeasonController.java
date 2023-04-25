package Controllers.FXMLControllers;

import Controllers.Avis_SaisonController;
import Controllers.SeasonController;
import Utils.DataHolderSeason;
import com.example.netflixproject.HelloApplication;
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

public class CheckStatsProdSeasonController implements Initializable {


    public Label SeasonTitle;
    public Label ScoreLabel;
    public Label DebutDateLabel;

    @FXML
    private ListView<String> opinionList;


    public void onBack() throws Exception {
        HelloApplication.SetRoot("ProducerSeasonView");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        opinionList.setPlaceholder(new Label("No opinions for this season"));

        SeasonTitle.setText(DataHolderSeason.getSelectedSeason().getName());
        try {
            ScoreLabel.setText(SeasonController.StreamAverageScore(DataHolderSeason.getSelectedSeason()) + "%");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error printing rating");
            throw new RuntimeException(e);
        }
        DebutDateLabel.setText(DataHolderSeason.getSelectedSeason().getDebutDate().toString());

        List<String> opnions = Avis_SaisonController.FindAll(DataHolderSeason.getSelectedSeason());
        opinionList.getItems().addAll(opnions);

        opinionList.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                    // other stuff to do...

                } else {

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
