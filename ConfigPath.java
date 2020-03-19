/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basepostgresluaxls;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ConfigPath {

    private static final String FILENAME = "TestWork.xml";
    String pass, user, url;

    String getConfigSettings() throws IOException, ParserConfigurationException, SAXException {
        final File xmlFile = new File(System.getProperty("user.dir") //user.dir-это путь до домашнего каталога(каталог где хранится прога)
                + File.separator + FILENAME);//separator это разделитель =="\"

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(xmlFile);

        doc.getDocumentElement().normalize();

        System.out.println("Наш файл:" + doc.getDocumentElement().getNodeName());
        System.out.println("=================");

        NodeList nodeList = doc.getElementsByTagName("config");

        for (int i = 0; i < nodeList.getLength(); i++) {
            //выводим инфу по каждому их элементов
            Node node = nodeList.item(i);
            System.out.println();
            System.out.println("Текущий элемент: " + node.getNodeName());
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
//                    System.out.println("Пользователь:"+element.getElementsByTagName("USER").item(0).getTextContent());
//                    System.out.println("Пароль:"+element.getElementsByTagName("PASS").item(0).getTextContent());
//                    System.out.println("URL адрес:"+element.getElementsByTagName("DB_URL").item(0).getTextContent());
                pass = element.getElementsByTagName("PASS").item(0).getTextContent();
                user = element.getElementsByTagName("USER").item(0).getTextContent();
                url = element.getElementsByTagName("DB_URL").item(0).getTextContent();
            }

        }
        return null;

    }

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        ConfigPath cf = new ConfigPath();
        cf.getConfigSettings();
    }
}
