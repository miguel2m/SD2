/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taller1sd;

/**
 *
 * @author Fabian Graterol
 */
public class Product {

    private String code;
    private String cantidad;
    
    public Product(String _code,String _cantidad){
        code = _code;
        cantidad = _cantidad;
    }
    
    public Product(int _code,int _cantidad){
        code = Integer.toString(_code);
        cantidad = Integer.toString(_cantidad);
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
    
    public void setCantidad(String cantidad1,String cantidad2) {
        int Total = Integer.parseInt(cantidad1) + Integer.parseInt(cantidad2);
        this.setCantidad(Integer.toString(Total));
    }
    
    @Override
    public String toString(){
        return "Producto: " + code + ",Cantidad: " + cantidad;
    }
    
    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product product = (Product)obj;
        return this.code.equals(product.code);
    }
}
