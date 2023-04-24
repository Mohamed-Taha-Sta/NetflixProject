package Controllers.FXMLControllers;

import Controllers.ActorController;
import Controllers.EpisodeController;
import Controllers.SerieController;
import Controllers.UserController;
import DAO.UserDAO;
import Entities.Actor;
import Entities.Genre;
import Entities.Serie;
import Utils.DataHolder;
import com.example.netflixproject.HelloApplication;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static Utils.RepeatableFunction.*;

public class ProfilePageController implements Initializable {


    //LeftMenu Vars
    public ImageView Profile_Image;
    public Text ProfileName;
    public Button ProfileBtn;
    public Button NotfificationBtn;
    public Button returnBtn;


    //Profile Menu Anchor
    public TextField prenameField;
    public Button PrenameBtn;
    public TextField namefield;
    public Button NameBtn;
    public TextField mailfield;
    public Button MailBtn;
    public Button LogoutBtn;
    public Button PassBtn;
    public AnchorPane ProfileMenu;
    public Label NameLabel;
    public Label PrenameLable;
    public Label MailLabel;
    public DatePicker Birthdaypicker;
    public Button BirthdayBtn;
    public Label AlertText;
    public TableView<Actor> ActorTable=new TableView<>();
    public TableColumn<Actor,String> ActorNameColumn;
    public TableColumn<Actor,String> ActorPrenameColumn;
    public TableColumn<Actor, CheckBox> SelectedActor;
    public Button UpdateActors;
    public Button UpdateGenres;


    public TableView<Genre> GenreTable;
    public TableColumn<Genre,String> GenreColumn;
    public TableColumn<Genre,String> SelectedGenre;


    //Notification Menu Anchor
    public AnchorPane NotificationMenu;
    public VBox NotificationPane;

    List<Serie> series=new ArrayList<>();

    //Password Menu
    public AnchorPane PassMenu;
    public TextField OldPass;
    public TextField newPass;
    public TextField PassConf;
    public Button confirmBtn;
    public Label passAlert;
    public Label GenreAlertText;
    public Label ActorsAlertText;

    public static boolean noti=false;

    public static void setNoti(boolean noti) {
        ProfilePageController.noti = noti;
    }

    

    ObservableList<Actor> actors;
    ObservableList<Genre> genres = GetGenres();

    public void LoadSelectedActors(){
        actors= FXCollections.observableList(ActorController.GetAllActors(""));
        ArrayList<Long> userActors=DataHolder.getUser().getActorsList();
        for(Long actorId : userActors){
            for(Actor actor : actors){
                if(actor.getID() == actorId){
                    actor.getSelect().setSelected(true);
                    break;
                }
            }
        }
    }

    public void LoadSelectedGenres(){
        ArrayList<String > userGenre= DataHolder.getUser().getGenreList();
        for(String genre:userGenre){
            for(Genre genreName:genres){
                if(genre.equals(genreName.getNom())){
                    genreName.getSelect().setSelected(true);
                    break;
                }
            }
        }
    }


    public void OnUpdateActors(){
        ArrayList<Long> selectedActors = new ArrayList<>();
        for (Actor actor : actors ) {
            if (actor.getSelect().isSelected()) {
                selectedActors.add(actor.getID());
            }
        }
        String actorListString = String.join(",", selectedActors.stream().map(Object::toString).toArray(String[]::new));
        String userActors= String.join(",", DataHolder.getUser().getActorsList().stream().map(Object::toString).toArray(String[]::new));
        if(actorListString.equals(userActors)){
            showErrorMessage(ActorsAlertText,"You didnt change anything!");
        }
        else if(actorListString.isEmpty()){
            showErrorMessage(ActorsAlertText,"At least one Actor should be selected!");
        }
        else {
            DataHolder.getUser().setActorsList(selectedActors);
            System.out.println(UserController.Actors(actorListString));
            showErrorMessage(ActorsAlertText,"Actors list Updated Successfully");
        }

    }

    public void OnUpdateGenres(){
        ArrayList<String> selectedGenres = new ArrayList<>();
        for (Genre genre: genres){
            if(genre.getSelect().isSelected()){
                selectedGenres.add(genre.getNom());
            }
        }
        String userGenres =String.join(",", DataHolder.getUser().getGenreList().stream().map(Object::toString).toArray(String[]::new));
        System.out.println("User:"+userGenres);
        String genreListString = String.join(",", selectedGenres.stream().map(Object::toString).toArray(String[]::new));
        System.out.println("Selec"+genreListString);
        if(genreListString.equals(userGenres)){
            showErrorMessage(GenreAlertText,"You didnt change anything!");
        }
        else if(genreListString.isEmpty()){
            showErrorMessage(GenreAlertText,"At least one Genre should be selected!");
        }
        else{
            DataHolder.getUser().setGenreList(selectedGenres);
            System.out.println(UserController.Genres(genreListString));
            showErrorMessage(GenreAlertText,"Genres list Updated Successfully");
        }

    }
    @FXML
    public void OnProfileImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Image");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.jpeg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());

