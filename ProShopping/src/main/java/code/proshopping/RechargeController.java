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

    private String username;
    public void setUsername(String username){
        this.username = username;
    }
    public void okAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setContentText("Successfully recharged!");
        alert.showAndWait();

        ArrayList<String> dataCustomer = new ArrayList<>();
        String line;
        BufferedReader readerDataCustomer = new BufferedReader(new FileReader("src/Data/account/customer/" + username + ".txt"));
        while ((line = readerDataCustomer.readLine()) != null){
            dataCustomer.add(line);
        }
        readerDataCustomer.close();

        int indexLine = 0;
        for(String tmpLine : dataCustomer){
            if(!tmpLine.contains("Balance:")) indexLine++;
            else break;
        }
        int balance = 0;
        String[] part = dataCustomer.get(indexLine).split(":");
        balance = Integer.parseInt(part[1].trim());
        dataCustomer.set(indexLine, "Balance:" + String.valueOf(balance + Integer.parseInt(amount.getText())));

        BufferedWriter writerDataCustomer = new BufferedWriter(new FileWriter("src/Data/account/customer/" + username + ".txt"));
        for(String newLine : dataCustomer){
            writerDataCustomer.write(newLine);
            writerDataCustomer.newLine();
        }
        writerDataCustomer.close();
    }

    public void cancelAction(ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource("shopView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load(), 1100, 750);

        shopController shopController = root.getController();
        shopController.setUsername(username);
        shopController.setBalanceLabel();

        stage.setTitle("ProShopping");
        stage.setScene(scene);
        stage.show();
    }
}
