module com.example.netflixproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;


    opens com.example.netflixproject to javafx.fxml;
    exports com.example.netflixproject;
    exports Controllers;
    opens Controllers to javafx.fxml;
}