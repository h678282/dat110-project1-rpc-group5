package no.hvl.dat110.messaging;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import no.hvl.dat110.TODO;

/**
 * Dette er selve koblingen eller broen som skal sende og motta meldinger over en socket
 * Klassen inneholder DataOutputStream og DataInputStream
 */
public class MessageConnection {

	private DataOutputStream outStream; // for writing bytes to the underlying TCP connection
	private DataInputStream inStream; // for reading bytes from the underlying TCP connection
	private Socket socket; // socket for the underlying TCP connection
	
	public MessageConnection(Socket socket) {

		try {

			this.socket = socket;

			outStream = new DataOutputStream(socket.getOutputStream());

			inStream = new DataInputStream (socket.getInputStream());

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Bruker MessageUtils.encapsulate fra MessageUtils klassen til å sende 128 bytes melding på socket
	 * @param message - melding vi vil sende
	 */
	public void send(Message message) {

		byte[] data;

		// encapsulate the data contained in the Message and write to the output stream
		// Vi gjør om "Message" til et 128-byte segment
		data = MessageUtils.encapsulate(message);

		try {
			// vi skriver alle 128 bytes til tilkoblingen
			outStream.write(data);

			// flush gjør at bytene sendes ut med en gang og ikke blir liggende i en buffer
			outStream.flush();

		} catch (IOException ex) {
			System.out.println("MessageConnection.send: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Leser segment på 128 byte og bruker MessageUtils.decapsulate() til å returnere meldingen
	 * @return - selve meldingen
	 */
	public Message receive() {

		Message message = null;
		byte[] data;

		// read a segment from the input stream and decapsulate data into a Message
		try {
			// vi sørger for at vi alltid får et helt segment (128 bytes)
			data = new byte[MessageUtils.SEGMENTSIZE];

			inStream.readFully(data);
			message = MessageUtils.decapsulate(data);

		} catch (IOException ex) {
			System.out.println("MessageConnection.receive: " + ex.getMessage());
			ex.printStackTrace();
		}
		return message;
	}

	// close the connection by closing streams and the underlying socket	
	public void close() {

		try {
			
			outStream.close();
			inStream.close();

			socket.close();
			
		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}