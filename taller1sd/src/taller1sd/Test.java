/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taller1sd;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fabian Graterol
 */
public class Test {

    public static void main(String[] args) {
        Lector lector = new Lector();
        //System.out.println(lector.sumProducts(Test.mockTienda()).toString());
        //lector.sumProducts(Test.mockTienda());
        //lector.restProduct("2", "30");
        //lector.AddProduct("5", "100");
        //System.out.println(lector.getTienda());
    }
    
    public static Tienda mockTienda(){
        Tienda tienda = new Tienda("mockTienda",Test.mockProducts());
        return tienda;
    }
    
    public static List<Product> mockProducts(){
        List<Product> products = new ArrayList<>();
        Product product;
        
        product = new Product(1,100);
        products.add(product);
        product = new Product(2,400);
        products.add(product);
        product = new Product(3,200);
        products.add(product);
        product = new Product(7,200);
        products.add(product);
        
        return products;
    }
}
