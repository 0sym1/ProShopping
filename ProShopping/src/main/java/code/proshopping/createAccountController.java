package code.proshopping;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class createAccountController implements Initializable {
    @FXML
    private TextField usernameText;
    @FXML
    private TextField passwordText;
    @FXML
    private TextField age;
    @FXML
    private TextField confirmPasswordText;
    @FXML
    public ChoiceBox<String> genderBox;
    ObservableList<String> listGender = FXCollections.observableArrayList("Male", "Female", "Other");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderBox.setItems(listGender);
    }

    public void cancelAction(ActionEvent event){

    }
    public void okAction(ActionEvent event){

        if(!passwordText.getText().equals(confirmPasswordText.getText())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setContentText("Password confirmation doesn't match!");
            alert.showAndWait();
            return;
        }

        File fileAccCheck = new File("src/Data/account/customer/" + usernameText.getText() + ".txt");
        if (fileAccCheck.exists()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setContentText("Account already exists!");
            alert.showAndWait();
        }
        else {
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter("src/Data/account/customer/" + usernameText.getText() + ".txt"));
                bw.write(passwordText.getText());
                bw.newLine();
                bw.write("Information: " + age.getText() + "/" + genderBox.getValue());
                bw.newLine();
                bw.write("Balance: 0");
                bw.newLine();
                bw.write("Cart: ");
                bw.newLine();
                bw.write("Shopping history:");
                bw.newLine();
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setContentText("Account created successfully!");
            alert.showAndWait();
        }
    }
}
