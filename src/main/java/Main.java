import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int[] price = {50, 14, 80};
        String[] product = {"Молоко", "Хлеб", "Гречневая крупа"};
        Basket basket;
        File logFile = new File("log.csv"); // Файл для хранения логов
        File jsonFile = new File("basket.json");
        ClientLog log = new ClientLog();

        // Проверяем, существует ли файл basket.json
        if (jsonFile.exists()) {
            // если файл существет, то восстанавливаем из него корзину
            basket = Basket.loadFromJson(jsonFile);
        } else {
            // если файл не существует, то создаем новую корзину, передав в неё начальные параметры
            basket = new Basket(price, product);
        }

        // Проверяем существует ли файл log.csv
        if (!logFile.exists()) {
            // если лог-файл не существует, то создаем его и передаем первую строку с заголовками
            String[] head = {"productNum", "amount"};
            try (CSVWriter writer = new CSVWriter(new FileWriter(logFile))) {
                writer.writeNext(head);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            int enteredNumber = Integer.parseInt(parts[0]);
            // сюда запишем введенный номер продукта
            int productNumber = Integer.parseInt(parts[0]) - 1;
            // сюда запишем введенное количество продукта
            int productCount = Integer.parseInt(parts[1]);
            // сохраняем номер и количество продукта в корзине
            basket.addToCart(productNumber, productCount);
            // сохраняем состояние корзины в json-файл
            basket.saveJson(jsonFile);
            // сохраняем выполненное действие в лог-файл
            log.log(enteredNumber, productCount);
        }

        // сохраняем все выполненные действия в лог-файл
        log.exportAsCSV(logFile);

        // выводим корзину на экран
        basket.printCart();
    }
}
