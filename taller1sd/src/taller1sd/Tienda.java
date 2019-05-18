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
    private List<Product> products;
    
    public Tienda(){
        products = new ArrayList<Product>();
    }
    
    public Tienda(String _name){
        name = _name;
        products = new ArrayList<Product>();
    }
    
    public Tienda(String _name,List<Product> _products){
        name = _name;
        products = _products;
    }
    
    public void addProduct(Product product){
        products.add(product);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> Products) {
        this.products = Products;
    }
    
    @Override
    public String toString(){
        String retorno;
        retorno = "Tienda: " + name + "\nProductos:\n";
        retorno = products.stream().map((product) -> "*\t" + product.toString() + "\n").reduce(retorno, String::concat);
        return retorno;
    }
}
