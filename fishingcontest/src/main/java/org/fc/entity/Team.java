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
	
	private boolean dummy = false;
	private boolean disqualified = false;
	private int fee;
	
	// plan of team role in each round
	private ArrayList<Round> roundPlan;
	// team CIPS gain in each round
	private ArrayList<Gain> roundGain;
	
	private ArrayList<Catch> catched;
	
	public Team() {
		id = Contest.getContest().getNextDataSeq();
		roundPlan = new ArrayList<Round>(Contest.getContest().getNumRounds());
		roundGain = new ArrayList<Gain>(Contest.getContest().getNumRounds());
		for (int i = 0; i < Contest.getContest().getNumRounds(); i++) {
			roundPlan.add(new Round(i));
			roundGain.add(new Gain());
		}
		
		catched = new ArrayList<Catch>(20);
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

	public String getPlanAsText() {
		
		StringBuilder sb = new StringBuilder();
		
		for (Round r: roundPlan) {
			
			if (r.getRole() != null && r.getRole().equals("R")) {
				sb.append("R[");
			    sb.append(r.getSector());
			    sb.append("]");
			}
			else if (r.getSector() != null && r.getSector().equals("H")) {
				sb.append(" Hr ");
			}
			else {
				sb.append(r.getSector());
			    sb.append("[");
			    sb.append(r.getRole());
			    sb.append("]");
			}
			
			sb.append("-");
			
		}
		
		sb.delete(sb.length() - 1 , sb.length());
		String ret = sb.toString();
		
		String[] spl = ret.split("null");
		
		if (spl.length > 3)
			return "";
		else
			return ret;
	}

	public ArrayList<Catch> getCatched() {
		return catched;
	}

	public boolean isDisqualified() {
		return disqualified;
	}

	public void setDisqualified(boolean disqualified) {
		this.disqualified = disqualified;
	}

	public void setCatched(ArrayList<Catch> catched) {
		this.catched = catched;
	}

	public boolean isDummy() {
		return dummy;
	}

	public void setDummy(boolean dummy) {
		this.dummy = dummy;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

}
