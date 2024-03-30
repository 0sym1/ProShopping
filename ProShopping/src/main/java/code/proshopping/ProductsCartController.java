package code.proshopping;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.QuadCurve;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProductsCartController {
    @FXML
    private ImageView imageView;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private CheckBox checkBox;

    public void setData(Product product, String username) throws IOException {
        imageView.setImage(new Image(getClass().getResourceAsStream(product.getImageSrc())));
        nameLabel.setText(product.getName());
        priceLabel.setText(product.getPrice());

        BufferedReader readerDataCustomer = new BufferedReader(new FileReader("src/Data/account/customer/" + username + ".txt"));
        String line;
        while((line = readerDataCustomer.readLine()) != null){
            if(line.contains(product.getName())) break;
        }
        String[] part = line.split("/");
        quantityLabel.setText(part[1].trim());
    }
}
