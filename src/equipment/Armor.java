package equipment;

public class Armor extends Equipment {
	
	public enum ArmorType { HEADGEAR, CHEST, BOOTS, HANDS, SHIELD}
	
	private ArmorType type;
	private int defenceBonus;
	
	/**
	 * Generates a spesific armor with given name, type, and defence value
	 * @param name The name of the armor 
	 * @param type The type of the armor
	 * @param defence The defence bonus given by the armor
	 */
	public Armor(String name, ArmorType type, int defence) {
		super(name);
		if (defence < 0){
			throw new IllegalArgumentException("Armor cannot have negative defence");
		}
		this.type = type;
		this.defenceBonus = defence;
	}
	
	/**
	 * Generates a random piece of armor with given type and of given level
	 * @param type The type of the armor to be generated
	 * @param level The level of the armor
	 */
	public Armor(ArmorType type, int level){
		super("");
		this.type = type;
		String name = getRandomArmorName();
	}
	
	/**
	 * Returns the ArmorType of the given armor
	 * @return The ArmorType of the armor
	 */
	public ArmorType getType(){
		return type;
	}
	
	/**
	 * Returns the defence bonus provided by the armor
	 * @return The armor's defence beonus
	 */
	public int getStat(){
		return defenceBonus;
	}

}
