/**
 * 
 */
package org.fc.entity;

import java.util.ArrayList;

import org.fc.data.Contest;

/**
 * @author pskopek
 *
 */
public class Team {
	
	private Long id;
	
	private String name;
	private String organisation;
	
	private ArrayList<Round> rounds;
	
	public Team() {
		id = Contest.getNextDataSeq();
		rounds = new ArrayList<Round>(Contest.getContest().getNumRounds());
	}
	
	public Team(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	
	public String toString() {
		
		StringBuilder sbRounds = new StringBuilder("{");
		for (Round r : rounds)
			sbRounds.append(r);
		sbRounds.append("}");		
		
		return "[" + id + ":" + getName() + " - " + getOrganisation() + ":" + sbRounds + "]";
	}

	public ArrayList<Round> getRounds() {
		return rounds;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

}
