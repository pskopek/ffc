/**
 * 
 */
package org.fc.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.management.relation.Role;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;

import org.fc.entity.Catch;
import org.fc.entity.Round;
import org.fc.entity.Team;

/**
 * @author pskopek
 *
 */
public class Contest {

	private int NUM_ROUNDS;
	
	private Long dataSeq = 1L;
	
	private ArrayList<Team> teams = new ArrayList<Team>();
	
	private ArrayList<ArrayList<Catch>> roundCatch;
	
	private static Contest CONTEST = null;
	
	public static Contest getContest() {
		if (CONTEST == null) 
			CONTEST = new Contest();
		return CONTEST;
	}
	
	
	private Contest() {
		
		NUM_ROUNDS = 4;
		
		
		roundCatch = new ArrayList<ArrayList<Catch>>(NUM_ROUNDS);
		for (int i = 0; i < NUM_ROUNDS; i++) {
			roundCatch.add(new ArrayList<Catch>()); 
		}
		
	}
	

	public ArrayList<Team> getTeams() {
		return teams;
	}
	
	public void addTeam(Long id, String name, String organisation) {
		Team t = new Team(id);
		t.setName(name);
		t.setOrganisation(organisation);
		teams.add(t);
	}

	public int getNumRounds() {
		return NUM_ROUNDS;
	}
	
	public Team getTeamById(Long id) {
		
		for (Team t: teams) {
			if (t.getId().equals(id))
				return t;
		}
		
		return null;
	}
	
	public ArrayList<Catch> getRoundCatch(int i) {
		return roundCatch.get(i);
	}

	public void addCatch(int r, Catch c) {
		getRoundCatch(r).add(c);
	}
	
	public Long getNextDataSeq() {
		return dataSeq++;
	}
	
	public static void createNewContest() {
		
		if (CONTEST == null)
			return;
		
		if (CONTEST.teams != null ) {
			CONTEST.teams.clear();
		}
		
		if (CONTEST.roundCatch != null) {
			for (ArrayList<Catch> cl: CONTEST.roundCatch) {
				cl.clear();
			}
			CONTEST.roundCatch.clear();
		}
		
		CONTEST = new Contest();
	}
	
	/**
	 * Test data creation. Do not use in production ;-)
	 */
	public void createTestData() {
		
		for (int i = 0; i < 64; i++) {
			Team t = new Team();
			t.setName("Name " + i);
			t.setOrganisation("Org " + i);
			teams.add(t);
		}
		
	}
	
	public void dumpToFile(String fileName) throws XMLStreamException, FileNotFoundException  {
		
		XMLOutputFactory xmlof = XMLOutputFactory.newInstance();
		
		
		XMLStreamWriter xmlw = 
		         xmlof.createXMLStreamWriter(new FileOutputStream(fileName));
		
		
		xmlw.writeStartDocument();
		xmlw.writeStartElement("contest");
		
		writeNumRounds(xmlw);
		writeDataSeq(xmlw);
		
		writeTeams(xmlw);
		
		
		
		xmlw.writeEndElement();
		xmlw.writeEndDocument();
		
		
		
	}
	
	private void writeDataSeq(XMLStreamWriter xmlw) throws XMLStreamException {
		xmlw.writeStartElement("dataseq");
		xmlw.writeCharacters(dataSeq.toString());
		xmlw.writeEndElement();
	}

	private void writeNumRounds(XMLStreamWriter xmlw) throws XMLStreamException {
		xmlw.writeStartElement("numRounds");
		xmlw.writeCharacters(Integer.toString(NUM_ROUNDS));
		xmlw.writeEndElement();
	}
	
	private void writeTeams(XMLStreamWriter xmlw) throws XMLStreamException {
		xmlw.writeStartElement("teams");
		
		for (Team t: teams) {
			xmlw.writeStartElement("team");
			xmlw.writeAttribute("id", t.getId().toString());
			xmlw.writeAttribute("name", t.getName());
			xmlw.writeAttribute("org", t.getOrganisation());
			xmlw.writeEndElement();
		}
		
		xmlw.writeEndElement();
	}
	
	
	public void loadFromFile(String fileName) throws XMLStreamException, FileNotFoundException {
		 
		FileInputStream fileInputStream = new FileInputStream(fileName);
		XMLStreamReader xmlr = XMLInputFactory.newInstance().createXMLStreamReader(fileInputStream);
	
		String path = "";
		while(xmlr.hasNext()) {
			int event = xmlr.next();
			if (event == XMLEvent.START_ELEMENT) {
				StringBuilder sb = new StringBuilder(path);
				sb.append("/").append(xmlr.getName());
				path = sb.toString();
			}
			else if (event == XMLEvent.END_ELEMENT) {
				path = path.substring(0, path.lastIndexOf("/"));
			} 

			if (event == XMLEvent.START_ELEMENT) {
				if (path.equals("/contest/teams/team")) {
					loadTeams(xmlr);
				}
				else if (path.equals("/contest/dataseq")) {
					dataSeq = Long.parseLong(xmlr.getElementText());
					path = path.substring(0, path.lastIndexOf("/"));
					System.out.println("dataseq="+dataSeq);
				}
				else if (path.equals("/contest/numRounds")) {
					NUM_ROUNDS = Integer.parseInt(xmlr.getElementText());
					path = path.substring(0, path.lastIndexOf("/"));
					System.out.println("NUM_ROUNDS=" + NUM_ROUNDS);
				}
			
			}
		}
		
		xmlr.close();
		
	}
	
