/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author eliazith
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class Servidor {
    
    public static void main(String[] args) throws SocketException {
        
        final int PUERTO = 5000;
        DatagramSocket socketUDP = new DatagramSocket(PUERTO); 
        var valid = true;
        
        System.out.println("Server en funcionamiento");
        do
        {
            try
            { 
                byte[] buffer = new byte[1024];
                DatagramPacket peticion = new DatagramPacket(buffer,buffer.length);
                socketUDP.receive(peticion);
                System.out.println("------------------------------");
                System.out.println("Nueva informacion recibida:\n");
               
                String MensajeCliente = new String(peticion.getData());
                
               
                System.out.println("Mensaje: "+MensajeCliente+"\n");
                String exit = "@kilConex";
                if(MensajeCliente.indexOf(exit) >-1)
                {
                    int puertoCliente = peticion.getPort();
                    InetAddress direccion = peticion.getAddress();
                    String Cierre = "Conexion Cerrada";
                    buffer = Cierre.getBytes();
                    System.out.println("Cliente: "+direccion+" puerto: "+puertoCliente+"\n");
                    System.out.println("Cerrando la conexion");
                    System.out.println("Notificando al cliente "+direccion+"\n");
                    DatagramPacket Respuesta = new DatagramPacket(buffer,buffer.length,
                    peticion.getAddress(), peticion.getPort());
                    socketUDP.send(Respuesta);
                    socketUDP.close();
                    System.out.println("Conexion cerrada");
                    valid = false;
                    break;
                }
                else
                {
                    int puertoCliente = peticion.getPort();
                    InetAddress direccion = peticion.getAddress();
                    System.out.println("Cliente: "+direccion+" puerto: "+puertoCliente+"\n");
            
                    System.out.println("Respondiendo al cliente "+direccion+"\n");
                    DatagramPacket Respuesta = new DatagramPacket(peticion.getData(), peticion.getLength(),
                    peticion.getAddress(), peticion.getPort());
                    socketUDP.send(Respuesta);
                }
            }
            catch(IOException e)
            {
                System.out.println(e);
            }
            catch(Exception i)
            {
                System.out.println("Error: " + i.getMessage());
            }
        }while(true == valid);
    }
}
   
