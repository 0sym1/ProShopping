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
    private final FileProcessing fileProcessing = new FileProcessing();

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

        shopController shopController = new shopController(username);
        shopController.setUsername(username);
        root.setController(shopController);

        Scene scene = new Scene(root.load(), 1100, 750);
        stage.setScene(scene);
        stage.show();
    }

    public void buyAction(ActionEvent event) throws IOException {
        int total = Integer.parseInt(toatalLabel.getText());
        int balance = Integer.parseInt(fileProcessing.getBalance("src/Data/account/customer/" + username + ".txt"));

        balanceLabel.setText(String.valueOf(balance));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        if(total > balance){
            alert.setContentText("Account balance insufficient!");
        }
        else{
            alert.setContentText("Successful payment!");

            balance -= total;
            balanceLabel.setText(String.valueOf(balance));
            //update balance trong file dataCustomer
            fileProcessing.updateBalance("src/Data/account/customer/" + username + ".txt", "Balance", String.valueOf(balance));
            //reset total
            numberOfProductsLabel.setText("0");
            toatalLabel.setText("0");
            //update cart vs shopping history

            for(Node node : vBoxProduct.getChildren()){
                if(node instanceof AnchorPane anchorPane){
                    ProductsCartController productsCartController = (ProductsCartController) anchorPane.getUserData();
                    if(productsCartController.getCheckBox()){
                        fileProcessing.updateShoppingHistory("src/Data/account/customer/" + username + ".txt", productsCartController.getProduct());
                        fileProcessing.updateCart("src/Data/account/customer/" + username + ".txt", productsCartController.getProduct());
                        productArrayList.remove(productsCartController.getProduct());
                    }
                }
            }
            vBoxProduct.getChildren().clear();
            display();
        }
        alert.showAndWait();
    }

    public void display(){
        ClickListener clickListenerDelete = new ClickListener() {
            @Override
            public void click(Product product) throws IOException {
                productArrayList.remove(product);
                fileProcessing.updateCart("src/Data/account/customer/" + username + ".txt", product);
                vBoxProduct.getChildren().clear();
                display();
            }
        };
        ClickListener clickListenerAdd = new ClickListener() {
            @Override
            public void click(Product product) throws IOException {
                int quantity = fileProcessing.getQuantityProduct("src/Data/account/customer/" + username + ".txt", product);

                numberOfProductsLabel.setText(String.valueOf(Integer.parseInt(numberOfProductsLabel.getText()) + 1));
                toatalLabel.setText(String.valueOf(Integer.parseInt(toatalLabel.getText()) + Integer.parseInt(product.getPrice())*quantity));
            }
        };
        ClickListener clickListenerSubtract = new ClickListener() {
            @Override
            public void click(Product product) throws IOException {
                int quantity = fileProcessing.getQuantityProduct("src/Data/account/customer/" + username + ".txt", product);

                numberOfProductsLabel.setText(String.valueOf(Integer.parseInt(numberOfProductsLabel.getText()) - 1));
                toatalLabel.setText(String.valueOf(Integer.parseInt(toatalLabel.getText()) - Integer.parseInt(product.getPrice())*quantity));
            }
        };

        try{
            for(Product product : productArrayList){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ProductsCartView.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ProductsCartController productsCartController = fxmlLoader.getController();
                productsCartController.setData(product, username,clickListenerAdd, clickListenerSubtract, clickListenerDelete);

                anchorPane.setUserData(productsCartController);
                vBoxProduct.getChildren().add(anchorPane);
                VBox.setMargin(anchorPane, new Insets(1));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            balanceLabel.setText(fileProcessing.getBalance("src/Data/account/customer/" + username + ".txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            setData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        display();
    }
}