	private void loadTeams(XMLStreamReader xmlr) {
		
		Team t = new Team();
		
		for (int i = 0; i < xmlr.getAttributeCount(); i++) {
			String attr = xmlr.getAttributeLocalName(i);
			String val = xmlr.getAttributeValue(i);
			if (attr.equals("id")) {
				t.setId(Long.parseLong(val));
			}
			else if (attr.equals("name")) {
				t.setName(val);
			}
			else if (attr.equals("org")) {
				t.setOrganisation(val);
			}
		}
		
		teams.add(t);
		
	}
	
	
	/**
	 * Draw will delete all catch data and scramble all teams.
	 */
	public void draw() throws ContestDrawException {
		
		if (teams.size() % 8 != 0) {
			throw new ContestDrawException("Počet súťažiacich musí byť deliteľný číslom 8.\nAktuálny počet je však " + teams.size());
		}
		else if (teams.size() < 60) {
			throw new ContestDrawException("Počet súťažiacich musí byť väčší ako 60, inak je pretek nebodovaný.\nAktuálny počet je však " + teams.size());
		}
		else if (teams.size() > 64) {
			throw new ContestDrawException("Počet súťažiacich nesmie byť väčší ako 64.\nAktuálny počet je však " + teams.size());
		}
		
		
		singleDraw();
		
	}
	
	private void singleDraw() throws ContestDrawException {
		
		Team[] perm = generatePermutation(this.teams, 3123, 5000);
		
		checkRules(perm);
		setNewPermutationAsTeams(perm);

		
		
	}
	
	
	private void checkRules(Team[] perm) {
		
	}
	
	
	public Team[] generatePermutation(ArrayList<Team> teamList, long minIterations, long maxIterations) throws ContestDrawException {

		Team[] teams = new Team[teamList.size()];
		teams = teamList.toArray(teams);
		
		long iterations = minIterations + Math.round((Math.random() * maxIterations));
		System.out.println("Drawing using " + iterations + " iterations.");
		
		for (int i = 0; i < iterations; i++) {
			int from = new Long(Math.round(Math.random() * (teams.length - 1))).intValue();
			int to = new Long(Math.round(Math.random() * (teams.length - 1))).intValue();
			
			if (from == to) continue;
			
			// swap
			Team t = teams[from];
			teams[from] = teams[to];
			teams[to] = t;
			
		}
		
		return teams;
	}
	
	
	private void setNewPermutationAsTeams(Team[] perm) throws ContestDrawException {
		
		teams = new ArrayList<Team>(perm.length);
		
		for (int teamIndex = 0; teamIndex < perm.length; teamIndex++) {
			Team t = perm[teamIndex];
			teams.add(t);
			t.setId((long)teamIndex + 1);
			
			for (int roundNumber = 0; roundNumber < NUM_ROUNDS; roundNumber++) {
				createPlan(t, teamIndex, roundNumber);
			}
		
			System.out.println(t.getPlanAsText());
			
		}
		

		createBoatSittingOrder();
		
		
	}
	
	/**
	 * Creates sitting order for each boat.
	 * @throws ContestDrawException
	 */
	private void createBoatSittingOrder() throws ContestDrawException {
		
		for (int round = 0; round < NUM_ROUNDS; round++) {
			for (String sector: new String[] {"A","B"}) {
				ArrayList<Team> front = new ArrayList<Team>();
				ArrayList<Team> rear = new ArrayList<Team>();
				ArrayList<Team> referee = new ArrayList<Team>();

				fillLists(round, sector, front, rear, referee);
				assignBoats(round, front, rear, referee);
			}
		}
		
	}
	
	private void assignBoats(int round, ArrayList<Team> front, ArrayList<Team> rear, ArrayList<Team> referee) throws ContestDrawException {
		
		Team[] pf = generatePermutation(front, 300, 1000);
		Team[] pr = generatePermutation(rear, 300, 1000);
		Team[] re = generatePermutation(referee, 300, 1000);
		
		for (int i = 0; i < pf.length; i++) {
			if (pf[i].getOrganisation().equalsIgnoreCase(re[i].getOrganisation())
			    || pr[i].getOrganisation().equalsIgnoreCase(re[i].getOrganisation()) ) {
			
				int j;
				for (j = i + 1; j < pf.length; j++) {
					if (!pf[j].getOrganisation().equalsIgnoreCase(re[j].getOrganisation())
						    & !pr[j].getOrganisation().equalsIgnoreCase(re[j].getOrganisation()) ) {
						break;
					}
				}
				
				if (j < pf.length) {
					// we have good candidate, so swap
					Team t = re[i];
					re[i] = re[j];
					re[j] = t;
				}
				else {
					throw new ContestDrawException("Rozlosovanie nie je možné, skús znova.");
				}
			}
		}
		
		int boat = 1;
		for (int i = 0; i < pf.length; i++) {
			pf[i].getRoundPlan().get(round).setLocation(boat);
			pr[i].getRoundPlan().get(round).setLocation(boat);
			re[i].getRoundPlan().get(round).setLocation(boat);
			boat++;
		}
		
	}
	
