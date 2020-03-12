/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.*;

/**
 *
 * @author cherepanov
 */
public class testWriteXML {

    public void WriteXML() throws org.xml.sax.SAXException, ParserConfigurationException, IOException, TransformerException {
        File file = new File("Config.xml");
        
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc=dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: "+doc.getDocumentElement().getNodeName());//создали нфайл или открыли,еще не понимаю
            
            Element nList=doc.getDocumentElement();
            System.out.println("------------");
            Element newItem=doc.createElement("item");
            
            newItem.setNodeValue("some text");
            nList.appendChild(newItem);
            
            Transformer transformer =TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            StreamResult result=new StreamResult(new StringWriter());
            DOMSource sourse=new DOMSource(doc);
            transformer.transform(sourse, result);
            String xmlString=result.getWriter().toString();
            System.out.println(xmlString);
            
            PrintWriter output=new PrintWriter(file);
            output.println(xmlString);
            output.close();

        
    }
    public static void main(String[] args) throws org.xml.sax.SAXException, ParserConfigurationException, IOException, TransformerException {
        testWriteXML test=new testWriteXML();
        test.WriteXML();
    }
}
