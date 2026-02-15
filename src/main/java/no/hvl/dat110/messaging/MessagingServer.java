package no.hvl.dat110.messaging;

import java.io.IOException;
import java.net.ServerSocket;

import no.hvl.dat110.TODO;

/**
 * Har en server-socket som lytter på en port
 */
public class MessagingServer {

	// server-side socket for accepting incoming TCP connections
	private ServerSocket welcomeSocket;

	public MessagingServer(int port) {

		try {

			this.welcomeSocket = new ServerSocket(port);

		} catch (IOException ex) {

			System.out.println("Messaging server: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Venter til en klient kobler seg på før den returnerer en MessageConnection
	 * @return - MessageConnection
	 */
	// accept an incoming connection from a client
	public MessageConnection accept() {

		MessageConnection connection = null;

		// accept TCP connection on welcome socket and create messaging connection to be returned
		try {
			// venter (stopper programmet) helt til en klient kobler seg til
			java.net.Socket socket = welcomeSocket.accept();

			/* når en klient er koblet til får vi en socket og oppretter en MessageConnection
			som kan sende og motta meldinger over denne. */
			connection = new MessageConnection(socket);

		} catch (IOException ex) {
			System.out.println("MessagingServer.accept: " + ex.getMessage());
			ex.printStackTrace();
		}
		
		return connection;

	}

	public void stop() {

		if (welcomeSocket != null) {

			try {
				welcomeSocket.close();
			} catch (IOException ex) {

				System.out.println("Messaging server: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}

}
