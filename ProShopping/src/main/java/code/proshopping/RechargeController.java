package code.proshopping;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class RechargeController {
    @FXML
    private TextField amount;
    private FileProcessing fileProcessing = new FileProcessing();
    private String username;
    public void setUsername(String username){
        this.username = username;
    }
    public void okAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setContentText("Successfully recharged!");
        alert.showAndWait();

        int balance = Integer.parseInt(fileProcessing.getBalance("src/Data/account/customer/" + username + ".txt"));
        fileProcessing.updateBalance("src/Data/account/customer/" + username + ".txt", "Balance", String.valueOf(balance + Integer.parseInt(amount.getText())));
    }

    public void cancelAction(ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource("shopView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        shopController shopController = new shopController(username);
        shopController.setUsername(username);
        root.setController(shopController);

        Scene scene = new Scene(root.load(), 1100, 750);
        stage.setScene(scene);
        stage.show();
    }
}
