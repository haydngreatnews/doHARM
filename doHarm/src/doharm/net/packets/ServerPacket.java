package doharm.net.packets;

/**
 * Enum for different types of packet sent from the server.
 * @author Adam McLaren (300248714)
 */
public enum ServerPacket {
	NONE,
	SNAPSHOT,
	GAMESTATE,
	RESPONSE;
}
