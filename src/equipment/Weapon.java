package equipment;

import java.io.FileNotFoundException;

import utils.RandomOdd;

public class Weapon extends Equipment {
	
	public enum WeaponType { KNIFE, RIFLE, PISTOL, AUTOMATIC}
	public enum DamageType { REGULAR, LASER, PLASMA }
	
	private int attack;
	private WeaponType weaponType;
	private DamageType damageType;
	
	/**
	 * Generates a specific weapon with given stats
	 * @param name The name of the weapon
	 * @param weaponType The WeaponType of the weapon
	 * @param damageType The DamageType of the weapon
	 * @param attack The attack of the weapon
	 * @param value The value of the weapon
	 */
	public Weapon(String name, WeaponType weaponType, DamageType damageType, int attack, int value){
		super(name);
		if (attack < 0){
			throw new IllegalArgumentException("Weapons cannot have negative attack");
		}
		this.weaponType = weaponType;
		this.damageType = damageType;
		this.attack = attack;
	}
	
	/**
	 * Generates a random weapon of appropriate level
	 * @param level The level of the weapon
	 * @throws FileNotFoundException 
	 */
	public Weapon(int level){
		super("");
		String name = "";
		attack = RandomOdd.getRandomInt(level*(1/2)+1, level);
		try{
			name = RandomOdd.getRandomNameFromFile("WeaponNames.txt");			
		}
		catch (FileNotFoundException f){
			name = "Generic";
		}
		int weapon = RandomOdd.getRandomInt(1,4);
		int damage = RandomOdd.getRandomInt(1,3);
		switch(damage){
		case 1:
			damageType = DamageType.REGULAR;
			break;
		case 2:
			damageType = DamageType.LASER;
			name += " laser ";
			break;
		case 3:
			damageType = DamageType.PLASMA;
			name += " plasma ";
			break;
		}
		switch(weapon){
		case 1:
			weaponType = WeaponType.KNIFE;
			attack += 0;
			name += " knife";
			break;
		case 2:
			weaponType = WeaponType.PISTOL;
			attack += 1;
			name += " pistol";
			break;
		case 3:
			weaponType = WeaponType.RIFLE;
			attack += 3;
			name += " rifle";
			break;
		case 4:
			weaponType = WeaponType.AUTOMATIC;
			attack += 5;
			name += " automatic";
			break;
		}
		setValue(attack*2);
		setName(name);
	}
	
	/**
	 * Returns the attack bonus provided by the weapon
	 * @return The attack bonus of the weapon
	 */
	public int getStat(){
		return attack;
	}
	
	/**
	 * Returns the weapon type of the weapon
	 * @return The WeaponType
	 */
	public WeaponType getWeaponType(){
		return weaponType;
	}
	
	/**
	 * Returns the damage type of the weapon
	 * @return The DamageType
	 */
	public DamageType getDamageType(){
		return damageType;
	}
	
	/**
	 * Returns the attack bonus and power consumption of a simple attack with the given weapon
	 * @return int list with two elements, first element is attack bonus, second element is power consumption
	 */
	public int[] getSimpleAttackMod(){
		int[] returnValue = new int[2];
		switch(weaponType){
		case KNIFE:
			returnValue[0] = (1/2) * getStat() * 1;
			returnValue[1] = 0;
			break;
		case PISTOL:
			returnValue[0] = (1/2) * getStat() * 1;
			returnValue[1] = 0;
			break;
		case RIFLE:
			returnValue[0] = (1/2) * getStat() * 1;
			returnValue[1] = 0;
			break;
		case AUTOMATIC:
			returnValue[0] = (1/2) * getStat() * 1;
			returnValue[1] = 0;
			break;
		}
		switch (damageType){
		case REGULAR:
			returnValue[0] += (1/2) * getStat() * 1;
			returnValue[1] += 0;
			break;
		case LASER:
			returnValue[0] += (1/2) * getStat() * 1;
			returnValue[1] += 2;
			break;
		case PLASMA:
			returnValue[0] += (1/2) * getStat() * 1;
			returnValue[1] += 4;
			break;
		}
		return returnValue;
	}
	
	/**
	 * Returns the attack bonus and power consumption of a standard attack with the given weapon
	 * @return int list with two elements, first element is attack bonus, second element is power consumption
	 */
	public int[] getStandardAttackMod(){
		int[] returnValue = new int[2];
		switch(weaponType){
		case KNIFE:
			returnValue[0] += (1/2) * getStat() * (3/2);
			returnValue[1] += 2;
			break;
		case PISTOL:
			returnValue[0] += (1/2) * getStat() * 2;
			returnValue[1] += 2;
			break;
		case RIFLE:
			returnValue[0] += (1/2) * getStat() * 3;
			returnValue[1] += 2;
			break;
		case AUTOMATIC:
			returnValue[0] += (1/2) * getStat() * 4;
			returnValue[1] += 2;
			break;
		}
		switch (damageType){
		case REGULAR:
			returnValue[0] += (1/2) * getStat() * 1;
			returnValue[1] += 0;
			break;
		case LASER:
			returnValue[0] += (1/2) * getStat() * 1;
			returnValue[1] += 4;
			break;
		case PLASMA:
			returnValue[0] += (1/2) * getStat() * 1;
			returnValue[1] += 8;
			break;
		}
		return returnValue;
	}

	/**
	 * Returns the attack bonus and power consumption of a heavy attack with the given weapon
	 * @return int list with two elements, first element is attack bonus, second element is power consumption
	 */
	public int[] getHeavyAttackMod(){
		int[] returnValue = new int[2];
		switch(weaponType){
		case KNIFE:
			returnValue[0] += (1/2) * getStat() * 2;
			returnValue[1] += 4;
			break;
		case PISTOL:
			returnValue[0] += (1/2) * getStat() * 4;
			returnValue[1] += 4;
			break;
		case RIFLE:
			returnValue[0] += (1/2) * getStat() * 6;
			returnValue[1] += 4;
			break;
		case AUTOMATIC:
			returnValue[0] += (1/2) * getStat() * 8;
			returnValue[1] += 4;
			break;
		}
		switch (damageType){
		case REGULAR:
			returnValue[0] += (1/2) * getStat() * 1;
			returnValue[1] += 0;
			break;
		case LASER:
			returnValue[0] += (1/2) * getStat() * 1;
			returnValue[1] += 8;
			break;
		case PLASMA:
			returnValue[0] += (1/2) * getStat() * 1;
			returnValue[1] += 10;
			break;
		}
		return returnValue;
	}

}
