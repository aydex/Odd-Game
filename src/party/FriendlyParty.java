package party;

import java.util.ArrayList;

import javafx.util.Callback;
import equipment.Equipment;

public class FriendlyParty extends Party {
	
	/**
	 * ArrayList containing all equipment currently in the inventory of the party
	 */
	private ArrayList<Equipment> inventory;
	/**
	 * The amount of money currently carried by the party
	 */
	private int money;

	/**
	 * Default constructor for friendly party, consisting of given members, with empty inventory and 0 money
	 * @param members ArrayList of members, max. size 4
	 */
	public FriendlyParty(ArrayList<Member> members) {
		super(members);
		inventory = new ArrayList<Equipment>();
	}
	
	/**
	 * Constructs a party with the given members, and given inventory and money
	 * @param members ArrayList of members, max. size 4
	 * @param inventory ArrayList of equipment
	 * @param money The money carried by the party
	 */
	public FriendlyParty(ArrayList<Member> members, ArrayList<Equipment> inventory, int money) {
		super(members);
		this.inventory = inventory;
		this.money = money;
	}
	
	/**
	 * Adds the given equipment to the party's inventory
	 * @param item The equipment to be added to the inventory
	 */
	public void addItem(Equipment item){
		System.out.println("adding " + item + " to inventory");
		inventory.add(item);
	}
	
	
	
	/**
	 * Returns the equipment at the given index in the inventory
	 * @param index The index of the equipment to be returned
	 * @return The equipment at the given index
	 */
	public Equipment getItem(int index){
		if (index > inventory.size() || index < 0){
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		return inventory.get(index);
	}
	
	/**
	 * Removes the given equipment from the party's inventory
	 * @param item The equipment to be removed
	 */
	public void removeItem(Equipment item){
		System.out.println("removing " + item + " from inventory");
		inventory.remove(item);
	}
	
	/**
	 * Removes equipment at the given index from the party's inventory
	 * @param index The index of the equipment to be removed
	 */
	public void removeItem(int index){
		if (index > inventory.size() || index < 0){
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		inventory.remove(index);
	}
	
	/**
	 * Adds the given amount of money to the party's current money
	 * @param amount The amount to be added
	 */
	public void addMoney(int amount){
		if (amount < 0){
			throw new IllegalArgumentException("Cannot add negative amount");
		}
		money += amount;
	}
	
	/**
	 * Withdraws the given amount from the party's money
	 * @param amount The amount to be withdrawn
	 */
	public void withdrawMoney(int amount){
		if (amount > this.money){
			throw new IllegalArgumentException("Acnnot withdraw more money than currently held by the party");
		}
		money -= amount;
	}
	
	/**
	 * Returns the amount of money currently held by the party
	 * @return The money currently held by the party
	 */
	public int getMoney(){
		return money;
	}

	public ArrayList<Equipment> getInventory() {
		System.out.println("inventory contains" + inventory);
		return inventory;
	}

}
