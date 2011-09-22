/**
 * 
 */
package org.fc.entity;

/**
 * @author pskopek
 *
 */
public class Result {
	
	private int round;
	private String sector;
	private int order;
	private long teamId;
	private String name;
	private String organisation;
	private int cips;
	private int amount;
	private int max;
	private int orderPoints;
	
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
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCips() {
		return cips;
	}
	public void setCips(int cips) {
		this.cips = cips;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getOrderPoints() {
		return orderPoints;
	}
	public void setOrderPoints(int orderPoints) {
		this.orderPoints = orderPoints;
	}
	public long getTeamId() {
		return teamId;
	}
	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}
	public String getOrganisation() {
		return organisation;
	}
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	
	

}
