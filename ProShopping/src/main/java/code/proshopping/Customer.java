package code.proshopping;

import java.io.IOException;
import java.util.ArrayList;

public class Customer {
    private String username;
    private String gender;
    private String age;
    private String accountBalance;
    private String nameFile;
    private String totalAmountSpent;
    ArrayList<Product> shoppingHistory;

    FileProcessing fileProcessing = new FileProcessing();

    public Customer(String username) throws IOException {
        this.username = username;
        nameFile = "src/Data/account/customer/" + username + ".txt";
        age = fileProcessing.getAge(nameFile);
        gender = fileProcessing.getGender(nameFile);
        accountBalance = fileProcessing.getBalance(nameFile);

    }
}
