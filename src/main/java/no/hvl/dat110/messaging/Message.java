package no.hvl.dat110.messaging;

import no.hvl.dat110.TODO;

/**
 * En enkel container som kun holder på payload-data (maks 127 bytes)
 * Sjekker også at all data er gyldig
 * Når vi er ferdig å implementere "messaging" - Task 1 skal vi kunne gjøre følgende:
 * Klient kan koble til server og sende en melding, f.eks. Message("Hello")
 * Serveren skal kunne lese den samme meldingen og få "Hello" tilbake, og alt skal skje via 128 bytes segmenter.
 */

public class Message {

	// the up to 127 bytes of data (payload) that a message can hold
	private byte[] data;

	// construction a Message with the data provided
	public Message(byte[] data) {

		/**
		 * Vi sjekker at data ikke er null og at lengden på data er mindre enn eller lik 127
		 * bytes, før vi lagrer dataen.
		 */
		if (data == null) {
			throw new IllegalArgumentException("Message data cannot be null");
		}
		if (data.length > 127) {
			throw new IllegalArgumentException("Message data cannot be longer than 127 bytes");
		}

		// vi lagrer en kopi av data for å unngå at caller endrer tabellen senere.
		this.data = java.util.Arrays.copyOf(data, data.length);
	}

	public byte[] getData() {
		return this.data; 
	}

}
