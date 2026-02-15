package no.hvl.dat110.system.display;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.RPCRemoteImpl;
import no.hvl.dat110.rpc.RPCUtils;
import no.hvl.dat110.rpc.RPCServer;

/**
 * Mottar et PRC-kall fra klienten og utfører write()-metoden som skriver til konsollen.
 */
public class DisplayImpl extends RPCRemoteImpl {

	public DisplayImpl(byte rpcid, RPCServer rpcserver) {
		super(rpcid,rpcserver);
	}

	public void write(String message) {
		System.out.println("DISPLAY:" + message);
	}

	/**
	 * Utfører write-metoden via RPC
	 * @param param . meldingen mottat som et byte array
	 * @return - tomt byte-array som bekrefter at operasjonen er utført
	 */
	public byte[] invoke(byte[] param) {
		
		byte[] returnval = null;

		// implement unmarshalling, call, and marshall for write RPC method
		// look at how this is done in the SensorImpl class for the read method
		String message = RPCUtils.unmarshallString(param);
		write(message);

		returnval = RPCUtils.marshallVoid();
		return returnval;
	}
}
