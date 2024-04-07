package code.proshopping;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label stockLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private ImageView image;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private void clickEven(MouseEvent mouseEvent) throws IOException {
        listener.click(product);
    }
    private ClickListener listener;
    private ClickListener clickListenerDelete;
    private Product product;

    public void setData(Product product, ClickListener listener, ClickListener clickListenerDelete){
        this.product = product;
        this.listener = listener;
        this.clickListenerDelete = clickListenerDelete;
        nameLabel.setText(product.getName());
        stockLabel.setText(product.getStock());
        priceLabel.setText((product.getPrice()) + "$");
        image.setImage(new Image(getClass().getResourceAsStream(product.getImageSrc())));
    }

    public void setAdminButton(){
        deleteButton.setVisible(true);
        editButton.setVisible(true);
    }
    public void deleteAction(ActionEvent event) throws IOException {
        clickListenerDelete.click(product);
    }
    public void editAction(ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource("EditProductView.fxml"));
        Scene scene = new Scene(root.load(), 500, 500);

        EditProductController editProductController = root.getController();
        editProductController.setProduct(product);
        Stage stage = new Stage();

        Image icon = new Image(getClass().getResourceAsStream("/image/logohehe.png"));
        stage.getIcons().add(icon);
        stage.setTitle("Edit Product");
        stage.setScene(scene);
        stage.show();
    }
}
