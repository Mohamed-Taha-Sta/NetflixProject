package Controllers.FXMLControllers;

import Controllers.SerieController;

import Entities.Film;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CheckStatsProdSerieController implements Initializable {


    public Label SeriesTitle;
    public Label ScoreLabel;
    public Label DebutDateLabel;

    public TableView<String> TableViewAvis = new TableView<>();
    public TableColumn<String,String> Avis;


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

    }


}
