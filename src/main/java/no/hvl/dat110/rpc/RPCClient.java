package no.hvl.dat110.rpc;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.*;

/**
 * Klassen oversetter lokale metodekall til nettverksmeldinger (RPC requests) og oversetter
 * svarmeldinger tilbake til returverdier.
 */
public class RPCClient {

	// underlying messaging client used for RPC communication
	private MessagingClient msgclient;

	// underlying messaging connection used for RPC communication
	private MessageConnection connection;
	
	public RPCClient(String server, int port) {
	
		msgclient = new MessagingClient(server,port);
	}

	/**
	 * RPC-klienten kobler seg til meldingsserveren via MessagingClient
	 */
	public void connect() {
		// connect using the RPC client
		connection = msgclient.connect();
	}

	/**
	 * Metoden lukker MessageConnection
	 */
	public void disconnect() {
		// disconnect by closing the underlying messaging connection
		if (connection != null) {
			connection.close();
		}
	}

	/*
	 Make a remote call om the method on the RPC server by sending an RPC request message and receive an RPC reply message

	 rpcid is the identifier on the server side of the method to be called
	 param is the marshalled parameter of the method to be called
	 */

	/**
	 * Metoden skal lage en RPC-request, sende den til server, motta svar, pakke ut returverdi og returnere resultat
	 * @param rpcid - metoden vi vil at serveren skal kjøre
	 * @param param - parameteret til metoden, gjort om til bytes
	 * @return - serverens returverdi, også bytes
	 */

	public byte[] call(byte rpcid, byte[] param) {

		byte[] returnval = null;

		/*
		The rpcid and param must be encapsulated according to the RPC message format
		The return value from the RPC call must be decapsulated according to the RPC message format
		*/

		// hvis metoden ikke har parameter kan "byte[] request" kræsje. vi legger derfor til en sjekk.
		if (param == null) {
			param = new byte[0];
		}

		byte[] request = RPCUtils.encapsulate(rpcid, param);

		Message requestmsg = new Message(request);

		// sender forespørselen som en Message via Messaging Layer
		connection.send(requestmsg);

		// Mottar svar fra serveren
		Message replymsg = connection.receive();
		byte[] reply = replymsg.getData();

		// Pakker ut returverdien
		returnval = RPCUtils.decapsulate(reply);

		return returnval;
		
	}

}
