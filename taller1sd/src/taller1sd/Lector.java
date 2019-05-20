/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taller1sd;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
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
    
    //private String fileName = "C:\\Users\\Fabian Graterol\\Desktop\\universidad\\9no semestre\\distribuidas\\proyecto 1\\SD2\\taller1sd\\src\\taller1sd\\productos.xml";
        private String fileName = "productos.xml";

    private Document doc;
    
    public Lector(){
        try{
            this.getDoc();
        }
        catch(NullPointerException e){
            this.createDoc();
        }
    }
    
    private void getDoc() throws NullPointerException{
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            URL url = getClass().getResource(fileName);
            URI uri = url.toURI();
            File fXmlFile = new File(uri);
            //File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
        }catch (ParserConfigurationException | SAXException | IOException | URISyntaxException ex) {
            Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createDoc(){
            try {
                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                Document document = documentBuilder.newDocument();
                
                Element root = document.createElement("Tienda");
                document.appendChild(root);
                
                //save the new document
                URL url = getClass().getResource(fileName);
                //URI uri = url.toURI();
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(this.fileName));
                transformer.transform(source, result);
                
                //this.getDoc();
            } catch (ParserConfigurationException | TransformerConfigurationException ex) {
                Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public static void main(String[] args) {
        /*
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            Tienda tienda = new Tienda();
            
            fXmlFile = new File(fileName);
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            
            NodeList nList = doc.getElementsByTagName("Tienda");
            tienda = new Tienda();
            tienda.setName(((Element)nList.item(0)).getElementsByTagName("name").item(0).getTextContent());
            System.out.println(tienda.toString());
            System.out.println("----------------------------");
            nList = doc.getElementsByTagName("product");
            
            for (int i = 0; i < nList.getLength(); i++){
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String code = eElement.getElementsByTagName("code").item(0).getTextContent();
                    String cantidad = eElement.getElementsByTagName("cantidad").item(0).getTextContent();
                    Product product = new Product(code,cantidad);
                    System.out.println(product.toString());
                }
            }
        }
        catch(DOMException e){
            System.out.println("archivo no encontrado");
        } catch (ParserConfigurationException ex) {
            System.out.println("archivo no encontrado");
        } */
    }
    
    public String getNombreTienda(){
        String nombre;
        Element eElement;
        
        NodeList nList = doc.getElementsByTagName("Tienda");
        eElement = (Element)nList.item(0);
        nombre = eElement.getElementsByTagName("name").item(0).getTextContent();

        return nombre;
    }
    
    public Tienda getTienda(){
        Tienda tienda;
        Element eElement;
        
        NodeList nList = doc.getElementsByTagName("Tienda");
        eElement = (Element)nList.item(0);
        String name = this.getNombreTienda();
        List<Product> products = this.getTiendaProductos();
        tienda = new Tienda(name,products);

        return tienda;
    }
    
    public List<Product> getTiendaProductos(){
        List<Product> products = new ArrayList<>();
        
        NodeList nList = doc.getElementsByTagName("product");
        for (int i = 0; i < nList.getLength(); i++){
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String code = eElement.getElementsByTagName("code").item(0).getTextContent();
                String cantidad = eElement.getElementsByTagName("cantidad").item(0).getTextContent();
                Product product = new Product(code,cantidad);
                products.add(product);
            }
        }
        
        return products;
    }
    
    public Tienda sumEnterpriseProducts(Tienda _tienda){
        List<Product> LProducts = getTiendaProductos();
        List<Product> EProducts = _tienda.getProducts();
        LProducts.forEach((LProduct) -> {
            if (EProducts.contains(LProduct)){
                int index = EProducts.indexOf(LProduct);
                EProducts.get(index).setCantidad(EProducts.get(index).getCantidad(),LProduct.getCantidad());
            } else{
                EProducts.add(LProduct);
            }
        }); 
        _tienda.setProducts(EProducts);
        return _tienda;
    }
    
    public void AddProduct(String _code,String _cantidad){
        
        NodeList products = doc.getElementsByTagName("products");
        Element newProduct = doc.createElement("product");

        Element code = doc.createElement("code");
        code.setTextContent(_code);

        Element cantidad = doc.createElement("cantidad");
        cantidad.setTextContent(_cantidad);

        newProduct.appendChild(code);
        newProduct.appendChild(cantidad);
        newProduct.setAttribute("id",_code);
        products.item(0).appendChild(newProduct);
        saveData();
    }
    
    public void SumProduct(String _code,String _cantidad){
        XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            NodeList nodes = (NodeList)xpath.evaluate
                ("//products/product[@id='" + _code + "']", doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                
                String cantidad = nodes.item(i).getChildNodes().item(1).getTextContent();
                int total = Integer.parseInt(cantidad) + Integer.parseInt(_cantidad);
                if (total >= 0){
                    String STotal = Integer.toString(total);
                    nodes.item(i).getChildNodes().item(1).setTextContent(STotal);
                } else{
                    System.out.println("No hay suficiente inventario");
                }
              }
        } catch (XPathExpressionException ex) {
            Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
        }
        saveData();
        
    }
    
    public void saveData(){
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            URL url = getClass().getResource(fileName);
            URI uri = url.toURI();
            StreamResult result = new StreamResult(new File(uri));
            //StreamResult result = new StreamResult(fileName);
            transformer.transform(source, result);
            
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
        }   catch (URISyntaxException ex) {
                Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
