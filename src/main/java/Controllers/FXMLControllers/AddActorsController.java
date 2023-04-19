package Controllers.FXMLControllers;

import Entities.Actor;
import Entities.Text;
import Utils.DataHolderSeries;
import com.example.netflixproject.HelloApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class AddActorsController implements Initializable {

    public Text AlertText;
    public TableView<Actor> MainActorTable = new TableView<>();
    public TableView<Actor> SupportingActorTable = new TableView<>();

    @FXML
    public TableColumn<Actor, String> MainActorColumn = new TableColumn<>();
    public TableColumn<Actor, CheckBox> SelectedMainActor = new TableColumn<>();

    public TableColumn<Actor, String> SupportingActorColomn = new TableColumn<>();
    public TableColumn<Actor, CheckBox> SelectedSupportingActor = new TableColumn<>();

    //CHANGE ASAP
    ObservableList<Actor> CHANGEMainActorsList = observableArrayList(new Actor(11, "Tom", "Hanks", "dd", "sssa"), new Actor(12, "Salah", "DooDOO", "dd", "sssa"), new Actor(13, "Emm", "", "dd", "sssa"));
    ObservableList<Actor> CHANGESuppActorsList = observableArrayList(new Actor(11, "Jasser", "Hamdi", "dd", "sssa"), new Actor(12, "Salouh", "Mejri", "dd", "sssa"), new Actor(13, "Salem", "El Behi", "dd", "sssa"));


    @FXML
    protected void OnBack() throws Exception {
        HelloApplication.SetRoot("AddSeries");
    }

    @FXML
    protected void OnNext() throws Exception {
        List<Actor> selectedMainActors = new ArrayList<>();
        List<Actor> selectedSupportingActors = new ArrayList<>();

        for (Actor actor : CHANGEMainActorsList) {
            if (actor.getSelect().isSelected()) {
                selectedMainActors.add(actor);
            }
        }
        for (Actor actor : CHANGESuppActorsList) {
            if (actor.getSelect().isSelected()) {
                selectedSupportingActors.add(actor);
            }
        }

        if (selectedMainActors.isEmpty())
        {
            AlertText.setTexte("Must select at least 1 main Actor");
        }else
        {
            DataHolderSeries.setMainActorsList(selectedMainActors);
            DataHolderSeries.setSuppActorsList(selectedSupportingActors);

            HelloApplication.SetRoot("AddSeries");
        }

    }










    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MainActorColumn.setCellValueFactory(new PropertyValueFactory<>("Actor Name"));
        SelectedMainActor.setCellValueFactory(new PropertyValueFactory<>("Select"));
        SupportingActorColomn.setCellValueFactory(new PropertyValueFactory<>("Actor Name"));
        SelectedSupportingActor.setCellValueFactory(new PropertyValueFactory<>("Select"));
        MainActorTable.setItems(CHANGEMainActorsList);
        SupportingActorTable.setItems(CHANGESuppActorsList);

    }
}
