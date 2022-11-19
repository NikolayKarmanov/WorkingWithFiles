import com.opencsv.CSVWriter;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        Scanner scanner = new Scanner(System.in);

        int[] price = {50, 14, 80};
        String[] product = {"Молоко", "Хлеб", "Гречневая крупа"};
        Basket basket;
        File file;
        ClientLog log = new ClientLog();

        // Проверяем в настройках, нужно ли восстанавливать корзину из файла
        Parameters load = new Parameters("load");
        if (load.getEnabled()) {
            file = new File(load.getFileName());
            // восстанавливаем файл из того файла, расширение которого указано в настройках
            basket = (load.getFormat().equals("json")) ? Basket.loadFromJson(file) : Basket.loadFromTxtFile(file);
        } else {
            // если восстанавливать не нужно, то создаем пустую корзину
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
            int enteredNumber = Integer.parseInt(parts[0]);
            // сюда запишем введенный номер продукта
            int productNumber = Integer.parseInt(parts[0]) - 1;
            // сюда запишем введенное количество продукта
            int productCount = Integer.parseInt(parts[1]);
            // сохраняем номер и количество продукта в корзине
            basket.addToCart(productNumber, productCount);
            // сохраняем состояние корзины, если это указано в настройках
            Parameters save = new Parameters("save");
            if (save.getEnabled()) {
                // создаем файл с именем, указанным в настройках
                File saveFile = new File(save.getFileName());
                // сохраняем корзину в файл, расширение которого указано в настройках (json или text)
                if (save.getFormat().equals("json")) {
                    basket.saveJson(saveFile);
                } else {
                    basket.saveTxt(saveFile);
                }
            }
            // сохраняем выполненное действие
            log.log(enteredNumber, productCount);
        }

        // сохраняем все выполненные действия в лог-файл, если это указано в настройках
        Parameters toLog = new Parameters("log");
        if (toLog.getEnabled()) {
            File logFile = new File(toLog.getFileName());
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
            log.exportAsCSV(new File(toLog.getFileName()));
        }

        // выводим корзину на экран
        basket.printCart();
    }
}
