package dam.psp;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class ServidorSSL {
	
	static int PUERTO=5555;
	static String FORMAT = "utf-8";
	
	public ServidorSSL()
	{
		SSLServerSocketFactory serverSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		SSLServerSocket socketSRV;
		try {
			socketSRV = (SSLServerSocket) serverSocketFactory.createServerSocket(PUERTO);
			while (true) {
				    SSLSocket socket = (SSLSocket) socketSRV.accept();
					System.out.println("Creando hilo para tratamiento de cliente");
					new ServidorThread(socket).start();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.keyStore", "./claves/AlmacenSRV");
		System.setProperty("javax.net.ssl.keyStorePassword", "123456");
		System.setProperty("javax.net.ssl.trustStore", "./claves/AlmacenSRV");
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
		new ServidorSSL();
	}
	
	public class ServidorThread extends Thread
	{
		SSLSocket socket;
		public ServidorThread(SSLSocket socket)
		{
			this.socket=socket;
		}
		@Override
		public void run() {
			
			try {
				System.out.println("Hilo creado");
				InputStreamReader input =new InputStreamReader(socket.getInputStream(),FORMAT);
				
				BufferedReader br = new BufferedReader(input);
				System.out.println("Esperando Mensaje");
				/* Se queda infinitamente esperando el mensaje, la conexion se establece correctamente y el servidor puede responder perfectamente
				 * Pero se queda atascado esperando el br.readLine();
				String mensaje = br.readLine();
				System.out.println("Mensaje recibido: "+mensaje);
				*/
				PrintWriter pw = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()),true);
				//String mensajeReves = MyReverse(mensaje);
				String mensajeReves= "Probando";
				System.out.println("Mensaje dado la vuelta: "+mensajeReves);
				pw.print(mensajeReves);
				System.out.println("Mensaje enviado, fin del hilo");
				pw.flush();
				pw.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private String MyReverse(String mensaje) {
		return new StringBuilder(mensaje).reverse().toString().toUpperCase();
		
	}
}
