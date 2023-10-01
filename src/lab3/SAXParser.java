package lab3;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SAXParser {

    public static List<PCComponent> parsePCComponents(String filePath) throws Exception {
        List<PCComponent> components = new ArrayList<>();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();

        DefaultHandler handler = new DefaultHandler() {
            PCComponent component;
            String currentElement;

            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                currentElement = qName;
                if ("pc_component".equals(currentElement)) {
                    component = new PCComponent();
                }
            }

            public void endElement(String uri, String localName, String qName) {
                if ("pc_component".equals(qName)) {
                    components.add(component);
                }
                currentElement = "";
            }

            public void characters(char[] ch, int start, int length) {
                String value = new String(ch, start, length).trim();
                if (value.isEmpty()) return;

                if ("name".equals(currentElement)) {
                    component.setName(value);
                } else if ("origin".equals(currentElement)) {
                    component.setOrigin(value);
                } else if ("price".equals(currentElement)) {
                    component.setPrice(Double.parseDouble(value));
                } else if ("is_critical".equals(currentElement)) {
                    component.setIsCritical(Boolean.parseBoolean(value));
                }
            }
        };

        saxParser.parse(filePath, handler);
        return components;
    }

    public static void main(String[] args) {
        try {
            List<PCComponent> components = parsePCComponents("pc_components.xml");
            writeComponentsToFile(components);
            validateAndPrintComponents(components);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void validateAndPrintComponents(List<PCComponent> components) {
        for (PCComponent component : components) {
            try {
                component.validate();
                System.out.println("Valid component: " + component);
            } catch (Exception e) {
                System.out.println("Invalid component detected: " + component + " Reason: " + e.getMessage());
            }
        }
    }

    private static void writeComponentsToFile(List<PCComponent> components) throws IOException {
        List<String> outputLines = components.stream().map(PCComponent::toString).collect(Collectors.toList());
        Files.write(Paths.get("output.txt"), outputLines);
    }
}
