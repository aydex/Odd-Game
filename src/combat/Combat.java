package combat;

import party.FriendlyParty;
import party.EnemyParty;
import party.Member;

public class Combat {
	
	public enum Attack {HEAVY,STANDRAD,SIMPLE};
	
	private FriendlyParty party;
	private EnemyParty enemy;
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
	 * Returns the friendly party of the combat
	 * @return The combat's party
	 */
	public FriendlyParty getParty(){
		return party;
	}
	
	/**
	 * Returns the enemy party of the combat
	 * @return The combat's enemy
	 */
	public EnemyParty getEnemy(){
		return enemy;
	}
	
	/**
	 * Method for performing one player controlled character's turn in combat
	 * @param member The Member character who's turn it is
	 */
	public void performTurn(Member member){
		target = null;
		attack = null;
		currentPlayer = member;
		playerTurn = true;
	}
	
	/**
	 * Method for performing one AI controlled character's turn in combat
	 * @param member The Member character who's turn it is
	 */
	public void performAITurn(Member member){
		System.out.println("performAITurn");
		currentPlayer = member;
		double[] attackStat = new double[2];
		attackStat = aiChooseAttack();
		Member target = aiChooseTarget();
		System.out.println("Target: "+target.getName());
		currentPlayer.decreasePower(attackStat[1]);
		target.decreaseHealth(attackStat[0], member.getDamageType());
		//if (!target.isAlive()){
		//	party.removeMember(target);
		//}
	}
	
	/**
	 * Method for allowing AI controlled characters to choose a target for attack
	 * @return The chosen target for the next attack
	 */
	private Member aiChooseTarget(){
		Member target = party.getMember(party.getSize()-1);
		for (int i = party.getSize() - 1; i >= 0; i--){
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
		if (currentPlayer.getPower() > currentPlayer.getHeavyAttackStats()[1]){
			return currentPlayer.getHeavyAttackStats();
		}
		else if (currentPlayer.getPower() > currentPlayer.getStandardAttackStats()[1]){
			return currentPlayer.getStandardAttackStats();
		}
		else{
			return currentPlayer.getSimpleAttackStats();
		}
	}
	
	/**
	 * Performs attack when attack button is pressed in combat graphics, target and attack must be set
	 */
	public void performAttack(Member currentPlayer){
		this.currentPlayer = currentPlayer;
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
			currentPlayer.decreasePower(attackStat[1]);
			target.decreaseHealth(attackStat[0], currentPlayer.getDamageType());
			//if (!target.isAlive()){
			//	enemy.removeMember(target);
			//}
		}
	}
	
	/**
	 * Surrenders the battle, called when surrender button is clicked in combat graphics, currently not usable
	 */
	public void surrender(){
		
	}
	
	/**
	 * Sets the current player's target to enemy0, called from button in combat graphics
	 */
	public void setTargetEnemy0(){
		target = null;
		if (enemy.getMember(0).isAlive()){
			target = enemy.getMember(0);			
		}
	}
	
	/**
	 * Sets the current player's target to enemy1, called from button in combat graphics
	 */
	public void setTargetEnemy1(){
		target = null;
		if (enemy.getMember(1).isAlive()){
			target = enemy.getMember(1);			
		}
	}
	
	/**
	 * Sets the current player's target to enemy2, called from button in combat graphics
	 */
	public void setTargetEnemy2(){
		target = null;
		if (enemy.getMember(2).isAlive()){
			target = enemy.getMember(2);			
		}
	}
	
	/**
	 * Sets the current player's target to enemy3, called from button in combat graphics
	 */
	public void setTargetEnemy3(){
		target = null;
		if (enemy.getMember(3).isAlive()){
			target = enemy.getMember(3);			
		}
	}
	
	/**
	 * Sets the current player's attack type to heavy
	 */
	public void setAttackHeavy(Member currentPlayer){
		attack = null;
		double[] attackStat = currentPlayer.getHeavyAttackStats();
		if (attackStat[1] <= currentPlayer.getPower()){
			attack = Attack.HEAVY;			
		}
	}
	
	/**
	 * Sets the current player's attack type to standard
	 */
	public void setAttackStandard(Member currentPlayer){
		attack = null;
		double[] attackStat = currentPlayer.getStandardAttackStats();
		if (attackStat[1] <= currentPlayer.getPower()){
			attack = Attack.STANDRAD;			
		}
	}
	
	/**
	 * Sets the current player's attack type to simple
	 */
	public void setAttackSimple(Member currentPlayer){
		attack = null;
		double[] attackStat = currentPlayer.getSimpleAttackStats();
		if (attackStat[1] <= currentPlayer.getPower()){
			attack = Attack.SIMPLE;			
		}		
	}

}
