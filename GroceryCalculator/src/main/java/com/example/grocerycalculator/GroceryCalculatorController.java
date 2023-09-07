package com.example.grocerycalculator;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GroceryCalculatorController {

    private double total;
    private int weightsTotal;
    private String log = "";
    private CheckBox [] checkBoxArray;
    private TextField [] outputTextsArray;
    private ChoiceBox [] weightsArray;

    private final double [] totalsArray = new double [] {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private final String [] namesArray = new String [] {null, "User 1", "User 2", "User 3", "User 4", "User 5", "User 6", "User 7", "User 8"};

    @FXML
    private ChoiceBox boxChooser;

    @FXML
    private TextField namePicker, itemCost, itemName, totalText;

    @FXML
    private TextArea receipt, errorMsg;

    @FXML
    private CheckBox box1, box2, box3, box4, box5, box6, box7, box8;

    @FXML
    private TextField text1, text2, text3, text4, text5, text6, text7, text8;

    @FXML
    private ChoiceBox weights1, weights2, weights3, weights4, weights5, weights6, weights7, weights8;


    @FXML
    void addAmounts(){
        errorMsg.setText("");
        checkBoxes();
        if (weightsTotal == 0) {
            return;
        }
        try {
            Double.parseDouble(itemCost.getText());
        } catch (NumberFormatException ex){
            return;
        }

        String item = itemName.getText();
        if (item.isEmpty()){
            item = "Misc. Item";
        }
        log += (item + " | " + String.format("$%.2f", Double.parseDouble(itemCost.getText())) + " | " );

        double splitAmount = Double.parseDouble(itemCost.getText());
        total += splitAmount;
        splitAmount = splitAmount/weightsTotal;
        int weightCount = 0;
        for (int i = 1; i < checkBoxArray.length; i++) {
            if(checkBoxArray[i].isSelected()) {
                int weight = Integer.parseInt(weightsArray[i].getValue().toString());
                weightCount += weight;
                totalsArray[i] += splitAmount * weight;
                outputTextsArray[i].setText(namesArray[i] + " Total: " + String.format("$%.2f", totalsArray[i]));
                log += (namesArray[i] + " (" + String.format("$%.2f", splitAmount*weight) + ")");

                if (weightCount != weightsTotal) {
                    log += ", ";
                } else {
                    log += "\n";
                }
            }
        }
        totalText.setText("Total: " + String.format("$%.2f", total));
        receipt.setText(log);
    }

    @FXML
    void changeBoxName(){
        errorMsg.setText("");
        if (namePicker.getText().isEmpty()) {
            return;
        }
        int num = Integer.parseInt(boxChooser.getValue().toString());
        checkBoxArray[num].setText(namePicker.getText());
        namesArray[num] = namePicker.getText();
    }

    @FXML
    void save(){
        String fileString = "saveData.txt";
        File file = new File(fileString);
        FileWriter writer;
        try{
            file.createNewFile();
            writer = new FileWriter(file.getCanonicalPath());
            writer.write("DO NOT MODIFY FORMAT\n");
            for(int i = 1; i < checkBoxArray.length; i++){
                writer.write(namesArray[i] + "\n" + String.format("%.2f", totalsArray[i]) + "\n");
            }
            writer.write(total + "\n" + log);
            errorMsg.setText("Saved to: " + file.getCanonicalPath());
            writer.close();
        } catch (IOException e){
            errorMsg.setText("Error Creating Save");
        }

    }

    @FXML
    void load(){
        String fileName = "saveData.txt";
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            for (int i = 1; i < checkBoxArray.length; i++){
                namesArray[i] = scanner.nextLine();
                totalsArray[i] = Double.parseDouble(scanner.nextLine());
                checkBoxArray[i].setText(namesArray[i]);
                if (totalsArray[i] != 0) {
                    outputTextsArray[i].setText(namesArray[i] + " Total: " + String.format("$%.2f", totalsArray[i]));
                } else {
                    outputTextsArray[i].setText("");
                }
            }
            total = Double.parseDouble(scanner.nextLine());
            log = "";
            while (scanner.hasNextLine()) {
                log += (scanner.nextLine() + "\n");
            }
            errorMsg.setText("File Loaded");
            receipt.setText(log);
            scanner.close();
        } catch (FileNotFoundException e) {
            errorMsg.setText("File not found: " + fileName);
        }
    }

    @FXML
    void clear(){
        total = 0;
        for (int i = 1; i < checkBoxArray.length; i++){
            checkBoxArray[i].setText("User " + i);
            totalsArray[i] = 0;
            weightsArray[i].setValue(1);
            namesArray[i] = "User " + i;
            outputTextsArray[i].setText("");
        }
    }

    private void checkBoxes(){
        int temp = 0;
        for (int i = 1; i < checkBoxArray.length; i++) {
            if (checkBoxArray[i].isSelected()) {
                temp += Integer.parseInt(weightsArray[i].getValue().toString());
            }
        }
        weightsTotal = temp;
    }

    public void setDefaults(){
        checkBoxArray = new CheckBox[] {null, box1, box2, box3, box4, box5, box6, box7, box8};
        outputTextsArray = new TextField[] {null, text1, text2, text3, text4, text5, text6, text7, text8};
        weightsArray = new ChoiceBox[] {null, weights1, weights2, weights3, weights4, weights5, weights6, weights7, weights8};

        for (int i = 1; i < checkBoxArray.length; i++) {
            weightsArray[i].setValue(1);
            checkBoxArray[i].setText(namesArray[i]);
        }
        boxChooser.setValue(1);
    }
}
