/**
 * 
 */
package org.fc.data;

import java.util.ArrayList;

import org.fc.entity.Catch;
import org.fc.entity.Team;

/**
 * @author pskopek
 *
 */
public class Contest {

	public final int NUM_ROUNDS;
	
	private ArrayList<Team> teams = new ArrayList<Team>();
	
	private ArrayList<ArrayList<Catch>> round;
	
	public Contest() {
		
		NUM_ROUNDS = 4;
		
		round = new ArrayList<ArrayList<Catch>>(NUM_ROUNDS);
		for (int i = 0; i < NUM_ROUNDS; i++) {
			round.set(i, new ArrayList<Catch>()); 
		}
		
	}
	

	public ArrayList<Team> getTeams() {
		return teams;
	}

	public Team getTeamById(Long id) {
		
		for (Team t: teams) {
			if (t.getId().equals(id))
				return t;
		}
		
		return null;
	}
	
	public ArrayList<Catch> getRound(int i) {
		return round.get(i);
	}

	public void addCatch(int r, Catch c) {
		getRound(r).add(c);
	}
	
	
}
