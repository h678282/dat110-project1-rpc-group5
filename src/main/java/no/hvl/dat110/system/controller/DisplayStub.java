package no.hvl.dat110.system.controller;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.*;

/**
 * Klassen gjør at controlleren kan kalle på write(), som om displayet hadde vært lokalt,
 * men metoden blir egentlig utflrt på display-serveren med RPC.
 */

public class DisplayStub extends RPCLocalStub {

	public DisplayStub(RPCClient rpcclient) {
		super(rpcclient);
	}

	/**
	 * Metoden sender en tekst (som skal vises) til display serveren
	 * @param message - selve teksten
	 */
	public void write (String message) {

		// implement marshalling, call and unmarshalling for write RPC method
		// marshall parameter
		byte[] request = RPCUtils.marshallString(message);

		// svaret fra serveren
		byte[] response = rpcclient.call((byte) Common.WRITE_RPCID, request);

		// write metoden har void som returparameter, så vi bare verifiserer responsen
		RPCUtils.unmarshallVoid(response);
		
	}
}
