package code.proshopping;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Locale;

public class EditProductController {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField stockTextField;
    @FXML
    private TextField ratingsTextField;
    @FXML
    private TextField sourceImageTextField;
    private Product product;
    private FileProcessing fileProcessing = new FileProcessing();
    private File selectFile;

    public void setProduct(Product product){
        this.product = product;
    }

    public void selectAction(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select file");
        chooser.setInitialDirectory(new File("C:\\"));
        selectFile = chooser.showOpenDialog(stage);
        if(selectFile != null){
            sourceImageTextField.setText(selectFile.toString());
        }
    }
    public void okAction(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        if(sourceImageTextField.getText().toLowerCase().endsWith(".jpg") || sourceImageTextField.getText().isEmpty()){
            if(!nameTextField.getText().isEmpty()) product.setName(nameTextField.getText());
            if(!priceTextField.getText().isEmpty()) product.setPrice(priceTextField.getText());
            if(!stockTextField.getText().isEmpty()) product.setStock(stockTextField.getText());
            if(!ratingsTextField.getText().isEmpty()) product.setRatings(Float.valueOf(ratingsTextField.getText()));
            if(!sourceImageTextField.getText().isEmpty()){
                product.setImageSrc(sourceImageTextField.getText());
                fileProcessing.addFileImage(selectFile, product);
            }

            fileProcessing.editProduct(product);
            alert.setContentText("Edited successfully!");
        }
        else{
            alert.setContentText("Invalid file! Please select the file .jpg");
        }
        alert.showAndWait();
    }
}
