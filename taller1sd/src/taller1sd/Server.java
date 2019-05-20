/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taller1sd;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author franciscogomezlopez
 */
public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private HashMap<String, String> tiendas = new HashMap<String, String>();
    private boolean finishGame = false;
    private boolean tengopapa = false;
    private String name = "";
    private boolean sendFinishGame = false;

    public void start(int port, String name) throws IOException {

        System.out.println("Im listening ... on " + port + " I'm " + name);
        this.name = name;
        Gson g = new Gson();

        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String greeting = in.readLine();

        if(greeting.startsWith("inventario")){
            Lector lector = new Lector();
            System.out.println(lector.getTienda());
            
        } else if (greeting.startsWith("regtienda")) {
            /*
            int totallength = "regtienda".length();
            
            String tienda = greeting.substring(totallength, totallength + 4);
            String puerto = greeting.substring(totallength + 4, totallength + 8);
            String ip = greeting.substring(totallength + 8);
            */
            String[] parts = greeting.split("-");
            String tienda = parts[1];
            String puerto = parts[2];
            String ip = parts[3];
            // agregar participante
            if (!tiendas.containsKey(tienda)) {
                this.tiendas.put(tienda, ip + ":" + puerto);

                // paseo por cada uno de los elementos de los participantes para enviar la lista
                for (Map.Entry<String, String> entry : tiendas.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    String[] parts1 = value.split(":");
                    String ipDestino = parts1[0];
                    String puertoDestino = parts1[1];

                    if (!key.equals(this.name)) {

                        ClientMessage sendmessage = new ClientMessage();
                        sendmessage.startConnection(ipDestino, new Integer(puertoDestino));
                        sendmessage.sendMessage("actualizalista-" + "##" + g.toJson(tiendas));

                    }
                    
                }
                System.out.println(tiendas.toString());
                System.out.println("Tienda " + tienda +" agregada");
                out.println("Se registro una nueva tienda");
            }else{out.println("Tienda ya registrada");}
        }else if(greeting.startsWith("actualizalista")){
            System.out.println(greeting);
            String[] parts = greeting.split("##");
            this.tiendas = g.fromJson(parts[1],HashMap.class);

        }else if (greeting.startsWith("tiendaInv")) {

            String[] parts = greeting.split("-");
            if (parts[1].equals(this.name)){
                Lector lector = new Lector();
                Tienda tienda = lector.getTienda();
                out.println(g.toJson(tienda));
            }else{
                if (this.tiendas.containsKey(parts[1])){
                    String direccion = this.tiendas.get(parts[1]);
                    parts = direccion.split(":");
                    String ipDestino = parts[0];
                    String puertoDestino = parts[1];

                    ClientMessage sendmessage = new ClientMessage();
                    sendmessage.startConnection(ipDestino, new Integer(puertoDestino));
                    String response = sendmessage.sendMessage("getInventario");
                    Tienda tienda = g.fromJson(response, Tienda.class);
                    out.println(tienda.toString());
                } else{
                    out.println("Tienda no registrada");
                }
            }
            

        } else if (greeting.startsWith("getInventario")) {

            Lector lector = new Lector();
            Tienda tienda = lector.getTienda();
            out.println(g.toJson(tienda));

        } else if (greeting.startsWith("actualizalista")) {

            String lista = greeting.substring("actualizalista".length());

            String[] listatmp = lista.split(",");
            this.tiendas = new HashMap<String, String>();
            for (String tmp : listatmp) {
                String[] finaltmp = tmp.split("#");
                this.tiendas.put(finaltmp[0], finaltmp[1]);
            }

        } else if (greeting.startsWith("invEmpresa")){
            Lector lector = new Lector();
            Tienda tienda = lector.getTienda();
            for (Map.Entry<String, String> entry : tiendas.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    String[] parts = value.split(":");
                    String ipDestino = parts[0];
                    String puertoDestino = parts[1];

                    if (!key.equals(this.name)){
                        ClientMessage sendmessage = new ClientMessage();
                        sendmessage.startConnection(ipDestino, new Integer(puertoDestino));
                        String response = sendmessage.sendMessage("getInventario");
                        Tienda tiendaResponse = g.fromJson(response, Tienda.class);
                        tienda.sumarInventario(tiendaResponse);
                    }
                }
            out.println(g.toJson(tienda));
        }
        else {
            System.out.println("Mensaje no reconocido");
            out.println("mensaje corrupto vete de aqui");
        }

        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();

        if (this.tengopapa) {
            try {
                this.sendingPapa();
            } catch (InterruptedException ex) {
                System.out.println("Error sending papa");
                //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.start(port, name);

    }

    private void sendingPapa() throws InterruptedException, IOException {

        boolean found = false;

        for (Map.Entry<String, String> entry : tiendas.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (key.equals(this.name)) {
                found = true;
            } else {

                if (found) {
                    // siguiente
//                    ClientMessage sendmessage = new ClientMessage();
//                    sendmessage.startConnection(value.substring(5), new Integer(value.substring(0, 4)));
//                    sendmessage.sendMessage("recibepapa");

                    SendMessageThread hilo = new SendMessageThread();
                    hilo.setValue(value);
                    hilo.setMessage("recibepapa");
                    (new Thread(hilo)).start();
                    System.out.println("bye hilo1");
                    this.tengopapa = false;
                    break;
                }
            }

        }

        if (this.tengopapa) {
            Map.Entry<String, String> entry = this.tiendas.entrySet().iterator().next();
            String key = entry.getKey();
            String value = entry.getValue();

            SendMessageThread hilo = new SendMessageThread();
            hilo.setValue(value);
            hilo.setMessage("recibepapa");
            (new Thread(hilo)).start();
            System.out.println("bye hilo2");
            this.tengopapa = false;
        }

    }

    private String serializarLista() {

        String finallista = "";

        for (Map.Entry<String, String> entry : tiendas.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            finallista += key + "#" + value + ",";

        }

        return finallista;
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

}
