package dam.psp;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.CharBuffer;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class ClienteSSL {

	static String FORMAT = "utf-8";
	static String SERVIDOR = "localhost";
	static int PUERTO = 5555;
	public ClienteSSL()
	{
		try {
			SSLSocketFactory socketCLIFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			SSLSocket socket = (SSLSocket) socketCLIFactory.createSocket(SERVIDOR, PUERTO);

			System.out.println("Socket conectado");
			InputStreamReader in = new InputStreamReader(System.in, FORMAT);
			BufferedReader br = new BufferedReader(in);
			System.out.println("Escriba el mensaje: ");	
			PrintWriter pw = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
			pw.write(br.readLine());
			System.out.println("Mensaje enviado");
			BufferedReader brser = new BufferedReader(new InputStreamReader(socket.getInputStream(),FORMAT));
			String mensajeAlReves = brser.readLine();
			System.out.println("Mensaje recibido de vuelta: "+mensajeAlReves);
			pw.flush();
			pw.close();
			brser.close();
			socket.close();
			br.close();
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.keyStore", "./claves/AlmacenCLI");
		System.setProperty("javax.net.ssl.keyStorePassword", "abcdef");
		System.setProperty("javax.net.ssl.trustStore", "./claves/AlmacenCLI");
		System.setProperty("javax.net.ssl.trustStorePassword", "abcdef");
		new ClienteSSL();
	}
}
