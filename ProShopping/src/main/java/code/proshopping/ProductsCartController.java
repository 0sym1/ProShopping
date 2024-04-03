package code.proshopping;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.QuadCurve;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

    @FXML
    public void selectEven(MouseEvent mouseEvent) throws IOException {
        if(!checkBox.isSelected()) listenerAdd.click(product);
        else listenerSubtract.click(product);
    }
    private ClickListener listenerAdd;
    private ClickListener listenerSubtract;
    private ClickListener listenerDelete;
    private Product product;

    public boolean getCheckBox(){
        return checkBox.isSelected();
    }
    public Product getProduct(){
        return product;
    }

    public void setData(Product product, String username ,ClickListener listenerAdd, ClickListener listenerSubtract, ClickListener listenerDelete) throws IOException {
        imageView.setImage(new Image(getClass().getResourceAsStream(product.getImageSrc())));
        nameLabel.setText(product.getName());
        priceLabel.setText(product.getPrice());
        this.listenerAdd = listenerAdd;
        this.listenerSubtract = listenerSubtract;
        this.listenerDelete = listenerDelete;
        this.product = product;

        BufferedReader readerDataCustomer = new BufferedReader(new FileReader("src/Data/account/customer/" + username + ".txt"));
        String line;
        while((line = readerDataCustomer.readLine()) != null){
            if(line.contains(product.getName())) break;
        }
        String[] part = line.split("/");
        quantityLabel.setText(part[1].trim());

    }

    public void deleteAction(ActionEvent event) throws IOException {
        listenerDelete.click(product);
    }
}
