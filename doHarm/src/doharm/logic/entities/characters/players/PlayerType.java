package doharm.logic.entities.characters.players;

/**
 * Possible types a player can be.
 * @author Roland
 */

public enum PlayerType 
{
	HUMAN, //only one of these per client, no human players in the server.
	NETWORK, //all clients in server are represented as network players
	AI, //Artificial Intelligence players
}
