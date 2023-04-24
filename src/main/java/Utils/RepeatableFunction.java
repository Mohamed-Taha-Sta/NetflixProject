package Utils;

import Entities.Genre;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

import java.io.File;
import java.time.format.DateTimeFormatter;

import static javafx.collections.FXCollections.observableArrayList;

public class RepeatableFunction {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static void ImageSetter(ImageView Imageview, String path,int width, int height){
        Image image = new Image(path);
        Imageview.setImage(image);
        Imageview.setFitWidth(width);
        Imageview.setFitHeight(height);
    }

    public static ImageView ImageClipper(ImageView imageView){
        Rectangle imageClip=new Rectangle(imageView.getFitWidth(),imageView.getFitHeight());
        imageClip.setArcHeight(30);
        imageClip.setArcWidth(30);
        imageClip.setStroke(Color.BLACK);
        imageClip.setStrokeType(StrokeType.INSIDE);
        imageClip.setFill(Color.WHITE);
        imageView.setClip(imageClip);
        return imageView;

    }

    public static void IconSetter(Button btn, String path, int size){
        Image img=new Image(new File(path).toURI().toString());
        ImageView imgView=new ImageView(img);
        imgView.setFitHeight(size);
        imgView.setFitWidth(size);
        btn.setGraphic(imgView);
    }

    public static boolean isTextExceedingLength(TextInputControl textInput, int maxLength) {
        return textInput.getText().length() > maxLength;
    }

    public static ObservableList<Genre>  GetGenres(){

        return  observableArrayList(
                new Genre("Action"),
                new Genre("Adventure"),
                new Genre("Animation"),
                new Genre("Biography"),
                new Genre("Comedy"),
                new Genre("Crime"),
                new Genre("Documentary"),
                new Genre("Drama"),
                new Genre("Family"),
                new Genre("Fantasy"),
                new Genre("Film-Noir"),
                new Genre("Game-Show"),
                new Genre("History"),
                new Genre("Horror"),
                new Genre("Music"),
                new Genre("Musical"),
                new Genre("Mystery"),
                new Genre("News"),
                new Genre("Reality-TV"),
                new Genre("Romance"),
                new Genre("Sci-Fi"),
                new Genre("Sport"),
                new Genre("Talk-Show"),
                new Genre("Thriller"),
                new Genre("War"),
                new Genre("Western")
        );
    }
}
