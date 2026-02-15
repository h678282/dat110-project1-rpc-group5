package no.hvl.dat110.rpc;

import java.nio.ByteBuffer;
import java.util.Arrays;
import no.hvl.dat110.TODO;

/**
 * Nettverk kan kun sende byte[], men metodene våre bruker både int, String, boolean og void
 * Vi må derfor oversette disse datatypene til bytes.
 */
public class RPCUtils {

	/**
	 * Lager en RPC melding ved å først legge inn rpcid og deretter payload.
	 * @param rpcid - forteller oss hvilken metode serveren skal kjøre
	 * @param payload - parameteren til metoden (gjort om til bytes)
	 * @return - et byte-array som representerer RPC-forespørselen
	 */
	public static byte[] encapsulate(byte rpcid, byte[] payload) {
		
		byte[] rpcmsg = null;

		// Encapsulate the rpcid and payload in a byte array according to the RPC message syntax / format
		if (payload == null) {
			payload = new byte[0];
		}

		// første byte = rpcid, resten av dem = payload
		rpcmsg = new byte[1 + payload.length];
		rpcmsg[0] = rpcid;

		System.arraycopy(payload, 0, rpcmsg, 1, payload.length);

		return rpcmsg;
	}

	/**
	 * Fjerner rpcid fra en RPC-melding og returnerer kun payload
	 * @param rpcmsg - RPC-meldingen
	 * @return - payload uten rpcid
	 */
	public static byte[] decapsulate(byte[] rpcmsg) {
		
		byte[] payload = null;

		// Decapsulate the rpcid and payload in a byte array according to the RPC message syntax

		// Vi fjerner første byte (rpcid delen) og beholder resten (payload)
		payload = Arrays.copyOfRange(rpcmsg, 1, rpcmsg.length);
		
		return payload;
	}

	// convert String to byte array
	public static byte[] marshallString(String str) {
		
		byte[] encoded = null;
		encoded = str.getBytes();
		
		return encoded;
	}

	// convert byte array to a String
	public static String unmarshallString(byte[] data) {
		
		String decoded = null; 
		decoded = new String(data);
		
		return decoded;
	}

	/**
	 * Metoden har ingen parameter eller returverdi
	 * @return - Vi returnerer en tom byte-tabell
	 */
	public static byte[] marshallVoid() {
		
		byte[] encoded = null;
		encoded = new byte[0];
		
		return encoded;
		
	}
	
	public static void unmarshallVoid(byte[] data) {

		// vi sjekker om data er tom og sender en feilmelding hvis ikke
		if (data.length != 0) {
			throw new IllegalArgumentException("void should have an empty payload");
		}
	}

	// convert boolean to a byte array representation
	public static byte[] marshallBoolean(boolean b) {
		
		byte[] encoded = new byte[1];
				
		if (b) {
			encoded[0] = 1;
		} else
		{
			encoded[0] = 0;
		}
		
		return encoded;
	}

	// convert byte array to a boolean representation
	public static boolean unmarshallBoolean(byte[] data) {
		
		return (data[0] > 0);
		
	}

	// integer to byte array representation
	public static byte[] marshallInteger(int x) {
		
		byte[] encoded = null;

		// I Java er et heltall 4 bytes
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(x);
		encoded = buffer.array();
		
		return encoded;
	}
	
	// byte array representation to integer
	public static int unmarshallInteger(byte[] data) {
		
		int decoded = 0;
		
		// Vi gjør 4 bytes om til et heltall (int)
		ByteBuffer buffer = ByteBuffer.wrap(data);
		decoded = buffer.getInt();
		
		return decoded;
		
	}
}
