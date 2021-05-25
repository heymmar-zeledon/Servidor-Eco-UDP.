
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.net.SocketException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws IOException {
        byte[] buffer2 = new byte[1024];
        DatagramSocket socketUDP = new DatagramSocket();
        String Bienvenida = new String();
        Bienvenida = "Bienvenido";
        Scanner sc = new Scanner(System.in);
        System.out.println("Escriba el puerto del servidor: ");
        final int PUERTO_SERVIDOR = sc.nextInt();
        sc.nextLine();
        //Obtengo la localizacion del host
        System.out.println("--Conexion al servidor--");
        System.out.println("Escriba la direccion ip del servidor o (localhost): ");
        String direccion = sc.nextLine();
        if("".equals(direccion))
        {
            direccion = "localhost";
        }
        InetAddress direccionServidor = InetAddress.getByName(direccion);
        try
        {
            //obtengo el puerto donde escucha el servidor
            
            buffer2 = Bienvenida.getBytes();
            //Creo un datagrama
            DatagramPacket pregunta = new DatagramPacket(buffer2, buffer2.length, direccionServidor, PUERTO_SERVIDOR);
            //Lo envio con send
            socketUDP.send(pregunta);
        }
        catch(SocketException e)
        {
            System.out.println("Error! Datos incorrectos");
            System.out.println(e);
        }
        catch(Exception i)
        {
            System.out.println("Error!"+i.getMessage());
        }
        var validar = true;
        do
        {
            byte[] buffer1 = new byte[1024];
            //Recibo la respuesta
            DatagramPacket peticion = new DatagramPacket(buffer1, buffer1.length);
            socketUDP.receive(peticion);
            
            String mensaje2 = new String(peticion.getData());
            System.out.println("El servidor dice: "+mensaje2);
            System.out.println("-------------------------------");
            String exit = "Conexion Cerrada";
            if(mensaje2.indexOf(exit)>-1)
            {
                System.out.println("servidor cerrado");
                System.out.println("Cerrando cliente");
                socketUDP.close();
                validar = false;
                break;
            }
            else
            {
                System.out.println("Escriba el mensaje que desea enviar al servidor: ");
                String mensaje = sc.nextLine();
                if(mensaje.isEmpty())
                {
                    mensaje = "@kilConex";
                }
                //Convierto el mensaje a bytes
                buffer1 = mensaje.getBytes();
                //Creo un datagrama
                DatagramPacket message = new DatagramPacket(buffer1, buffer1.length, direccionServidor, PUERTO_SERVIDOR);
                //Lo envio con send
                socketUDP.send(message);
            }
        }while(true == validar);
        System.out.println("Finalizado");
    }
}