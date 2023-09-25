package lab3;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DOMParser {
    public static List<PCComponent> parsePCComponents(String filePath) throws Exception {
        List<PCComponent> computers = new ArrayList<>();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(filePath);

        NodeList nList = doc.getElementsByTagName("pc_component");

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                PCComponent component = new PCComponent();
                component.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                component.setOrigin(eElement.getElementsByTagName("origin").item(0).getTextContent());
                component.setPrice(Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent()));
                //TODO здесь сетить для типов
                component.setIsCritical(Boolean.parseBoolean(eElement.getElementsByTagName("is_critical").item(0).getTextContent()));

                computers.add(component);
            }
        }
        return computers;
    }

    public static void main(String[] args) {
        try {
            List<PCComponent> computers = parsePCComponents("pc_components.xml");
            writeComputersToFile(computers);
            validateAndPrintComputers(computers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeComputersToFile(List<PCComponent> cards) throws IOException {
        List<String> outputLines = cards.stream().map(PCComponent::toString).collect(Collectors.toList());
        Files.write(Paths.get("output.txt"), outputLines);
    }

    private static void validateAndPrintComputers(List<PCComponent> components) {
        for (PCComponent component : components) {
            try {
                component.validate();
                System.out.println("Valid component: " + component);
            } catch (Exception e) {
                System.out.println("Invalid component detected: " + component + " Reason: " + e.getMessage());
            }
        }
    }
}
