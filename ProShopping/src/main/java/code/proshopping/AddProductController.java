package code.proshopping;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AddProductController {
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
    private Product product = new Product();
    private FileProcessing fileProcessing = new FileProcessing();
    private File selectFile;

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
    public void okAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        if(nameTextField.getText().isEmpty() || priceTextField.getText().isEmpty() || stockTextField.getText().isEmpty() || ratingsTextField.getText().isEmpty() || sourceImageTextField.getText().isEmpty()){
            alert.setContentText("Please fill in all information!");
        }
        else {
            if(sourceImageTextField.getText().toLowerCase().endsWith(".png")){
                product.setName(nameTextField.getText());
                product.setPrice(priceTextField.getText());
                product.setStock(stockTextField.getText());
                product.setRatings(Float.valueOf(ratingsTextField.getText()));
                product.setPriorityLevel(0);
                product.setImageSrc(sourceImageTextField.getText());
                fileProcessing.addFileImage(selectFile, product);

                fileProcessing.addProduct(product);
                product.setProduct(product.getName());

                alert.setContentText("Added successfully!");
            }
            else{
                alert.setContentText("Invalid file! Please select the file .png");
            }
        }
        alert.showAndWait();
    }
}
