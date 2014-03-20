package equipment;

public class Equipment {
	
	private String name;
	private int value;
	
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
	
	/**
	 * Returns the value of the current equipment
	 * @return The value of the equipment
	 */
	public int getValue(){
		return value;
	}
	
	
	public void setValue(int value){
		if (value <= 0){
			throw new IllegalArgumentException("Equipment cannot have negative value");
		}
		this.value = value;
	}

}