            int width = (int) image.getWidth();
            int height = (int) image.getHeight();
            if (width <= 512 && height <= 512) {
                Profile_Image.setImage(image);
                UserDAO.adding_Image(selectedFile);

            } else {
                // If the selected image does not have the required dimensions, display an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Image Size");
                alert.setContentText("Please select an image with dimensions of 512x512 pixels.");
                alert.showAndWait();
            }
        }
    }

    public void OnReturn() throws Exception {
        setNoti(false);
        HelloApplication.SetRoot("HomePage");
    }

    public void OnloadProfile() {

        System.out.println(DataHolder.getUser().getBirthday());
        ProfileName.setText(DataHolder.getUser().getName() + " " + DataHolder.getUser().getPrename());
        NameLabel.setText(DataHolder.getUser().getName());
        PrenameLable.setText(DataHolder.getUser().getPrename());
        MailLabel.setText(DataHolder.getUser().getMail());
        try {
            Birthdaypicker.setValue(DataHolder.getUser().getBirthday());
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
        TableSetter();


    }


    public void TableSetter() {
        LoadSelectedGenres();
        LoadSelectedActors();
        ActorNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ActorPrenameColumn.setCellValueFactory(new PropertyValueFactory<>("Prename"));
        SelectedActor.setCellValueFactory(new PropertyValueFactory<>("select"));
        GenreColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        SelectedGenre.setCellValueFactory(new PropertyValueFactory<>("select"));
        ActorTable.setItems(actors);
        GenreTable.setItems(genres);
    }

    public void OnProfilebtn()  {
        ProfileMenu.setVisible(true);
        NotificationMenu.setVisible(false);
        PassMenu.setVisible(false);
    }

    public void OnNotibtn()  {
        ProfileMenu.setVisible(false);
        NotificationMenu.setVisible(true);
        PassMenu.setVisible(false);
    }

    public void OnPassbtn()  {
        ProfileMenu.setVisible(false);
        NotificationMenu.setVisible(false);
        PassMenu.setVisible(true);
    }

    public void OnLogOutBtn() throws Exception {
        HelloApplication.SetRoot("LoginPage");
        DataHolder.setUser(null);
    }

    public void OnPrenameBtn() {
        if (prenameField.getText().isEmpty()) {
            showErrorMessage(AlertText,"Your FirstName field is empty");
        } else {
            System.out.println(UserController.FirstName(prenameField.getText()));
            DataHolder.getUser().setPrename(prenameField.getText());
            PrenameLable.setText(prenameField.getText());
            ProfileName.setText(DataHolder.getUser().getName() + " " + DataHolder.getUser().getPrename());
            prenameField.setText("");
        }
    }

    public void OnMailBtn()  {
        if (mailfield.getText().isEmpty()) {
            showErrorMessage(AlertText, "Your Mail field is empty");
        } else {
            System.out.println(UserController.Mail(mailfield.getText()));
            DataHolder.getUser().setMail(mailfield.getText());
            MailLabel.setText(mailfield.getText());
            mailfield.setText("");
        }
    }

    public void OnNameBtn()  {
        if (namefield.getText().isEmpty() || namefield.getText().equals("")) {
            showErrorMessage( AlertText,"Your LastName field is empty");
        } else {
            System.out.println(UserController.LastName(namefield.getText()));
            DataHolder.getUser().setName(namefield.getText());
            NameLabel.setText(namefield.getText());
            ProfileName.setText(DataHolder.getUser().getName() + " " + DataHolder.getUser().getPrename());
            namefield.setText("");
        }
    }

    public void OnBirthdayBtn(){
        if (Birthdaypicker.getValue().equals(DataHolder.getUser().getBirthday())) {
            showErrorMessage(AlertText, "You didnt change your birthday");
        } else {
            System.out.println(UserController.Birthday(Birthdaypicker.getValue()));
            DataHolder.getUser().setBirthday(Birthdaypicker.getValue());
        }
    }


    public void OnConfirm()  {
        if (OldPass.getText().isEmpty()) {
            showErrorMessage(passAlert, "Old Password Required!");
        } else if (newPass.getText().isEmpty()) {
            showErrorMessage(passAlert, "Your new Password is empty!");
        } else if (PassConf.getText().isEmpty()) {
            showErrorMessage(passAlert, "Please Confirm your Password!");
        } else if (!newPass.getText().equals(PassConf.getText())) {
            showErrorMessage(passAlert, "Your confirm password is wrong!");

        } else if (newPass.getText().equals(OldPass.getText())) {
            showErrorMessage(passAlert, "New Password already in use!");
        } else if (!OldPass.getText().equals(DataHolder.getUser().getPassword())) {
            showErrorMessage(passAlert,"Wrong Password!");
        } else {
            System.out.println(UserController.Password(newPass.getText()));
            showErrorMessage(passAlert,"Your password was changed Successfully!");
            PassConf.setText("");
            OldPass.setText("");
            newPass.setText("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            UserDAO.retrieve_Image((int) DataHolder.getUser().getID());
            File imageFile = DataHolder.getImage();
            System.out.println("image file: " + imageFile);
            if (imageFile != null) {
                Image profileImage = new Image(imageFile.toURI().toString());
                System.out.println("Profile image in profilepage: " + profileImage);
                Profile_Image.setImage(profileImage);
                ImageClipper(Profile_Image);
            } else {
                System.out.println("No image found for user.");
            }
        } catch (Exception e) {
            System.out.println("image not found");
        }
        IconSetter(returnBtn, "src/main/resources/Images/HomePage/BackArrow.png", 25);
        try {
            OnloadProfile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TableSetter();
        if(noti){
            OnNotibtn();
        }
        try {
           series= SerieController.GetReleasedEpisode(EpisodeController.GetAllEpisodes(),DataHolder.getUser()) ;
            System.out.println("parsed all episodes");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        if(!series.isEmpty()){
            for(Serie s:series){
                Label label=new Label();
                label.setText("A new Episode of  " + s.getNom()+ " has been released");
                NotificationPane.getChildren().add(label);
            }
        }
        else{
            Label label=new Label();
            label.setText("No new Notifications");
            NotificationPane.getChildren().add(label);
        }


    }

    private void showErrorMessage(Label label, String message) {
        label.setText(message);
        label.setOpacity(1);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            label.setOpacity(0);
        }));
        timeline.play();
    }
}
