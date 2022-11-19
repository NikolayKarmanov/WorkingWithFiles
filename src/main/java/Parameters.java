import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Parameters {
    private boolean enabled;
    private String fileName;
    private String format;

    public Parameters(String action) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("shop.xml"));
        Node root = doc.getDocumentElement();

        NodeList actions = root.getChildNodes();
        for (int i = 0; i < actions.getLength(); i++) {
            Node node = actions.item(i);
            if (node.getNodeName().equals(action)) {
                NodeList parameters = node.getChildNodes();
                for (int j = 0; j < parameters.getLength(); j++) {
                    Node node1 = parameters.item(j);
                    if (Node.ELEMENT_NODE == node1.getNodeType()) {
                        if(node1.getNodeName().equals("enabled")) {
                            enabled = node1.getTextContent().equals("true") ? true : false;
                        }
                        if(node1.getNodeName().equals("fileName")) {
                            fileName = node1.getTextContent();
                        }
                        if(node1.getNodeName().equals("format")) {
                            format = node1.getTextContent();
                        }
                    }
                }
            }
        }

    }

    public boolean getEnabled() {
        return enabled;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFormat() {
        return format;
    }
}