	private void fillLists(int round, String sector, ArrayList<Team> front, ArrayList<Team> rear, ArrayList<Team> referee) throws ContestDrawException {

		for (Team t: this.teams) {

			String role = t.getRoundPlan().get(round).getRole();
			String ts = t.getRoundPlan().get(round).getSector();
			
			if (role != null && role.equals("R") && ts.equals(sector)) 
				referee.add(t);
			else if (ts != null && ts.equals(sector) && role.equals("P"))
				front.add(t);
			else if (ts != null && ts.equals(sector) && role.equals("Z"))
				rear.add(t);
		}
		
		// little check
		int size = front.size(); 
		if (rear.size() != size || referee.size() != size) {
			throw new ContestDrawException("Veľkosť zoznamov pre zasadaci poriadok musí býť rovnaký. \n" +
					"P="+front.size() + ", Z="+rear.size()+ ",R="+referee.size() + "\n" +
				    "Kolo: " + round + ", Sektor: " + sector);
		}
		
		
	}
	
	private void createPlan(Team t, int teamIndex, int roundNumber) throws ContestDrawException {

		ArrayList<Round> roundPlan = t.getRoundPlan();
		
		if (t.getRoundPlan().get(roundNumber) == null) {
			Round r = new Round(roundNumber);
			t.getRoundPlan().add(r);
		}
			
		int order = teamIndex + 1;
		
		
		if (order >= 1 && order <= 16) {
			if (roundNumber == 0) {
				roundPlan.get(roundNumber).setSector("A"); 
				roundPlan.get(roundNumber).setRole((order % 2 == 0 ? "P" : "Z" ));
			} 
			else if (roundNumber == 1) {
				roundPlan.get(roundNumber).setSector("B"); 
				roundPlan.get(roundNumber).setRole((order % 2 == 1 ? "P" : "Z" )); 
			}
			else if (roundNumber == 2) {
				roundPlan.get(roundNumber).setRole("R"); 
				roundPlan.get(roundNumber).setSector((order <= 8 ? "A" : "B" )); 
			}
			else if (roundNumber == 3) {
				roundPlan.get(roundNumber).setSector("H"); 
			}
		}
		else if (order >= 17 && order <= 32) {
			if (roundNumber == 0) {
				roundPlan.get(roundNumber).setSector("B"); 
				roundPlan.get(roundNumber).setRole((order % 2 == 1 ? "P" : "Z" )); 
			} 
			else if (roundNumber == 1) {
				roundPlan.get(roundNumber).setRole("R"); 
				roundPlan.get(roundNumber).setSector((order <= 24 ? "A" : "B" )); 
			}
			else if (roundNumber == 2) {
				roundPlan.get(roundNumber).setSector("H"); 
			}
			else if (roundNumber == 3) {
				roundPlan.get(roundNumber).setSector("A"); 
				roundPlan.get(roundNumber).setRole((order % 2 == 0 ? "P" : "Z" )); 
			}
		}
		else if (order >= 33 && order <= 48) {
			if (roundNumber == 0) {
				roundPlan.get(roundNumber).setRole("R"); 
				roundPlan.get(roundNumber).setSector((order <= 40 ? "A" : "B" )); 
			} 
			else if (roundNumber == 1) {
				roundPlan.get(roundNumber).setSector("H"); 
			}
			else if (roundNumber == 2) {
				roundPlan.get(roundNumber).setSector("A"); 
				roundPlan.get(roundNumber).setRole((order % 2 == 0 ? "P" : "Z" )); 
			}
			else if (roundNumber == 3) {
				roundPlan.get(roundNumber).setSector("B"); 
				roundPlan.get(roundNumber).setRole((order % 2 == 1 ? "P" : "Z" )); 
			}
			
		}
		else if (order >= 49 && order <= 64) {
			if (roundNumber == 0) {
				roundPlan.get(roundNumber).setSector("H"); 
			} 
			else if (roundNumber == 1) {
				roundPlan.get(roundNumber).setSector("A"); 
				roundPlan.get(roundNumber).setRole((order % 2 == 0 ? "P" : "Z" )); 
			}
			else if (roundNumber == 2) {
				roundPlan.get(roundNumber).setSector("B"); 
				roundPlan.get(roundNumber).setRole((order % 2 == 1 ? "P" : "Z" )); 
			}
			else if (roundNumber == 3) {
				roundPlan.get(roundNumber).setRole("R"); 
				roundPlan.get(roundNumber).setSector((order <= 56 ? "A" : "B" )); 
			}
			
		}
		else {
			throw new ContestDrawException("Neočakávaná chyba počas rozlosovania. Počet súťažiacich je viac ako 64.");
		}
		
	}
	
}