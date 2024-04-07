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
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;


public class shopController implements Initializable {
    @FXML
    private AnchorPane shopView;
    @FXML
    private TextField textFieldSearch;
    @FXML
    private GridPane gridPaneList;
    @FXML
    private ComboBox<String> comboBoxSort;
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
    private Button addProductButton;
    @FXML
    private Button rechargeButton;
    private String username;
    private FileProcessing fileProcessing = new FileProcessing();
    private ArrayList<Product> productArrayList = new ArrayList<>();
    ObservableList<String> listSort = FXCollections.observableArrayList("High to low", "Low to high");

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public shopController(String username){
        this.username = username;
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

    public void choiceBoxSortAction(ActionEvent event){
        gridPaneList.getChildren().clear();
        ArrayList<Product> arrayListTmp = new ArrayList<>();
        arrayListTmp.addAll(productArrayList);
        if(comboBoxSort.getValue().equals("Low to high")){
            Collections.sort(arrayListTmp, new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return Integer.parseInt(o1.getPrice()) - Integer.parseInt(o2.getPrice());
                }
            });
        }
        else if(comboBoxSort.getValue().equals("High to low")){
            Collections.sort(arrayListTmp, new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return Integer.parseInt(o2.getPrice()) - Integer.parseInt(o1.getPrice());
                }
            });
        }
        dispay(arrayListTmp);
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

        currentStage.setScene(scene);
        currentStage.show();
    }
    public void informationAction(ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource("InformationView.fxml"));

        InformationController informationController = new InformationController(username);
        root.setController(informationController);
        Scene scene = new Scene(root.load(), 1100, 750);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        currentStage.setScene(scene);
        currentStage.show();
    }
    public void refreshAction(ActionEvent event) throws IOException {
        productArrayList.clear();
        setDataProducts();
        gridPaneList.getChildren().clear();
        dispay(productArrayList);
    }
    public void addProductButtonAction(ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource("AddProductView.fxml"));
        Scene scene = new Scene(root.load(), 500, 500);

        Stage stage = new Stage();
        Image icon = new Image(getClass().getResourceAsStream("/image/logohehe.png"));
        stage.getIcons().add(icon);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }
    public void searchAction(ActionEvent event) throws IOException {
        gridPaneList.getChildren().clear();
        String nameProduct = textFieldSearch.getText();

        ClickListener clickListener = product -> setInformationBar(product);
        ClickListener clickListenerDelete = new ClickListener() {
            @Override
            public void click(Product product) throws IOException {
                productArrayList.remove(product);
                fileProcessing.deleteProduct(product);
                gridPaneList.getChildren().clear();
                dispay(productArrayList);
            }
        };

        int column = 0;
        int row = 1;
        try {
            for (Product product : productArrayList) {
                if (product.getName().equals(nameProduct)) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("ProductView.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ProductController productController = fxmlLoader.getController();
                    productController.setData(product, clickListener, clickListenerDelete);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    gridPaneList.add(anchorPane, column++, row); //(child,column,row)

                    GridPane.setMargin(anchorPane, new Insets(12));
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
        if(quantityTextField.getText() != null && !Objects.equals(quantityTextField.getText(), "")){
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
    }
    public void setInformationBar(Product product){
        nameProductLabel.setText(product.getName());
        priceLabel.setText(product.getPrice() + "$");
        stockLabel.setText(product.getStock());
        ratingsLabel.setText(String.valueOf(product.getRatings()) + "/5");
        productImageView.setImage(new Image(String.valueOf(this.getClass().getResource(product.getImageSrc()))));
    }

    public void dispay(ArrayList<Product> arrayList){
        ClickListener clickListener = product -> setInformationBar(product);
        ClickListener clickListenerDelete = new ClickListener() {
            @Override
            public void click(Product product) throws IOException {
                productArrayList.remove(product);
                fileProcessing.deleteProduct(product);
                gridPaneList.getChildren().clear();
                dispay(productArrayList);
            }
        };

        int column = 0;
        int row = 1;
        try {
            for (Product product : arrayList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ProductView.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ProductController productController = fxmlLoader.getController();
                productController.setData(product, clickListener, clickListenerDelete);
                if (username.equals("Admin")) {
                    productController.setAdminButton();
                }

                if (column == 3) {
                    column = 0;
                    row++;
                }

                gridPaneList.add(anchorPane, column++, row); //(child,column,row)

                GridPane.setMargin(anchorPane, new Insets(12));
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxSort.setItems(listSort);

        try {
            setDataProducts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setInformationBar(productArrayList.get(0));

        try {
            balanceLabel.setText(fileProcessing.getBalance("src/Data/account/customer/" + username + ".txt") + "$");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(username.equals("Admin")) addProductButton.setVisible(true);

        dispay(productArrayList);
    }
}
