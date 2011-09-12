/**
 * 
 */
package org.fc.entity;

/**
 * @author pskopek
 *
 */
public class Team {
	
	private Long id;
	
	private String name;
	private String location;
	
	
	public Long getId() {
		return id;
	}
	
	private void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String toString() {
		return "[" + id + ": " + getName() + " - " + getLocation() + "]";
	}
	

}
