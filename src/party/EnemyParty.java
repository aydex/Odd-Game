package party;

import java.util.ArrayList;

import party.Member;
import party.Member.MemberType;

public class EnemyParty extends Party {

	/**
	 * Constructs an enemy party using the default constructor of the party class
	 * @param members ArrayList of members, max. size 4
	 */
	public EnemyParty(ArrayList<Member> members) {
		super(members);
	}
	
	/**
	 * Constructs a random enemy party of given size, with max level equal to level
	 * @param size The number of members in the party
	 * @param level The max level of the party to be generated
	 */
	public EnemyParty(int size, int level, MemberType type){
		super();
		if (level < 0){
			throw new IllegalArgumentException("Level cannot be negativ");
		}
		else if (size <= 0 || size > 4){
			throw new IllegalArgumentException("Size cannot be less than 1 or greater than 4");
		}
		for (int i = 0; i < size; i++){
			Member member = new Member(type, level);
			addMember(member);
		}
	}

}
