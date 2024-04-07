package code.proshopping;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Start extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 600);
        Image icon = new Image(getClass().getResourceAsStream("/image/logohehe.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
//sua lai stock luc add vao cart