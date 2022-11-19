import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    private List<String[]> list = new ArrayList<>();

    public void log(int productNum, int amount) {
        String[] temp = {Integer.toString(productNum), Integer.toString(amount)};
        list.add(temp);
    }

    public void exportAsCSV(File txtFile) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true));) {
            for (String[] s : list) {
                writer.writeNext(s);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}