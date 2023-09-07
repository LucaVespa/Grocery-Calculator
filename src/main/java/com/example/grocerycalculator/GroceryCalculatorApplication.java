package com.example.grocerycalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GroceryCalculatorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GroceryCalculatorApplication.class.getResource("grocery-calculator-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 450);
        GroceryCalculatorController controller = fxmlLoader.getController();
        controller.setDefaults();
        stage.setTitle("Receipt Calculator");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}