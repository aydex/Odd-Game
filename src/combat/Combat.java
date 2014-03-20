package combat;

import party.FriendlyParty;
import party.EnemyParty;

public class Combat {
	
	private FriendlyParty party;
	private EnemyParty enemy;
	
	/**
	 * Constructs a combat object with two sides
	 * @param party The friendlyParty participating in the combat
	 * @param enemy The enemy party in the combat
	 */
	public Combat(FriendlyParty party, EnemyParty enemy){
		this.party = party;
		this.enemy = enemy;
	}
	
	/**
	 * Resolves a combat between the two sides in the object, returning who won
	 * @return Won won the fight, true if the friendly party won, else false
	 */
	public boolean fight(){
		return false;
	}

}
