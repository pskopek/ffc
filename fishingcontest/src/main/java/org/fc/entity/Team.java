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
	
	// plan of team role in each round
	private ArrayList<Round> roundPlan;
	// team CIPS gain in each round
	private ArrayList<Gain> roundGain;
	
	public Team() {
		id = Contest.getContest().getNextDataSeq();
		roundPlan = new ArrayList<Round>(Contest.getContest().getNumRounds());
		roundGain = new ArrayList<Gain>(Contest.getContest().getNumRounds());
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
		for (Round r : roundPlan)
			sbRounds.append(r);
		sbRounds.append("}");		
		
		return "[" + id + ":" + getName() + " - " + getOrganisation() + ":" + sbRounds + "]";
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<Round> getRoundPlan() {
		return roundPlan;
	}

	public ArrayList<Gain> getRoundGain() {
		return roundGain;
	}

	

}
