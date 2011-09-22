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
	
	private Long teamId;
	private int round;
	private String sector;
	
	private String fishType;
	private int fish;
	private int length;
	private int cips;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return "[" + id + ": R=" + getRound() + ", S=" + getSector() + "-" + fish + "," + length + "," + cips + "]";
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public String getFishType() {
		return fishType;
	}

	public void setFishType(String fishType) {
		this.fishType = fishType;
	}

	public int getFish() {
		return fish;
	}

	public void setFish(int fish) {
		this.fish = fish;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getCips() {
		return cips;
	}

	public void setCips(int cips) {
		this.cips = cips;
	}

}
