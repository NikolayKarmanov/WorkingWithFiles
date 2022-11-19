import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class Basket {
    private int[] price;
    private String[] product;
    private int[] amount;

    public Basket(int[] price, String[] product) {
        this.price = price;
        this.product = product;
        this.amount = new int[product.length];
    }

    public Basket(int[] price, String[] product, int[] amount) {
        this.price = price;
        this.product = product;
        this.amount = amount;
    }

    // метод добавления amount штук продукта номер productNum в корзину
    public void addToCart(int productNum, int amount) {
        this.amount[productNum] += amount;
    }

    // метод вывода на экран покупательской корзины
    public void printCart() {
        System.out.println("Ваша корзина:");
        int sumProducts = 0;
        for (int i = 0; i < product.length; i++) {
            if (amount[i] != 0) {
                int currentSum = amount[i] * price[i];
                sumProducts += currentSum;
                System.out.println(product[i] + " в количестве " + amount[i] + " на сумму " + currentSum);
            }
        }
        System.out.println("Итого: " + sumProducts);
    }

    // метод сохранения корзины в текстовый файл
    public void saveTxt(File textFile) {
        try (FileWriter writer = new FileWriter(textFile)) {
            for (int i = 0; i < amount.length; i++) {
                writer.write(price[i] + "@");
            }
            writer.write("\n");
            for (int i = 0; i < amount.length; i++) {
                writer.write(product[i] + "@");
            }
            writer.write("\n");
            for (int i : amount) {
                writer.write(i + "@");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveJson(File jsonFile) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(this);
        try (FileWriter file = new FileWriter(jsonFile)) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Basket loadFromJson(File jsonFile) {
        String json = null;
        try (BufferedReader br = new BufferedReader(new FileReader(jsonFile))) {
            json = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Basket basket = gson.fromJson(json, Basket.class);
        return basket;
    }
}
