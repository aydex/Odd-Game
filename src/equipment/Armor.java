package equipment;

import java.io.FileNotFoundException;

import utils.RandomOdd;

public class Armor extends Equipment {
	
	public enum ArmorType { HEADGEAR, CHEST, BOOTS, HANDS, SHIELD}
	
	private ArmorType type;
	private int defenceBonus;
	
	/**
	 * Generates a specific armor with given name, type, and defense value
	 * @param name The name of the armor 
	 * @param type The type of the armor
	 * @param defence The defense bonus given by the armor
	 * @param value The value of the armor
	 */
	public Armor(String name, ArmorType type, int defence, int value) {
		super(name);
		if (defence < 0){
			throw new IllegalArgumentException("Armor cannot have negative defence");
		}
		this.type = type;
		this.defenceBonus = defence;
		setValue(value);
	}
	
	/**
	 * Generates a random piece of armor with given type and of given level
	 * @param type The type of the armor to be generated
	 * @param level The level of the armor
	 * @throws FileNotFoundException 
	 */
	public Armor(ArmorType type, int level){
		super("");
		String name = "";
		this.type = type;
		defenceBonus = RandomOdd.getRandomInt(level*(1/2), level);
		try{
			name = RandomOdd.getRandomNameFromFile("ArmorNames.txt");			
		}
		catch (FileNotFoundException f) {
			name = "Generic";
		}
		switch(type){
		case HEADGEAR:
			defenceBonus += 2;
			name += " helmet";
			break;
		case CHEST:
			defenceBonus += 4;
			name += " armor";
			break;
		case BOOTS:
			defenceBonus += 3;
			name += " boots";
			break;
		case HANDS:
			defenceBonus += 1;
			name += " gloves";
			break;
		case SHIELD:
			defenceBonus += 3;
			name += " shield";
			break;
		}
		setValue(defenceBonus * 2);
		setName(name);
	}
	
	/**
	 * Returns the ArmorType of the given armor
	 * @return The ArmorType of the armor
	 */
	public ArmorType getType(){
		return type;
	}
	
	/**
	 * Returns the defense bonus provided by the armor
	 * @return The armor's defense bonus
	 */
	public int getStat(){
		return defenceBonus;
	}
	
	public boolean isArmor() {
		return true;
	}
	
	public boolean isWeapon() {
		return false;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}

}
