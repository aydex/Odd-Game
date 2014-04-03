package combat;

import party.FriendlyParty;
import party.EnemyParty;
import party.Member;

public class Combat {
	
	public enum Attack {HEAVY,STANDRAD,SIMPLE};
	
	protected FriendlyParty party;
	protected EnemyParty enemy;
	private Member target;
	private Attack attack;
	private Member currentPlayer;
	private boolean playerTurn;
	
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
	 * Method for performing one player controlled character's turn in combat
	 * @param member The Member character who's turn it is
	 */
	protected void performTurn(Member member){
		target = null;
		attack = null;
		currentPlayer = member;
		playerTurn = true;
		while(playerTurn){};
		if (!target.isAlive()){
			enemy.removeMember(target);
		}
	}
	
	/**
	 * Method for performing one AI controlled character's turn in combat
	 * @param member The Member character who's turn it is
	 */
	protected void performAITurn(Member member){
		double[] attackStat = new double[2];
		attackStat = aiChooseAttack();
		Member target = aiChooseTarget();
		target.decreaseHealth(attackStat[0], member.getDamageType());
		if (!target.isAlive()){
			party.removeMember(target);
		}
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
	
	/**
	 * Performs attack when attack button is pressed in combat graphics, target and attack must be set
	 */
	protected void performAttack(){
		if (target != null && attack != null){
			double[] attackStat;
			if (attack == Attack.HEAVY){
				attackStat = currentPlayer.getHeavyAttackStats();
			}
			else if (attack == Attack.STANDRAD){
				attackStat = currentPlayer.getStandardAttackStats();
			}
			else{
				attackStat = currentPlayer.getSimpleAttackStats();
			}
			playerTurn = false;
			target.decreaseHealth(attackStat[0], currentPlayer.getDamageType());
		}
	}
	
	/**
	 * Surrenders the battle, called when surrender button is clicked in combat graphics, currently not usable
	 */
	protected void surrender(){
		
	}
	
	/**
	 * Sets the current player's target to enemy0, called from button in combat graphics
	 */
	protected void setTargetEnemy0(){
		if (enemy.getMember(0).isAlive()){
			target = enemy.getMember(0);			
		}
	}
	
	/**
	 * Sets the current player's target to enemy1, called from button in combat graphics
	 */
	protected void setTargetEnemy1(){
		if (enemy.getMember(1).isAlive()){
			target = enemy.getMember(1);			
		}
	}
	
	/**
	 * Sets the current player's target to enemy2, called from button in combat graphics
	 */
	protected void setTargetEnemy2(){
		if (enemy.getMember(2).isAlive()){
			target = enemy.getMember(2);			
		}
	}
	
	/**
	 * Sets the current player's target to enemy3, called from button in combat graphics
	 */
	protected void setTargetEnemy3(){
		if (enemy.getMember(3).isAlive()){
			target = enemy.getMember(3);			
		}
	}
	
	/**
	 * Sets the current player's attack type to heavy
	 */
	protected void setAttackHeavy(){
		double[] attackStat = currentPlayer.getHeavyAttackStats();
		if (attackStat[1] < currentPlayer.getPower()){
			attack = Attack.HEAVY;			
		}
	}
	
	/**
	 * Sets the current player's attack type to standard
	 */
	protected void setAttackStandard(){
		double[] attackStat = currentPlayer.getStandardAttackStats();
		if (attackStat[1] < currentPlayer.getPower()){
			attack = Attack.STANDRAD;			
		}
	}
	
	/**
	 * Sets the current player's attack type to simple
	 */
	protected void setAttackSimple(){
		double[] attackStat = currentPlayer.getSimpleAttackStats();
		if (attackStat[1] < currentPlayer.getPower()){
			attack = Attack.SIMPLE;			
		}		
	}

}
