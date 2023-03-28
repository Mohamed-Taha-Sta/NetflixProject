module com.example.netflixproject1 {
    requires javafx.controls;
    requires javafx.fxml;



    opens com.example.netflixproject1 to javafx.fxml;
    exports com.example.netflixproject1;
}