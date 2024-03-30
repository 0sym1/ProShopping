package code.proshopping;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Product {
    private String name;
    private String price;
    private String stock;
    private String imageSrc;
    private float ratings;
    private float priorityLevel;

    public void setProduct(String name) throws IOException {
        this.name = name;
        BufferedReader br = new BufferedReader(new FileReader("src/Data/product/" + name + ".txt"));
        price = br.readLine();
        stock = br.readLine();
        imageSrc = "/image/" + name + ".jpg";
        ratings = Float.parseFloat(br.readLine());
        priorityLevel = Float.parseFloat(br.readLine());
    }


    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getStock() {
        return stock;
    }
    public String getImageSrc(){
        return imageSrc;
    }
    public float getRatings() {
        return ratings;
    }

    public float getPriorityLevel() {
        return priorityLevel;
    }
}
