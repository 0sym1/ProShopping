package code.proshopping;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CartController implements Initializable {
    @FXML
    private Label balanceLabel;
    @FXML
    private Label numberOfProductsLabel;
    @FXML
    private Label toatalLabel;
    @FXML
    private VBox vBoxProduct;
    ArrayList<Product> productArrayList = new ArrayList<>();

    private String username;

    public void setUsername(String username){
        this.username = username;
    }

    public CartController(String username){ this.username = username; };

    public void setData() throws IOException {
        BufferedReader readerDataCustomer = new BufferedReader(new FileReader("src/Data/account/customer/" + username + ".txt"));
        String line;
        while ((line = readerDataCustomer.readLine()) != null){
            if(line.contains("Cart")){
                break;
            }
        }

        while ((line = readerDataCustomer.readLine()) != null){
            if(!line.contains("Shopping history")){
                String[] part = line.split("/");
                String nameProduct = part[0];
                Product product = new Product();
                product.setProduct(nameProduct);

                productArrayList.add(product);
            }
            else break;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            BufferedReader readerDataCustomer = new BufferedReader(new FileReader("src/Data/account/customer/" + username + ".txt"));
            String line;
            while ((line = readerDataCustomer.readLine()) != null){
                if(line.contains("Balance")) break;
            }
            String[] part = line.split(":");
            balanceLabel.setText(part[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            setData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int row = 0;
        try{
            for(Product product : productArrayList){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ProductsCartView.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ProductsCartController productsCartController = fxmlLoader.getController();
                productsCartController.setData(product, username);

                vBoxProduct.getChildren().add(anchorPane);
                VBox.setMargin(anchorPane, new Insets(1));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
