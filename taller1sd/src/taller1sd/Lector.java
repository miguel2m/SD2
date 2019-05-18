/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taller1sd;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Fabian Graterol
 */
public class Lector {
    
    String fileName = "/productos.xml";
    File xmlFile;
    
    public static void main(String[] args) {
        String fileName = "C:\\Users\\Fabian Graterol\\Desktop\\universidad\\9no semestre\\distribuidas\\proyecto 1\\SD2\\taller1sd\\src\\taller1sd\\productos.xml";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(fileName));
            document.getDocumentElement().normalize();
            Element root = document.getDocumentElement();
        
            NodeList nList = document.getElementsByTagName("Tienda");
            
            Tienda tienda = new Tienda();
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                
                Element eElement = (Element) node;
                System.out.println(eElement.getAttributeNode("name"));
                String name = node.getTextContent();
                tienda.setName(name);
                //System.out.println(tienda.toString());
            }
        }
        catch(DOMException e){
            System.out.println("archivo no encontrado");
        } catch (ParserConfigurationException ex) {
            System.out.println("archivo no encontrado");
        } catch (SAXException ex) {
            System.out.println("archivo no encontrado");
        } catch (IOException ex) {
            System.out.println("archivo no encontrado");
        }
    }
}
