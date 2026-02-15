package no.hvl.dat110.messaging;

import java.util.Arrays;

import no.hvl.dat110.TODO;

/**
 * Dette er selve format-koden.
 */
public class MessageUtils {

	public static final int SEGMENTSIZE = 128;

	public static int MESSAGINGPORT = 8080;
	public static String MESSAGINGHOST = "localhost";

	/**
	 * Tar parameteret message og lager et 128-bytes segment, setter lengde i [0} og kopierer payload fra [1]
	 * @param message - tar inn en melding som parameter
	 * @return - returnerer et 128-byte segment med meldingen i
	 */
	// encapulate/encode the payload data of the message and form a segment
	// according to the segment format for the messaging layer
	public static byte[] encapsulate(Message message) {
		
		byte[] segment = new byte[SEGMENTSIZE];
		byte[] data = message.getData();
		int length = data.length;
		
		// header = payload length (0..127)
		segment[0] = (byte) length;

		// payload bytes
		System.arraycopy(data, 0, segment, 1, length);

		// resten av segmentet er padding (default 0 i en ny byte[])

		return segment;
		
	}

	/**
	 * Tar 128-bytes segment, leser lengde, tar ut payload-bytene og lager Message
	 * @param segment - tar inn 128-byte segment som parameter
	 * @return - selve meldingen returneres
	 */
	// decapsulate segment and put received payload data into a message
	public static Message decapsulate(byte[] segment) {
		
		if (segment == null || segment.length != SEGMENTSIZE) {
			throw new IllegalArgumentException("Segment must be exactly " + SEGMENTSIZE + " bytes");
		}

		// segment[0] er en byte så vi gjør den om til 0..255
		int length = segment[0] & 0xFF;

		if (length > 127) {
			throw new IllegalArgumentException("Invalid payload length in segment header: " + length);
		}

		byte[] data = Arrays.copyOfRange(segment, 1, 1 + length);
		
		return new Message(data);
		
	}
}
