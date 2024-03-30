package code.proshopping;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class shopController implements Initializable {
    @FXML
    private AnchorPane shopView;
    @FXML
    private TextField textFieldSearch;
    @FXML
    private GridPane gridPaneList;
    @FXML
    private ChoiceBox<String> choiceBoxSort;
    @FXML
    private Label nameProductLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label stockLabel;
    @FXML
    private Label ratingsLabel;
    @FXML
    private TextField quantityTextField;
    @FXML
    private ImageView productImageView;
    @FXML
    private Label balanceLabel;
    @FXML
    private Button rechargeButton;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
    ObservableList<String> listSort = FXCollections.observableArrayList("High to low", "Low to high");

    private ArrayList<Product> productArrayList = new ArrayList<>();


    public void setBalanceLabel(){
        try {
            BufferedReader readerDataCustomer = new BufferedReader(new FileReader("src/Data/account/customer/" + username + ".txt"));
            readerDataCustomer.readLine();
            readerDataCustomer.readLine();
            String line = readerDataCustomer.readLine();
            String[] part = line.split(":");
            String balance = part[1].trim();
            balanceLabel.setText(balance + "$");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setDataProducts() throws IOException {
        File folderData = new File("src/Data/product");
        File[] filesProduct = folderData.listFiles();

        for(File filesData : filesProduct){
            String nameProduct = filesData.getName();
            nameProduct = nameProduct.substring(0, nameProduct.length() - 4);
            Product product = new Product();
            product.setProduct(nameProduct);

            productArrayList.add(product);
        }
    }

    public void rechargeAction(ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource("RechargeView.fxml"));
        Scene scene = new Scene(root.load(), 1100, 750);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        RechargeController rechargeController = root.getController();
        rechargeController.setUsername(username);
        currentStage.setScene(scene);
        currentStage.show();
    }

    public void cartAction(ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource("CartView.fxml"));

        CartController cartController = new CartController(username);
        root.setController(cartController);
        Scene scene = new Scene(root.load(), 1100, 750);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

//        CartController cartController = root.getController();
//        cartController.setUsername(username);
//        cartController.setData();

        currentStage.setScene(scene);
        currentStage.show();

    }
    public void informationAction(ActionEvent event){

    }
    public void refreshAction(ActionEvent event){
        gridPaneList.getChildren().clear();

        setInformationBar(productArrayList.get(0));

        ClickListener clickListener = product -> setInformationBar(product);

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < productArrayList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ProductView.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ProductController productController = fxmlLoader.getController();
                productController.setData(productArrayList.get(i), clickListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                gridPaneList.add(anchorPane, column++, row); //(child,column,row)

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void searchAction(ActionEvent event) throws IOException {
        gridPaneList.getChildren().clear();
        String nameProduct = textFieldSearch.getText();

        ClickListener clickListener = product -> setInformationBar(product);
        int column = 0;
        int row = 1;
        try {
            for (Product product : productArrayList) {
                if (product.getName().equals(nameProduct)) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("ProductView.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ProductController productController = fxmlLoader.getController();
                    productController.setData(product, clickListener);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    gridPaneList.add(anchorPane, column++, row); //(child,column,row)

                    GridPane.setMargin(anchorPane, new Insets(10));
                }
            }
            if(gridPaneList.getChildren().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText("No results found!");
                alert.showAndWait();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addToCartAction(ActionEvent event) throws IOException {
        int quantity = Integer.parseInt(quantityTextField.getText());
        Product product = new Product();
        product.setProduct(nameProductLabel.getText());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        if(quantity > Integer.parseInt(product.getStock())){
            alert.setContentText("Quantity exceeds available stock!");
        }
        else{
            alert.setContentText("Product added to cart successfully!");

            ArrayList<String> listStringFile = new ArrayList<>();
            String line;
            //them vao gio hang, load vao trong file data customer
            BufferedReader readerCustomerFile = new BufferedReader(new FileReader("src/Data/account/customer/" + username + ".txt"));
            while ((line = readerCustomerFile.readLine()) != null){
                listStringFile.add(line);
            }
            readerCustomerFile.close();
            listStringFile.add(4, product.getName() + "/" + quantityTextField.getText());

            BufferedWriter writerCustomerFile = new BufferedWriter(new FileWriter("src/Data/account/customer/" + username + ".txt"));
            for(String newLine : listStringFile){
                writerCustomerFile.write(newLine);
                writerCustomerFile.newLine();
            }
            writerCustomerFile.close();

            listStringFile.clear();
            //load lai stock trong file
            BufferedReader readerDataProduct = new BufferedReader(new FileReader("src/Data/product/" + product.getName() + ".txt"));
            while((line = readerDataProduct.readLine()) != null){
                listStringFile.add(line);
            }
            readerDataProduct.close();
            listStringFile.set(1, String.valueOf(Integer.parseInt(product.getStock()) - quantity));

            BufferedWriter writerDataProduct = new BufferedWriter(new FileWriter("src/Data/product/" + product.getName() + ".txt"));
            for(String newLine : listStringFile){
                writerDataProduct.write(newLine);
                writerDataProduct.newLine();
            }
            writerDataProduct.close();
        }
        alert.showAndWait();
    }
    public void setInformationBar(Product product){
        nameProductLabel.setText(product.getName());
        priceLabel.setText(product.getPrice());
        stockLabel.setText(product.getStock());
        ratingsLabel.setText(String.valueOf(product.getRatings()));
        productImageView.setImage(new Image(this.getClass().getResourceAsStream(product.getImageSrc())));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBoxSort.setItems(listSort);

        try {
            setDataProducts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setInformationBar(productArrayList.get(0));

        ClickListener clickListener = product -> setInformationBar(product);

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < productArrayList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ProductView.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ProductController productController = fxmlLoader.getController();
                productController.setData(productArrayList.get(i), clickListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                gridPaneList.add(anchorPane, column++, row); //(child,column,row)

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
