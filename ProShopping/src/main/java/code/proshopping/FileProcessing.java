package code.proshopping;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class FileProcessing {
    public int getQuantityProduct(String nameFile, Product product) throws IOException {
        BufferedReader readerData = new BufferedReader(new FileReader(nameFile));
        String line;
        while((line = readerData.readLine()) != null){
            if(line.contains(product.getName())) break;
        }
        readerData.close();
        String[] part = line.split("/");

        return Integer.parseInt(part[1]);
    }

    public String getBalance(String nameFile) throws IOException {
        BufferedReader readerData = new BufferedReader(new FileReader(nameFile));
        String line;
        while((line = readerData.readLine()) != null){
            if(line.contains("Balance")) break;
        }
        String[] part = line.split(":");

        return part[1].trim();
    }

    public String getAge(String nameFile) throws IOException {
        BufferedReader readerData = new BufferedReader(new FileReader(nameFile));
        String line;
        while((line = readerData.readLine()) != null){
            if(line.contains("Information")) break;
        }
        String[] part1 = line.split(":");
        String[] part2 = part1[1].split("/");

        return part2[0];
    }

    public String getGender(String nameFile) throws IOException {
        BufferedReader readerData = new BufferedReader(new FileReader(nameFile));
        String line;
        while((line = readerData.readLine()) != null){
            if(line.contains("Information")) break;
        }
        String[] part1 = line.split(":");
        String[] part2 = part1[1].split("/");

        return part2[1];
    }

    public void updateShoppingHistory(String nameFile, Product product) throws IOException {
        ArrayList<String> data = new ArrayList<>();
        String line;
        BufferedReader readerData = new BufferedReader(new FileReader(nameFile));
        while ((line = readerData.readLine()) != null){
            data.add(line);
        }
        readerData.close();

        int indexLine = 0;
        String quantity = null;
        for(String tmpLine : data){
            indexLine++;
            if(tmpLine.contains("Shopping history")) break;
            if(tmpLine.contains(product.getName())){
                String[] part = tmpLine.split("/");
                quantity = part[1];
            }
        }
        data.add(indexLine, product.getName() + "/" + quantity);

        BufferedWriter writerDataCustomer = new BufferedWriter(new FileWriter(nameFile));
        for(String newLine : data){
            writerDataCustomer.write(newLine);
            writerDataCustomer.newLine();
        }
        writerDataCustomer.close();
    }

    public void updateCart(String nameFile, Product product) throws IOException {
        ArrayList<String> data = new ArrayList<>();
        String line;
        BufferedReader readerData = new BufferedReader(new FileReader(nameFile));
        while ((line = readerData.readLine()) != null){
            data.add(line);
        }
        readerData.close();

        int indexLine = 0;
        for(String tmpLine : data){
            if(tmpLine.contains(product.getName())) break;
            indexLine++;
        }
        data.remove(indexLine);

        BufferedWriter writerDataCustomer = new BufferedWriter(new FileWriter(nameFile));
        for(String newLine : data){
            writerDataCustomer.write(newLine);
            writerDataCustomer.newLine();
        }
        writerDataCustomer.close();
    }
    public void updateBalance(String nameFile, String point, String newData) throws IOException {
        ArrayList<String> data = new ArrayList<>();
        String line;
        BufferedReader readerData = new BufferedReader(new FileReader(nameFile));
        while ((line = readerData.readLine()) != null){
            data.add(line);
        }
        readerData.close();

        int indexLine = 0;
        for(String tmpLine : data){
            if(!tmpLine.contains(point)) indexLine++;
            else break;
        }
        int balance = 0;
        String[] part = data.get(indexLine).split(":");
        balance = Integer.parseInt(part[1].trim());
        data.set(indexLine, point + ":" + newData);

        BufferedWriter writerDataCustomer = new BufferedWriter(new FileWriter(nameFile));
        for(String newLine : data){
            writerDataCustomer.write(newLine);
            writerDataCustomer.newLine();
        }
        writerDataCustomer.close();
    }

    public void deleteProduct(Product product) throws IOException {
        String nameFolder = "src/Data/product";
        File fileDelete = new File(nameFolder, product.getName() + ".txt");
        fileDelete.delete();
    }
    public void addProduct(Product product){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/Data/product/" + product.getName() + ".txt"));
            bw.write(product.getPrice());
            bw.newLine();
            bw.write(product.getStock());
            bw.newLine();
            bw.write(String.valueOf(product.getRatings()));
            bw.newLine();
            bw.write(String.valueOf(product.getPriorityLevel()));
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void editProduct(Product product){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/Data/product/" + product.getName() + ".txt"));
            bw.write(product.getPrice());
            bw.newLine();
            bw.write(product.getStock());
            bw.newLine();
            bw.write(String.valueOf(product.getRatings()));
            bw.newLine();
            bw.write(String.valueOf(product.getPriorityLevel()));
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void addFileImage(File selectFile, Product product){
        try {
            // Đường dẫn đích bạn muốn chuyển file đến
            File destinationFile = new File("src/main/resources/image");

            // Copy file đến đường dẫn đích và đổi tên
            Files.copy(selectFile.toPath(), destinationFile.toPath().resolve(product.getName() +".png"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
