package Controllers.FXMLControllers;

import Controllers.SerieController;
import Entities.Serie;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SeriesPageController implements Initializable {

    public VBox ContentHolder;
    public TextField searchBar;
    public Button searchButton;

    public List<Serie> series=new ArrayList<>();

    public void onSearch() throws Exception {
        series = SerieController.GetSerieByName(searchBar.getText());
        ContentHolder.getChildren().clear();
        initialize(null,null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (series.isEmpty()) {
            System.out.println("Series is empty");
        } else {
            HBox line = new HBox();
            for (int i = 0; i < series.size(); i++) {
                ImageView imageView = null;
                try {
                    imageView = new ImageView(String.valueOf(series.get(i).getImg().toURI()));
                    imageView.setCursor(Cursor.cursor("Hand"));
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(150);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                line.getChildren().add(imageView);
                if (i == 5) {
                    ContentHolder.getChildren().add(line);
                    line = new HBox();
                }
            }

        }
    }
}
