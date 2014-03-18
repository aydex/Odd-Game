package equipment;

public class Equipment {
	
	/**
	 * The name of the equipment
	 */
	private String name;
	
	/**
	 * Default constructor for equipment, constructs equipment with given name
	 * @param name The name of the equipment to be constructed
	 */
	public Equipment(String name){
		setName(name);
	}
	
	/**
	 * Sets the name of the equipment 
	 * @param name The new name of the equipment
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Returns the name of the equipment
	 * @return The name of the equipment
	 */
	public String getName(){
		return name;
	}

}
