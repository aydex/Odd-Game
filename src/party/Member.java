package party;

import java.util.ArrayList;

import equipment.Armor;
import equipment.Equipment;
import equipment.Weapon;


public class Member {
	
	private int level;
	private double defenceMod;
	private double defence;
	private double damageMod;
	private double damage;
	private double maxHealth;
	private double maxPower;
	private double health;
	private double power;
	private double modIncrease = 0.5;
	private double statIncrease = 5;
	private Weapon weapon;
	private Armor headGear;
	private Armor chest;
	private Armor hands;
	private Armor shield;
	private Armor boots;
	
	/**
	 * returns the level of member
	 * @return level of member
	 */
	public int getLevel(){
		return level;
	}
	
	/**
	 * Increases stats and mods for member when leveling
	 */
	public void increaseLevel(){
		this.level ++;
		damageMod += modIncrease;
		defenceMod += modIncrease;
		maxHealth += statIncrease;
		maxPower += statIncrease;
		health = maxHealth;
		power = maxPower;
		this.updateDamDef();
	}
	
	/**
	 * decreases member's health, and returns damage taken
	 * @param damage the damage done to the member
	 * @return the damage taken by the member
	 */
	public double decreaseHealth(double damage){
		if(damage<0){
			throw new IllegalArgumentException("damage cannot be negative, was " + damage);
		}
		double damageDone;
		if(damage<this.defence){
			damageDone = damage/2;
			health -= damageDone;
		}
		else{
			damageDone = defence/2;
			damageDone += damage-defence;
			health -= damageDone;
		}
		return damageDone;
	}
	
	/**
	 * decreases member's power if the powerDemand is manageable, and return whether it did it
	 * @param powerDemand the demanded power
	 * @return whether the demanded power could be met
	 */
	public boolean decreasePower(double powerDemand){
		if(powerDemand<0){
			throw new IllegalArgumentException("powerDemand cannot be negative, was " + powerDemand);
		}
		if(this.power < powerDemand){
			return false;
		}
		else{
			this.power -= powerDemand;
			return true;
		}
	}
	
	/**
	 * returns whether the member is alive
	 * @return whether member is alive
	 */
	public boolean isAlive(){
		if(health<=0){
			health = 0;
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * updates damage and defence by modifiers and equipment
	 */
	private void updateDamDef(){
		this.damage = damageMod + weapon.getStat();
		this.defence = defenceMod + headGear.getStat() + boots.getStat() + chest.getStat() + hands.getStat() + shield.getStat();
	}

	/**
	 * return the damage the member can do
	 * @return damage from user
	 */
	public double getDamage(){
		return damage;
	}

	/**
	 * return the power of the member
	 * @return the power of the member
	 */
	public double getPower(){
		return power;
	}

	/**
	 * returns the member's equipment in the form of a list
	 * @return list of member's equipment
	 */
	public ArrayList<Equipment> getEquipment(){
		ArrayList<Equipment> equipmentList = new ArrayList<Equipment>();
		equipmentList.add(weapon);
		equipmentList.add(headGear);
		equipmentList.add(boots);
		equipmentList.add(chest);
		equipmentList.add(hands);
		equipmentList.add(shield);
		return equipmentList;
	}

	/**
	 * changes the armorpiece, updates the stats and return the old armorpiece
	 * @param armor the armorpiece the member is going to equip
	 * @return return the old armorpiece
	 */
	public Equipment changeEquipment(Armor armor){
		if(armor.getType() == "headGear"){
			Armor returnArmor = this.headGear;
			this.headGear = armor;
		}
		else if(armor.getType() == "boots"){
			Armor returnArmor = this.boots;
			this.boots = armor;
		}
		else if(armor.getType() == "chest"){
			Armor returnArmor = this.chest;
			this.chest = armor;
		}
		else if(armor.getType() == "hands"){
			Armor returnArmor = this.hands;
			this.hands = armor;
		}
		else if(armor.getType() == "shield"){
			Armor returnArmor = this.shield;
			this.shield = armor;
		}
		else{
			throw new IllegalArgumentException("The armor must be of recognizable type");
		}
		this.updateDamDef();
	}

	/**
	 * changes the weapon of the member, updates stats, and return the old weapon
	 * @param weapon the weapon the member is going to equip
	 * @return the old weapon
	 */
	public Equipment changeEquipment(Weapon weapon){
		Weapon returnWeapon = this.weapon;
		this.weapon = weapon;
		this.updateDamDef();
		return returnWeapon;
	}

	/**
	 * Creates all the equipment the member is going to have
	 */
	private void createEquipment(){
		this.weapon = new Weapon(level);
		this.headGear = new Armor("headGear", level);
		this.chest = new Armor("chest", level);
		this.hands = new Armor("hands", level);
		this.shield = new Armor("shield", level);
		this.boots = new Armor("boots", level);
	}

	/**
	 * the constructor for member, it gives it the appropriate level and gives equipment and stats based on that
	 * @param level the level the member is going to have
	 */
	public Member(int level){
		this.level = 0;
		this.defenceMod = 1;
		this.damageMod = 1;
		this.maxHealth = 10;
		this.maxPower = 10;
		for(int i = 1 ; i <= level; i++){
			this.increaseLevel();
		}
		this.createEquipment();
		this.updateDamDef();
	}
}
