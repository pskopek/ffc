/**
 * 
 */
package org.fc.entity;

/**
 * @author pskopek
 *
 */
public class FinalResult {
	
	private int order;
	private long teamId;
	private String name;
	private String organisation;
	
	private String r1loc = null;
	private Integer r1cips;
	private Integer r1amount;
	private Integer r1max;
	private Integer r1orderPoints;
	
	private String r2loc = null;
	private Integer r2cips;
	private Integer r2amount;
	private Integer r2max;
	private Integer r2orderPoints;

	private String r3loc = null;
	private Integer r3cips;
	private Integer r3amount;
	private Integer r3max;
	private Integer r3orderPoints;

	private String r4loc = null;
	private Integer r4cips;
	private Integer r4amount;
	private Integer r4max;
	private Integer r4orderPoints;

	private int orderPoints;
	private int cips;
	private int amount;
	private int max;
	
	
	private int nice(Integer number) {
		return (number != null ? number.intValue() : 0);
	}
	
	public void calculateSummary() {
		orderPoints = nice(r1orderPoints) + nice(r2orderPoints) + nice(r3orderPoints) + nice(r4orderPoints);
		cips = nice(r1cips) + nice(r2cips) + nice(r3cips) + nice(r4cips);
		amount = nice(r1amount) + nice(r2amount) + nice(r3amount) + nice(r4amount);
		max = Math.max(Math.max(nice(r1max), nice(r2max)), Math.max(nice(r3max), nice(r4max)));
	}

	public String toString() {
		return order + ":" + name + ":"; 
	}

	public int getOrder() {
		return order;
	}


	public void setOrder(int order) {
		this.order = order;
	}


	public long getTeamId() {
		return teamId;
	}


	public void setTeamId(long teamId) {
		this.teamId = teamId;
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


	public String getR1loc() {
		return r1loc;
	}

	public int getOrderPoints() {
		return orderPoints;
	}


	public void setOrderPoints(int orderPoints) {
		this.orderPoints = orderPoints;
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

	public Integer getR1cips() {
		return r1cips;
	}

	public void setR1cips(Integer r1cips) {
		this.r1cips = r1cips;
	}

	public Integer getR1amount() {
		return r1amount;
	}

	public void setR1amount(Integer r1amount) {
		this.r1amount = r1amount;
	}

	public Integer getR1max() {
		return r1max;
	}

	public void setR1max(Integer r1max) {
		this.r1max = r1max;
	}

	public Integer getR1orderPoints() {
		return r1orderPoints;
	}

	public void setR1orderPoints(Integer r1orderPoints) {
		this.r1orderPoints = r1orderPoints;
	}

	public String getR2loc() {
		return r2loc;
	}

	public void setR2loc(String r2loc) {
		this.r2loc = r2loc;
	}

	public Integer getR2cips() {
		return r2cips;
	}

	public void setR2cips(Integer r2cips) {
		this.r2cips = r2cips;
	}

	public Integer getR2amount() {
		return r2amount;
	}

	public void setR2amount(Integer r2amount) {
		this.r2amount = r2amount;
	}

	public Integer getR2max() {
		return r2max;
	}

	public void setR2max(Integer r2max) {
		this.r2max = r2max;
	}

	public Integer getR2orderPoints() {
		return r2orderPoints;
	}

	public void setR2orderPoints(Integer r2orderPoints) {
		this.r2orderPoints = r2orderPoints;
	}

	public String getR3loc() {
		return r3loc;
	}

	public void setR3loc(String r3loc) {
		this.r3loc = r3loc;
	}

	public Integer getR3cips() {
		return r3cips;
	}

	public void setR3cips(Integer r3cips) {
		this.r3cips = r3cips;
	}

	public Integer getR3amount() {
		return r3amount;
	}

	public void setR3amount(Integer r3amount) {
		this.r3amount = r3amount;
	}

	public Integer getR3max() {
		return r3max;
	}

	public void setR3max(Integer r3max) {
		this.r3max = r3max;
	}

	public Integer getR3orderPoints() {
		return r3orderPoints;
	}

	public void setR3orderPoints(Integer r3orderPoints) {
		this.r3orderPoints = r3orderPoints;
	}

	public String getR4loc() {
		return r4loc;
	}

	public void setR4loc(String r4loc) {
		this.r4loc = r4loc;
	}

	public Integer getR4cips() {
		return r4cips;
	}

	public void setR4cips(Integer r4cips) {
		this.r4cips = r4cips;
	}

	public Integer getR4amount() {
		return r4amount;
	}

	public void setR4amount(Integer r4amount) {
		this.r4amount = r4amount;
	}

	public Integer getR4max() {
		return r4max;
	}

	public void setR4max(Integer r4max) {
		this.r4max = r4max;
	}

	public Integer getR4orderPoints() {
		return r4orderPoints;
	}

	public void setR4orderPoints(Integer r4orderPoints) {
		this.r4orderPoints = r4orderPoints;
	}

	public void setR1loc(String r1loc) {
		this.r1loc = r1loc;
	}

}
