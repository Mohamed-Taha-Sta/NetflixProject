package Controllers.FXMLControllers;

import Controllers.ActorController;
import Controllers.FilmController;
import Controllers.SerieController;
import Entities.*;
import Utils.DataHolder;
import Utils.DataHolderFilm;
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

public class AddActorsFilmController implements Initializable {

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
        HelloApplication.SetRoot("AddFilm");
    }

    @FXML
    protected void OnNext() throws Exception {
        List<Actor> selectedMainActors = new ArrayList<>();
        List<Actor> selectedSupportingActors = new ArrayList<>();

        for (Actor actor : MainActorsList) {
            if (actor.getSelect().isSelected()) {
                selectedMainActors.add(new MainActor(actor.getID(), actor.getName(), actor.getPrename(), actor.getMail(), actor.getPassword()));
            }
        }
        for (Actor actor : SuppActorsList) {
            if (actor.getSelect().isSelected()) {
                selectedSupportingActors.add(new Supportingactor(actor.getID(), actor.getName(), actor.getPrename(), actor.getMail(), actor.getPassword()));
            }
        }

        if (selectedMainActors.isEmpty())
        {
            AlertText.setText("Must select at least 1 main Actor");
        }else {
            DataHolderFilm.setMainActorsList(selectedMainActors);
            DataHolderFilm.setSuppActorsList(selectedSupportingActors);
            ArrayList<Actor> auxList = new ArrayList<>();
            auxList.addAll(selectedMainActors);
            auxList.addAll(selectedSupportingActors);
            DataHolderFilm.setAllTheActors(auxList);
            //PickUp constructor Here
            FilmController.Add(new Film(DataHolderFilm.getName(),DataHolderFilm.getDescription(),
                    DataHolderFilm.getDebutDate(),DataHolderFilm.getLanguage(),DataHolderFilm.getCountryOfOrigin(),
                    DataHolderFilm.getGenreList(),DataHolderFilm.getThumbnail(),DataHolderFilm.getDuration(),
                    DataHolderFilm.getSynopsis(),DataHolderFilm.getVideo(), DataHolder.getProducer().getId(),DataHolderFilm.getAllTheActors()));

            //CHANGE ME
//            HelloApplication.SetRoot("PRODUCER_DASHBOARD");
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
