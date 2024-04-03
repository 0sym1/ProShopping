package code.proshopping;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class HistoryProductsController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private ImageView image;
    private FileProcessing fileProcessing = new FileProcessing();

    public void setData(Product product, String username) throws IOException {
        nameLabel.setText(product.getName());
        priceLabel.setText(product.getPrice());
        quantityLabel.setText(String.valueOf(fileProcessing.getQuantityProduct("src/Data/account/customer/" + username + ".txt", product)));
        image.setImage(new Image(getClass().getResourceAsStream(product.getImageSrc())));
    }
}
