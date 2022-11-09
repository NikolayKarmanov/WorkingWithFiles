import java.io.*;

public class Basket implements Serializable {
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

    // метод для сохранения в файл в бинарном формате
    public void saveBin(File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // метод для загрузки корзины из бинарного файла
    static Basket loadFromBinFile(File file) {
        Basket basket = null;
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            basket = (Basket) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return basket;
    }
}
