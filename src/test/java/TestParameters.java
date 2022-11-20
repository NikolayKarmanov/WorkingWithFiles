import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class TestParameters {

    public TestParameters() throws ParserConfigurationException, IOException, SAXException {
    }

    // Проверяем, действительно ли в xml-файле элемент "load" содержит вложенный элемент "enabled" со значением "false"
    String load = "load";
    Parameters parameters1 = new Parameters(load);
    @Test
    public void testDataFromLoad() {
        boolean result = parameters1.getEnabled();
        boolean expected = false;
        Assertions.assertEquals(expected, result);
    }

     // Проверяем, действительно ли в xml-файле элемент "save" содержит вложенный элемент "enabled" со значением "true"
        String save = "save";
        Parameters parameters2 = new Parameters(save);
        @Test
        public void testDataFromSave() {
            boolean result = parameters2.getEnabled();
            boolean expected = true;
            Assertions.assertEquals(expected, result);
        }

    /*
    Если мы создадим новый объект Parameters, передав в конструктор названия элемента,
    которых нет в XML-файле, то значение полей объектов останется по умолчанию.
    Для полей типа boolean значение по умолчанию это FALSE.
    */
    @ParameterizedTest
    @ValueSource(strings = {"test", "Load", "savE", "false"})
    public void testDataFromNull(String arg) throws ParserConfigurationException, IOException, SAXException {
        Parameters parameters3 = new Parameters(arg);
        boolean result = parameters3.getEnabled();
        Assertions.assertFalse(result);
    }



}

