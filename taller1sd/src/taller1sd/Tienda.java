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
public class Tienda {
    private String name;
    private List<Product> Products;
    
    public Tienda(){
        Products = new ArrayList<Product>();
    }
    
    public void addProduct(Product product){
        Products.add(product);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(List<Product> Products) {
        this.Products = Products;
    }
    
    @Override
    public String toString(){
        return "Tienda: " + name;
    }
}
