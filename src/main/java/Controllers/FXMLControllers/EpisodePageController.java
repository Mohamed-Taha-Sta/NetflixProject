package Controllers.FXMLControllers;

import Controllers.EpisodeController;
import Controllers.SeasonController;
import Entities.Episode;
import Entities.Resume;
import Entities.Season;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class EpisodePageController implements Initializable {

    public Text SerieXXXX;
    public Text SeasonXXEpisodeYY;
    public Text Title;
    public Text Descirption;
    public ImageView Thumbnail;

    //For Decoration purposes:
    public AnchorPane DecoAnchor1;
    public AnchorPane DecoAnchor2;
    public AnchorPane DecoAnchor3;
    public AnchorPane DecoAnchor4;
    public Button WatchNowButton;
    public Text RatingValue;
    public Text DateSortieValue;
    public Text ProducerValue;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Resume resume = new Entities.Text();
        ((Entities.Text)resume).setTexte("This is an Episode");
        List<Season> seasonList;


        List<Episode> episodeList = null;
        try {
            episodeList = EpisodeController.FindEpisodeID(83);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Episode episode2 = episodeList.get(0);

        try {
            seasonList = SeasonController.FindSeasonID(episode2.getSeasonParentID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SerieXXXX.setText("Serie "+seasonList.get(0).getSERIE_ID());
        SeasonXXEpisodeYY.setText("Season "+episode2.getSeasonParentID()+" Episode "+episode2.getNumber());

        if(episode2.getResume() instanceof Entities.Text)
            Descirption.setText(((Entities.Text) episode2.getResume()).getTexte());
        else
            Descirption.setVisible(false);

//        File fileExported = new File("file:src/main/java/Test/ImgEp"+episode2.getID()+".jpeg");
//        Image ImageExported = new Image(fileExported.toURI().toString());

//        String ImagePath = "src/main/java/Test/ImgEp"+episode2.getID()+".jpeg";

//        Image imageInter = new Image(ImagePath);


        System.out.println(episode2.getImage());

        try {
            Thumbnail.setImage(new Image(String.valueOf(episode2.getImage().toURL())));
        } catch (Exception e) {
            System.out.println("Error setting image: " + e.getMessage());
        }
//        System.out.println("Image dimensions: " + Thumbnail.getImage().getWidth() + "x" + Thumbnail.getImage().getHeight());

//        try {
//            Thumbnail = new ImageView(String.valueOf(episode2.getImage().toURL()));
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }


    }
}
