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
    
    @Override
    public String toString(){
        return "Producto: " + code + ",Cantidad: " + cantidad;
    }
}
