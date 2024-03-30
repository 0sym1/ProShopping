module code.proshopping {
    requires javafx.controls;
    requires javafx.fxml;


    opens code.proshopping to javafx.fxml;
    exports code.proshopping;
}