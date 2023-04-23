package Controllers.FXMLControllers;

import Controllers.Avis_SaisonController;
import Controllers.EpisodeController;
import Controllers.SeasonController;
import Entities.Episode;
import Entities.Season;
import Utils.DataHolderEpisode;
import Utils.DataHolderSeason;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ActorSeasonViewController implements Initializable {

    public ListView opinionList;
    public Label SeasonTitle;
    public Label DebutDateLabel;
    public Label ScoreLabel;
    public TableView<Episode> EpisodesTable = new TableView<>();
    public TableColumn<Episode,String> EpisodeName;


    public void onBack(ActionEvent actionEvent) throws Exception {
        DataHolderEpisode.setEpisodeOBList(null);
        DataHolderEpisode.setSelectedEpisode(null);
        HelloApplication.SetRoot("ActorSeriesView");
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
            HelloApplication.SetRoot("ActorEpisodeView");
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
                EpisodesTable.setItems(episodeOBList);
            } catch (Exception e) {
//                e.printStackTrace();
                System.out.println("No Episodes in seasons Passing");
                EpisodesTable.setItems(null);
            }

        }

        opinionList.setPlaceholder(new Label("No opinions for this season"));

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

        List<String> opnions = Avis_SaisonController.FindAll(DataHolderSeason.getSelectedSeason());

        opinionList.getItems().addAll(opnions);

        opinionList.setCellFactory(param -> new ListCell<String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item==null) {
                    setGraphic(null);
                    setText(null);

                }else{

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
