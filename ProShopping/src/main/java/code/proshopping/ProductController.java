package code.proshopping;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
    private void clickEven(MouseEvent mouseEvent){
        listener.click(product);
    }
    private ClickListener listener;
    private Product product;

    public void setData(Product product, ClickListener listener){
        this.product = product;
        this.listener = listener;
        nameLabel.setText(product.getName());
        stockLabel.setText(product.getStock());
        priceLabel.setText((product.getPrice()));
        image.setImage(new Image(getClass().getResourceAsStream(product.getImageSrc())));
    }
}
