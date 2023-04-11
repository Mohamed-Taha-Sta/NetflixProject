module com.example.netflixproject {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;
    requires java.sql;
    requires org.apache.commons.io;
    requires java.desktop;
    requires javafx.swing;

    opens com.example.netflixproject to javafx.fxml;
    exports com.example.netflixproject;
    exports Controllers;
    opens Controllers to javafx.fxml;
    exports Entities;
    opens Entities to javafx.fxml;
    exports Utils;
    opens Utils to javafx.fxml;
    exports Controllers.FXMLControllers;
    opens Controllers.FXMLControllers to javafx.fxml;
}