package party;

import java.util.ArrayList;

public class Party {
	
	/**
	 * ArrayList containing the members of the party
	 */
	private ArrayList<Member> members;
	
	/**
	 * Default constructor for party, constructs empty party
	 */
	public Party(){
		members = new ArrayList<Member>();
	}

	public int getLevel(){
		int returnInt = 0;
		for(int i = 0 ; i < this.getSize(); i ++){
			if(returnInt<this.getMember(i).getLevel()){
				returnInt = this.getMember(i).getLevel();
			}
		}
		return returnInt;
	}
	
	/**
	 * Constructs a party consisting of the embers of the given ArrayList
	 * @param members ArrayList of members, max. size 4
	 */
	public Party(ArrayList<Member> members){
		if (members.size() > 4){
			throw new IllegalArgumentException("Party cannot contain more than 4 members");
		}
		this.members = members;
	}
	
	/**
	 * Adds the given member argument to the party
	 * @param member The member to be added to the party
	 */
	public void addMember(Member member){
		if (members.size() > 4){
			throw new IllegalArgumentException("Party cannot contain more than 4 members");
		}
		members.add(member);
	}
	
	/**
	 * Removes the given member from the party
	 * @param member The member to be removed from the party
	 */
	public void removeMember(Member member){
		members.remove(member);
	}
	
	/**
	 * Removes the member at the given index from the party
	 * @param index The index of the member to be removed
	 */
	public void removeMember(int index){
		if (index >= members.size() || index < 0){
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		members.remove(index);
	}
	
	/**
	 * Returns the member at the given index
	 * @param index The index of the member to be returned, 0-3
	 * @return Member at given index
	 */
	public Member getMember(int index){
		if (index >= members.size() || index < 0){
			throw new IndexOutOfBoundsException("Index out of bounds, was: "+index+" members.size(): "+members.size());
		}
		return members.get(index);
	}
	
	/**
	 * Returns the size of the party
	 * @return Number of members in the party
	 */
	public int getSize(){
		return members.size();
	}
	
	/**
	 * Returns true if the party is empty, else false
	 * @return Whether the party is empty
	 */
	public boolean isEmpty(){
		return members.isEmpty();
	}
	
	
	public boolean isDead(){
		boolean returnValue = true;
		for (int i = 0; i < getSize(); i++){
			if (getMember(i).isAlive()){
				returnValue = false;
			}
		}
		return returnValue;
	}

}
