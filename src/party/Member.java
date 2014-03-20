package party;

import java.util.ArrayList;

import equipment.Armor;
import equipment.Armor.ArmorType;
import equipment.Equipment;
import equipment.Weapon;
import equipment.Weapon.DamageType;

public class Member {
	
	private String name;
	private int level;
	private double defenceMod;
	private double defence;
	private double damageMod;
	private double damage;
	private double maxHealth;
	private double maxPower;
	private double health;
	private double power;
	private double modIncrease;
	private double statIncrease;
	private Weapon weapon;
	private Armor headGear;
	private Armor chest;
	private Armor hands;
	private Armor shield;
	private Armor boots;
	private DamageType weaknessType;
	public enum MemberType { HUMAN, ROBOT, SUPERROBOT, ZOMBIE, HERO}
	
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
	 * decreases member's health dependent on the member's weakness and attacktype, and returns damage taken
	 * @param damage the damage done to the member
	 * @param type the type of damage the attack did
	 * @return the damage taken by the member
	 */
	public double decreaseHealth(double damage, DamageType type){
		if(damage<0){
			throw new IllegalArgumentException("damage cannot be negative, was " + damage);
		}
		double damageDone;
		if(type!=weaknessType){
			if(damage<this.defence){
				damageDone = damage/2;
				health -= damageDone;
			}
			else{
				damageDone = defence/2;
				damageDone += damage-defence;
				health -= damageDone;
			}
		}
		else{
			damageDone = damage;
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
		Armor returnArmor;
		if(armor.getType() == ArmorType.HEADGEAR){
			returnArmor = this.headGear;
			this.headGear = armor;
		}
		else if(armor.getType() == ArmorType.BOOTS){
			returnArmor = this.boots;
			this.boots = armor;
		}
		else if(armor.getType() == ArmorType.CHEST){
			returnArmor = this.chest;
			this.chest = armor;
		}
		else if(armor.getType() == ArmorType.HANDS){
			returnArmor = this.hands;
			this.hands = armor;
		}
		else if(armor.getType() == ArmorType.SHIELD){
			returnArmor = this.shield;
			this.shield = armor;
		}
		else{
			throw new IllegalArgumentException("The armor must be of recognizable type");
		}
		this.updateDamDef();
		return returnArmor;
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
		this.weapon = new Weapon(this.level);
		this.headGear = new Armor(ArmorType.HEADGEAR, this.level);
		this.chest = new Armor(ArmorType.CHEST, this.level);
		this.hands = new Armor(ArmorType.HANDS, this.level);
		this.shield = new Armor(ArmorType.SHIELD, this.level);
		this.boots = new Armor(ArmorType.BOOTS, this.level);
	}

	/**
	 * the constructor for member, it gives it the appropriate level and gives equipment and stats based on that
	 * @param type the type of member
	 * @param level the level the member is going to have
	 */
	public Member(MemberType type, int level){
		switch(type){
		case HERO:
			weaknessType = null;
			modIncrease = 0.8;
			statIncrease = 8;
			this.defenceMod = 2;
			this.damageMod = 2;
			this.maxHealth = 20;
			this.maxPower = 20;
			break;
		case HUMAN:
			name = "Human";
			weaknessType = DamageType.REGULAR;
			modIncrease = 0.5;
			statIncrease = 5;
			this.defenceMod = 1;
			this.damageMod = 1;
			this.maxHealth = 10;
			this.maxPower = 10;
			break;
		case ROBOT:
			name = "Robot";
			weaknessType = DamageType.PLASMA;
			modIncrease = 0.3;
			statIncrease = 3;
			this.defenceMod = 0.5;
			this.damageMod = 0.5;
			this.maxHealth = 15;
			this.maxPower = 15;
			break;
		case SUPERROBOT:
			name = "Super-robot";
			weaknessType = DamageType.PLASMA;
			modIncrease = 1;
			statIncrease = 10;
			this.defenceMod = 1;
			this.damageMod = 1;
			this.maxHealth = 50;
			this.maxPower = 75;
			break;
		case ZOMBIE:
			name = "Zombie";
			weaknessType = DamageType.LASER;
			modIncrease = 0.2;
			statIncrease = 7;
			this.defenceMod = 0.5;
			this.damageMod = 0.2;
			this.maxHealth = 30;
			this.maxPower = 0;
			break;
		default:
			throw new IllegalArgumentException("membertype is not recognized");
		}
		
		
		
		this.level = 0;
		
		for(int i = 1 ; i <= level; i++){
			this.increaseLevel();
		}
		this.createEquipment();
		this.updateDamDef();
	}

	
	/**
	 * returns name of member
	 * @return the name of member
	 */
	public String getName(){
		return name;
	}
}