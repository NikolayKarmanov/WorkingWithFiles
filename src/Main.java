import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] price = {50, 14, 80};
        String[] product = {"Молоко", "Хлеб", "Гречневая крупа"};
        Basket basket;
        File file = new File("basket.bin");

        // проверяем существует ли файл basket.txt
        if (file.exists()) {
            // если существует, то создаем корзину на основе данных из файла
            basket = Basket.loadFromBinFile(file);
        } else {
            // если не существует, то создаем новую пустую корзину
            basket = new Basket(price, product);
        }

        // Вывод списка продуктов с ценами на экран
        for (int i = 0; i < product.length; i++) {
            System.out.println((i + 1) + ". " + product[i] + " " + price[i] + " руб/шт");
        }

        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();

            if (input.equals("end")) {
                break; // выход из цикла при вводе слова "end"
            }

            // делим введенное данные на два числа (номер продукта и количество)
            String[] parts = input.split(" ");
            // сюда запишем введенный номер продукта
            int productNumber = Integer.parseInt(parts[0]) - 1;
            // сюда запишем введенное количество продукта
            int productCount = Integer.parseInt(parts[1]);
            // в массиве amount указали, что общее количество введенного продукта изменилось
            basket.addToCart(productNumber, productCount);
            basket.saveBin(file);
        }

        basket.printCart();

    }
}
