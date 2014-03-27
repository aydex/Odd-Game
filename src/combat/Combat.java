package combat;

import party.FriendlyParty;
import party.EnemyParty;
import party.Member;

public class Combat {
	
	private FriendlyParty party;
	private EnemyParty enemy;
	private Member target;
	private String attack;
	
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
	 * "Main" method in combat, resolves a combat, and performs necessary clean up, as well as handing out rewards etc.
	 */
	public void performCombat(){
		
		if(fight()){
			//Party wins combat, display victory message. Give out exp., loot, reset health, etc.
		}
		else{
			//Party is defeated, display game over message
		}
	}
	
	/**
	 * Resolves a combat between the two sides in the object, returning who won
	 * @return Won Which side won the fight, true if the friendly party won, else false
	 */
	private boolean fight(){
		int nrFighters = party.getSize() + enemy.getSize();
		while(true){
			for (int i = 0; i < nrFighters; i++){
				
				if (i % 2 == 0){
					performTurn(party.getMember(i));
				}
				else{
					performAITurn(enemy.getMember(i-1));
				}
				if (party.isEmpty()){
					return false;
				}
				if (enemy.isEmpty()){
					return true;
				}
				nrFighters = party.getSize() + enemy.getSize();
			}
		}
	}
	
	/**
	 * Method for performing one player controlled character's turn in combat
	 * @param member The Member character who's turn it is
	 */
	private void performTurn(Member member){
		double[] attackStat = new double[2];
		do{
			attackStat = chooseAttack();			
		} while (!member.decreasePower(attackStat[1]));
		Member target = chooseTarget();
		double damageDone = target.decreaseHealth(attackStat[0], member.getDamageType());
		// Print damage done to screen, somewhere
		if (!target.isAlive()){
			enemy.removeMember(target);
		}
	}
	
	/**
	 * Method for performing one AI controlled character's turn in combat
	 * @param member The Member character who's turn it is
	 */
	private void performAITurn(Member member){
		double[] attackStat = new double[2];
		attackStat = aiChooseAttack();
		Member target = aiChooseTarget();
		double damageDone = target.decreaseHealth(attackStat[0], member.getDamageType());
		// Print damage done to screen, somewhere
		if (!target.isAlive()){
			party.removeMember(target);
		}
	}
	
	/**
	 * Method for allowing player controlled characters to choose a target for attack
	 * @return The chosen target for the next attack
	 */
	private Member chooseTarget(){
		//Not done
		Member target = enemy.getMember(0);
		return  target;
	}
	
	/**
	 * Method for allowing player controlled characters to choose which attack to use next
	 * @return The damage and power consumption of chosen attack
	 */
	private double[] chooseAttack(){
		//Not done
		double [] returnValue = {1,2};
		return returnValue;
	}
	
	/**
	 * Method for allowing AI controlled characters to choose a target for attack
	 * @return The chosen target for the next attack
	 */
	private Member aiChooseTarget(){
		Member target = party.getMember(0);
		for (int i = 0; i < party.getSize(); i++){
			if (party.getMember(i).getHealth() < target.getHealth()){
				target = party.getMember(i);
			}
		}
		return target;
	}
	
	/**
	 * Method for allowing AI controlled characters to choose which attack to use next
	 * @return The damage and power consumption of chosen attack
	 */
	private double[] aiChooseAttack(){
		//Not done
		double [] returnValue = {1,2};
		return returnValue;
	}
}
