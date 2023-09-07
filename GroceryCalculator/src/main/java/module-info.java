module com.example.grocerycalculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.grocerycalculator to javafx.fxml;
    exports com.example.grocerycalculator;
}