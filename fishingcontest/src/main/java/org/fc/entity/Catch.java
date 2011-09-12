/**
 * 
 */
package org.fc.entity;

/**
 * @author pskopek
 *
 */
public class Catch {
	
	private Long id;
	
	private Team team;
	private int round;
	
	private String sector;

	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
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
	
	public String toString() {
		return "[" + id + ": R=" + getRound() + ", S=" + getSector() + "]";
	}

}
