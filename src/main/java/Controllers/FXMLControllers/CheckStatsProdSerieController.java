package Controllers.FXMLControllers;

import Controllers.Avis_serieController;
import Controllers.SerieController;

import Entities.Film;
import Utils.DataHolder;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CheckStatsProdSerieController implements Initializable {


    public Label SeriesTitle;
    public Label ScoreLabel;
    public Label DebutDateLabel;

    @FXML
    private ListView<String> opinionList;


    public void onBack() throws Exception {
        HelloApplication.SetRoot("ProducerSeriesView");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SeriesTitle.setText(DataHolderSeries.getSelectedSeries().getNom());
        try {
            ScoreLabel.setText(String.valueOf(SerieController.StreamAverageScore(DataHolderSeries.getSelectedSeries()))+"%");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error printing rating");
            throw new RuntimeException(e);
        }
        DebutDateLabel.setText(DataHolderSeries.getSelectedSeries().getAnnerdesortie().toString());

        List<String> opnions = Avis_serieController.FindAvisAllSerie(DataHolderSeries.getSelectedSeries());
        opinionList.getItems().addAll(opnions);

    }


}
