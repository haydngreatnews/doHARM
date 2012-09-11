package doharm.logic.gameobjects.entities.players;

public enum PlayerType 
{
	HUMAN, //only one of these per client, no human players in the server.
	NETWORK, //all clients in server are represented as network players
	NPC, //AI players
}
