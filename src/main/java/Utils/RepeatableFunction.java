package Utils;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.io.File;

public class RepeatableFunction {

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
}
