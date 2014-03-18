package equipment;

public class Weapon extends Equipment {
	
	public enum WeaponType { MELEE, RIFLE, PISTOL, AUTOMATIC}
	public enum DamageType { REGULAR, LASER, PLASMA }
	
	private int attack;
	private WeaponType weaponType;
	private DamageType damageType;
	
	/**
	 * Generates a spesific weapon with given stats
	 * @param name The name of the weapon
	 * @param weaponType The WeaponType of the weapon
	 * @param damageType The DamageType of the weapon
	 * @param attack The attack of the weapon
	 */
	public Weapon(String name, WeaponType weaponType, DamageType damageType, int attack){
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
	 */
	public Weapon(int level){
		super("");
	}
	
	/**
	 * Returns the attack bonus provided by the weapon
	 * @return The attack bonus of the weapon
	 */
	public int getStat(){
		return attack;
	}

}
