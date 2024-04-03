package code.proshopping;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InformationController implements Initializable {
    @FXML
    private Label usernameLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private Label totalAmountSpentLabel;
    private String username;
    @FXML
    private VBox vBoxProducts;
    private ArrayList<Product> productArrayList = new ArrayList<>();

    public InformationController(String username){
        this.username = username;
    }

    public void setData() throws IOException {
        BufferedReader readerDataCustomer = new BufferedReader(new FileReader("src/Data/account/customer/" + username + ".txt"));
        String line;
        while ((line = readerDataCustomer.readLine()) != null){
            if(line.contains("Shopping history")){
                break;
            }
        }

        while ((line = readerDataCustomer.readLine()) != null){
            String[] part = line.split("/");
            String nameProduct = part[0];
            Product product = new Product();
            product.setProduct(nameProduct);

            productArrayList.add(product);
        }
        readerDataCustomer.close();
    }

    public void backAction(ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource("shopView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load(), 1100, 750);

        shopController shopController = root.getController();
        shopController.setUsername(username);
        shopController.setBalanceLabel();

        stage.setScene(scene);
        stage.show();
    }

    public void display(){
        try{
            for(Product product : productArrayList){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("HistoryProductsView.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                HistoryProductsController historyProductsController = fxmlLoader.getController();
                historyProductsController.setData(product, username);

                anchorPane.setUserData(historyProductsController);
                vBoxProducts.getChildren().add(anchorPane);
                VBox.setMargin(anchorPane, new Insets(1));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        usernameLabel.setText(username);
        ageLabel.setText("");
        genderLabel.setText("");
        balanceLabel.setText("");
        totalAmountSpentLabel.setText("");

        display();
    }
}
