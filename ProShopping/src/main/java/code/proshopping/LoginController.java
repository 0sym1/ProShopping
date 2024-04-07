package code.proshopping;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;

public class LoginController {
    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordField;
    Stage stage;

    public void loginAction(ActionEvent event) throws IOException {
        boolean checkLogin = false;
        File fileAccCheck = new File("src/Data/account/customer/" + usernameText.getText() + ".txt");
        if (fileAccCheck.exists()){
            try{
                BufferedReader bw = new BufferedReader(new FileReader("src/Data/account/customer/" + usernameText.getText() + ".txt"));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                if(bw.readLine().equals(passwordField.getText())){
                    alert.setContentText("Successful login!");
                    checkLogin = true;
                }
                else{
                    alert.setContentText("Incorrect password or username!");
                }
                alert.showAndWait();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setContentText("Incorrect password or username!");
            alert.showAndWait();
        }

        if(checkLogin){
            FXMLLoader root = new FXMLLoader(this.getClass().getResource("shopView.fxml"));
            stage = new Stage();

            shopController shopController = new shopController(usernameText.getText());
            shopController.setUsername(usernameText.getText());
            root.setController(shopController);

            Scene scene = new Scene(root.load(), 1100, 750);
            Image icon = new Image(getClass().getResourceAsStream("/image/logohehe.png"));
            stage.getIcons().add(icon);
            stage.setTitle("ProShopping");
            stage.setScene(scene);
            stage.show();
        }
    }
    public void signupAction(ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource("createAccountView.fxml"));
        Scene scene = new Scene(root.load(), 500, 600);
        stage = new Stage();
        Image icon = new Image(getClass().getResourceAsStream("/image/logohehe.png"));
        stage.getIcons().add(icon);
        stage.setTitle("Create account");
        stage.setScene(scene);
        stage.show();
    }
}
