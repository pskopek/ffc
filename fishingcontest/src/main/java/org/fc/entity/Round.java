/**
 * 
 */
package org.fc.entity;

/**
 * @author pskopek
 *
 */
public class Round {

	private int round;
	private String sector;  // A,B 
	private String role;    // Rozhodca, Prednik, Zadak
	
	private int location; // can be used as boat number or location number in cases of dam sector 

	public Round(int round) {
		this.round = round;
	}
	
	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		return "(" + round + ":" + sector + ":" + role + ":" + location + ")";
	}
	
}
