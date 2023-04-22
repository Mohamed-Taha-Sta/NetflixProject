package Controllers.FXMLControllers;

import Controllers.ActorController;
import Controllers.SerieController;
import Entities.Actor;
import Entities.Serie;
import Utils.DataHolder;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddActorsController implements Initializable {

    public Text AlertText;
    public TableView<Actor> MainActorTable = new TableView<>();
    public TableView<Actor> SupportingActorTable = new TableView<>();

    @FXML
    public TableColumn<Actor, String> MainActorColumnName = new TableColumn<>();
    public TableColumn<Actor, String> MainActorColumnLastName = new TableColumn<>();
    public TableColumn<Actor, CheckBox> SelectedMainActor = new TableColumn<>();

    public TableColumn<Actor, String> SupportingActorColomnName = new TableColumn<>();
    public TableColumn<Actor, String> SupportingActorColomnLastName = new TableColumn<>();
    public TableColumn<Actor, CheckBox> SelectedSupportingActor = new TableColumn<>();


    ObservableList<Actor> MainActorsList = FXCollections.observableList(ActorController.GetAllActors(""));
    ObservableList<Actor> SuppActorsList = FXCollections.observableList(ActorController.GetAllActors(""));


    @FXML
    protected void OnBack() throws Exception {
        HelloApplication.SetRoot("AddSeries");
    }

    @FXML
    protected void OnNext() throws Exception {
        List<Long> selectedMainActors = new ArrayList<>();
        List<Long> selectedSupportingActors = new ArrayList<>();

        for (Actor actor : MainActorsList) {
            if (actor.getSelect().isSelected()) {
                selectedMainActors.add(actor.getID());
            }
        }
        for (Actor actor : SuppActorsList) {
            if (actor.getSelect().isSelected()) {
                selectedSupportingActors.add(actor.getID());
            }
        }

        if (selectedMainActors.isEmpty())
        {
            AlertText.setText("Must select at least 1 main Actor");
        }else {
            DataHolderSeries.setMainActorsList(selectedMainActors);
            DataHolderSeries.setSuppActorsList(selectedSupportingActors);
            //PickUp constructor Here
            long idSeries = SerieController.AddSerie(new Serie(DataHolderSeries.getSeriesName(), DataHolder.getProducer().getId(),
                    DataHolderSeries.getDescription(),DataHolderSeries.getDebutDate(),DataHolderSeries.getLanguage(),
                    DataHolderSeries.getCountryOfOrigin(),DataHolderSeries.getGenreList(),DataHolderSeries.getThumbnail(),
                    DataHolderSeries.getSynopsis(),DataHolderSeries.getMainActorsList(),DataHolderSeries.getSuppActorsList()));

            DataHolderSeries.setIDSerie(idSeries);
            DataHolderSeries.getSeries().add(new Serie(DataHolderSeries.getSeriesName(), DataHolder.getProducer().getId(),
                    DataHolderSeries.getDescription(),DataHolderSeries.getDebutDate(),DataHolderSeries.getLanguage(),
                    DataHolderSeries.getCountryOfOrigin(),DataHolderSeries.getGenreList(),DataHolderSeries.getThumbnail(),
                    DataHolderSeries.getSynopsis(),DataHolderSeries.getMainActorsList(),DataHolderSeries.getSuppActorsList()));

            HelloApplication.SetRoot("AddSeason");
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MainActorColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        MainActorColumnLastName.setCellValueFactory(new PropertyValueFactory<>("prename"));
        SelectedMainActor.setCellValueFactory(new PropertyValueFactory<>("select"));
        SupportingActorColomnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        SupportingActorColomnLastName.setCellValueFactory(new PropertyValueFactory<>("prename"));
        SelectedSupportingActor.setCellValueFactory(new PropertyValueFactory<>("select"));
        MainActorTable.setItems(MainActorsList);
        SupportingActorTable.setItems(SuppActorsList);

    }
}
