/**
 * 
 */
package org.fc.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;

import org.fc.entity.Catch;
import org.fc.entity.FinalResult;
import org.fc.entity.Gain;
import org.fc.entity.Result;
import org.fc.entity.Round;
import org.fc.entity.Team;
import org.fc.entity.report.Boats;

/**
 * @author pskopek
 *
 */
public class Contest {

	public static final String CONTEST_DATE = "12.10.2013";
	public static final String YEAR = CONTEST_DATE.substring(CONTEST_DATE.lastIndexOf(".") + 1);  // for reporting
	public static final String FISH_TYPE = "Pd";
	public static final String SECTOR = "A";
	public static final int ROUND = 1;
	public static final int NUM_TRIES = 30;
	public static final int MAX_TEAMS = 60;
	public static final int TEAM_MODULUS = 3;

	private int NUM_ROUNDS;
	
	private Long dataSeq = 1L;
	
	private ArrayList<Team> teams = new ArrayList<Team>();
	
	private ArrayList<ArrayList<Catch>> roundCatch;
	
	private ArrayList<Result> results = new ArrayList<Result>();
	
	private ArrayList<FinalResult> finalResults = new ArrayList<FinalResult>();
	
	private static Contest CONTEST = null;
	
	public static Contest getContest() {
		if (CONTEST == null) 
			CONTEST = new Contest();
		return CONTEST;
	}
	
	
	private Contest() {
		
		NUM_ROUNDS = 6;
		
		
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
		
		CONTEST.cleanContest();
		
		CONTEST = new Contest();
	}
	
	private void cleanContest() {

		if (teams != null ) {
			teams.clear();
		}
		
		if (roundCatch != null) {
			for (ArrayList<Catch> cl: roundCatch) {
				cl.clear();
			}
			roundCatch.clear();
		}
		
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
		
		
		XMLStreamWriter xmlw = new com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter(
		         xmlof.createXMLStreamWriter(new FileOutputStream(fileName)));
		
		
		xmlw.writeStartDocument();
		xmlw.writeStartElement("contest");
		
		writeNumRounds(xmlw);
		writeDataSeq(xmlw);
		
		writeTeams(xmlw);
		
		writeCatched(xmlw);
		
		
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
			xmlw.writeAttribute("dummy", Boolean.toString(t.isDummy()));
			xmlw.writeAttribute("disq", Boolean.toString(t.isDisqualified()));
			xmlw.writeAttribute("fee", Integer.toString(t.getFee()));
		
			writeRoundPlan(xmlw, t);
			writeRoundGain(xmlw, t);
			xmlw.writeEndElement();
		}
		
		xmlw.writeEndElement();
	}
	
	private void writeCatched(XMLStreamWriter xmlw) throws XMLStreamException {
		
		xmlw.writeStartElement("catched");
		for (Team t: teams) {
			if (t.getCatched() == null) continue;
			for (Catch c: t.getCatched()) {
				xmlw.writeStartElement("catch");
				xmlw.writeAttribute("id", String.valueOf(c.getId()));
				xmlw.writeAttribute("teamid", String.valueOf(c.getTeamId()));
				xmlw.writeAttribute("round", String.valueOf(c.getRound()));
				xmlw.writeAttribute("sector", c.getSector());
				xmlw.writeAttribute("fishType", c.getFishType());
				xmlw.writeAttribute("length", String.valueOf(c.getLength()));
				xmlw.writeAttribute("cips", String.valueOf(c.getCips()));
				xmlw.writeEndElement();
			}
		}
		xmlw.writeEndElement();
	}

	
	private void writeRoundPlan(XMLStreamWriter xmlw, Team t) throws XMLStreamException {
		xmlw.writeStartElement("plan");
		for (Round r: t.getRoundPlan()) {
			xmlw.writeStartElement("round");
			xmlw.writeAttribute("round", String.valueOf(r.getRound()));
			xmlw.writeAttribute("sector", r.getSector() == null ? "null" : r.getSector());
			xmlw.writeAttribute("role", r.getRole() == null ? "null" : r.getRole());
			xmlw.writeAttribute("location", String.valueOf(r.getLocation()));
			xmlw.writeEndElement();
		}
		xmlw.writeEndElement();
	}

	private void writeRoundGain(XMLStreamWriter xmlw, Team t) throws XMLStreamException {
		xmlw.writeStartElement("gains");
		for (Gain g: t.getRoundGain()) {
			xmlw.writeStartElement("gain");
			xmlw.writeAttribute("fish", String.valueOf(g.fish));
			xmlw.writeAttribute("cips", String.valueOf(g.CIPS));
			xmlw.writeEndElement();
		}
		xmlw.writeEndElement();
	}
	
	public void loadFromFile(String fileName) throws XMLStreamException, FileNotFoundException {
		 
		cleanContest();
		
		FileInputStream fileInputStream = new FileInputStream(fileName);
		XMLStreamReader xmlr = XMLInputFactory.newInstance().createXMLStreamReader(fileInputStream);
	
		String path = "";
		Team lastLoadedTeam = null;
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
					lastLoadedTeam = loadTeams(xmlr);
				}
				else if (path.equals("/contest/teams/team/plan/round")) {
					loadPlan(xmlr, lastLoadedTeam);
				}
				else if (path.equals("/contest/teams/team/gains/gain")) {
					loadGains(xmlr, lastLoadedTeam);
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
				else if (path.equals("/contest/catched/catch")) {
					loadCatch(xmlr);
				}
			
			}
		}
		
		xmlr.close();
		
	}
	
	
	private void loadCatch(XMLStreamReader xmlr) {

		Catch c = new Catch();
		
		for (int i = 0; i < xmlr.getAttributeCount(); i++) {
			String attr = xmlr.getAttributeLocalName(i);
			String val = xmlr.getAttributeValue(i);
			if (attr.equals("id")) {
				c.setId(Long.parseLong(val));
			}
			else if (attr.equals("teamid")) {
				c.setTeamId(Long.parseLong(val));
			}
			else if (attr.equals("fishType")) {
				c.setFishType(val);
			}
			else if (attr.equals("round")) {
				c.setRound(Integer.parseInt(val));
			}
			else if (attr.equals("sector")) {
				c.setSector(val);
			}
			else if (attr.equals("length")) {
				c.setLength(Integer.parseInt(val));
			}
			else if (attr.equals("cips")) {
				c.setCips(Integer.parseInt(val));
			}
		}
		
		
		// put catch to proper team
		Team tc = null;
		for (Team t: teams) {
			if (t.getId().equals(c.getTeamId())) {
				tc = t;
				break;
			}
		}
		
		if (tc != null) {
			tc.getCatched().add(c);
		}
		else {
			RuntimeException e = new RuntimeException("Ulovok " + c + " sa neda zaradit do teamu.");
			throw e;
		}
			
	}
	
	private Team loadTeams(XMLStreamReader xmlr) {
		
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
			else if (attr.equals("dummy")) {
				t.setDummy(Boolean.parseBoolean(val));
			}
			else if (attr.equals("disq")) {
				t.setDisqualified(Boolean.parseBoolean(val));
			}
			else if (attr.equals("fee")) {
				t.setFee(Integer.parseInt(val));
			}
		}
		
		t.getRoundPlan().clear();
		t.getRoundGain().clear();
		teams.add(t);
		
		return t;
	}

	private void loadPlan(XMLStreamReader xmlr, Team t) {
		
		ArrayList<Round> rp = t.getRoundPlan();
		if (rp == null)
			rp = new ArrayList<Round>();
		
		Round r = new Round(rp.size() + 1);
		
		for (int i = 0; i < xmlr.getAttributeCount(); i++) {
			String attr = xmlr.getAttributeLocalName(i);
			String val = xmlr.getAttributeValue(i);
			if (attr.equals("round")) {
				r.setRound(Integer.parseInt(val));
			}
			else if (attr.equals("location")) {
				r.setLocation(Integer.parseInt(val));
			}
			else if (attr.equals("role")) {
				r.setRole(val.equals("null") ? null : val);
			}
			else if (attr.equals("sector")) {
				r.setSector(val.equals("null") ? null : val);
			}
		}
		
		rp.add(r);
		
	}
	
	private void loadGains(XMLStreamReader xmlr, Team t) {
		
		ArrayList<Gain> rg = t.getRoundGain();
		if (rg == null)
			rg = new ArrayList<Gain>();
		
		Gain g = new Gain();
		
		for (int i = 0; i < xmlr.getAttributeCount(); i++) {
			String attr = xmlr.getAttributeLocalName(i);
			String val = xmlr.getAttributeValue(i);
			if (attr.equals("cips")) {
				g.CIPS = Integer.parseInt(val);
			}
			else if (attr.equals("fish")) {
				g.fish = Integer.parseInt(val);
			}
		}
		
		rg.add(g);
		
	}
	
	
	/**
	 * Draw will delete all catch data and scramble all teams.
	 */
	public void draw() throws ContestDrawException {
		
		
		
		if (teams.size() % TEAM_MODULUS != 0) {
			throw new ContestDrawException("Počet súťažiacich musí byť deliteľný číslom " + TEAM_MODULUS
					+ ".\nAktuálny počet je však " + teams.size());
		}
		else if (teams.size() > 60) {
			throw new ContestDrawException("Počet súťažiacich nesmie byť väčší ako " + MAX_TEAMS
					+ ".\nAktuálny počet je však " + teams.size());
		}
		
		int count = NUM_TRIES;
		while (true) {
			count--;
			try {
				singleDraw();
				System.out.println("Vylosovane na " + (NUM_TRIES - count));
				break;
			}
			catch (ContestDrawException e) {
				if (count < 0) {
					throw e;
				}
			}
		}
		
	}
	
	private void singleDraw() throws ContestDrawException {
		System.out.println("singleDraw");
		
		ArrayList<Team> realTeams = new ArrayList<Team>();
		ArrayList<Team> dummyTeams = new ArrayList<Team>();
		
		for (Team t: teams) {
			t.reset();
			if (t.isDummy()) {
				dummyTeams.add(t);
			}
			else {
				realTeams.add(t);
			}
		}
		
		
		Collections.shuffle(realTeams, new Random(System.currentTimeMillis()));
		
		// add dummy teams at the end of the team lists
		realTeams.addAll(dummyTeams);
		
		if (realTeams.size() != teams.size()) {
			throw new ContestDrawException("realTeams.size() != teams.size()");
		}
		
		// merge dummies
		int numDummies = dummyTeams.size();
		if (numDummies > 0) {
			for (int i = 0; i < numDummies / 2; i++) {
				int dummy = realTeams.size() - numDummies + i;
				int real = realTeams.size() - realTeams.size() / (NUM_ROUNDS * 2) - 1 - i; 
				
				System.out.println("swapping " +  dummy + " <-> " + real);
				System.out.println("swapping " +  realTeams.get(dummy) + " <-> " + realTeams.get(real));
				
				Collections.swap(realTeams, dummy, real);
				
			}
		}
		
		setNewPermutationAsTeams(realTeams.toArray(new Team[realTeams.size()]));

		try {
			checkRules();
		}
		catch (Throwable e) {
			throw new ContestDrawException("Kontrola pravidiel zlyhala, vylosovanie nebolo úspešné.", e);
		}

	}
	
	/**
	 * Method to check various rules.
	 */
	private void checkRules() {

		checkSectorsForTeams();
		
	}
	
	/**
	 *  Check each team plan to contain proper sectors
	 */
	private void checkSectorsForTeams() {

		final String checkString = "APARAZBPBRBZ";
		
		for (Team t: teams) {
			
			ArrayList<Round> plan = t.getRoundPlan();
			ArrayList<String> chkSector1 = new ArrayList<String>();
			ArrayList<String> chkSector2 = new ArrayList<String>();
			for (int round = 0; round < NUM_ROUNDS; round++) {
				Round r = plan.get(round);
				chkSector1.add(r.getSector()+r.getRole());
				chkSector2.add(t.getPlannedSector(round)+t.getPlannedRole(round));
			}
			
			Collections.sort(chkSector1);
			Collections.sort(chkSector2);
			
			StringBuilder sb = new StringBuilder();
			for (String s: chkSector1) 
				sb.append(s);
			String chk1 = sb.toString();		

			sb = new StringBuilder();
			for (String s: chkSector2) 
				sb.append(s);
			String chk2 = sb.toString();		
			
			if (!chk1.equals(chk2)) {
				throw new RuntimeException("Oba kontrolne stringy musia byt rovnaké 1:" + chk1 + " 2:"+chk2);
			}
			if (!chk1.equals(checkString)) {
				throw new RuntimeException("Oba kontrolne stringy musia byt rovné " + checkString + "1:" + chk1 + " 2:"+chk2);
			}
				
		}
		
	}
	
	private Team[] generatePermutation(ArrayList<Team> teamList) throws ContestDrawException {

		Collections.shuffle(teamList, new Random(System.currentTimeMillis()));
		
		Team[] teams = new Team[teamList.size()];
		return teamList.toArray(teams);
		
	}
	
	
	private void setNewPermutationAsTeams(Team[] perm) throws ContestDrawException {
		
		teams = new ArrayList<Team>(perm.length);
		
		System.out.println("Before sitting order");
		for (int teamIndex = 0; teamIndex < perm.length; teamIndex++) {
			Team t = perm[teamIndex];
			teams.add(t);
			t.setId((long)teamIndex + 1);
			
			createPlan(t, teamIndex);
			
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
			
			// sector H needs locations too
			//scrambleTeamsOnH(round);
			
		}
		
	}
	
	private void assignBoats(int round, ArrayList<Team> front, ArrayList<Team> rear, ArrayList<Team> referee) throws ContestDrawException {
		System.out.println("assignBoats " + round);
		
		Team[] pf = generatePermutation(front);
		Team[] pr = generatePermutation(rear);
		Team[] re = generatePermutation(referee);
		
		for (int i = 0; i < pf.length; i++) {
			if (pf[i].getOrganisation().equalsIgnoreCase(re[i].getOrganisation())
			    || pr[i].getOrganisation().equalsIgnoreCase(re[i].getOrganisation()) ) {
			
				int j;
				for (j = i + 1; j < pf.length; j++) {
					if (!pf[j].getOrganisation().equalsIgnoreCase(re[j].getOrganisation())
						    && !pr[j].getOrganisation().equalsIgnoreCase(re[j].getOrganisation()) ) {
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
		/*
		int size = front.size(); 
		if (rear.size() != size || referee.size() != size) {
			throw new ContestDrawException("Veľkosť zoznamov pre zasadaci poriadok musí býť rovnaký. \n" +
					"P="+front.size() + ", Z="+rear.size()+ ",R="+referee.size() + "\n" +
				    "Kolo: " + round + ", Sektor: " + sector);
		}
		*/
		
	}
	
	private void scrambleTeamsOnH(int round) {
		
		System.out.println("scrambling on H " + round);
		
		ArrayList<Team> toScramble = new ArrayList<Team>(teams.size() / NUM_ROUNDS);
		
		for (Team t: this.teams) {
			String ts = t.getRoundPlan().get(round).getSector();
			if (ts.equals("H")) {
				toScramble.add(t);
			}
		}
		
		System.out.println("xx shuffling");
		Collections.shuffle(toScramble, new Random(System.currentTimeMillis()));

		int location = 1;
		for (Team t: toScramble) {
			t.getRoundPlan().get(round).setLocation(location++);
		}
		System.out.println("shuffling on H - done");
	}
	
	
	/*
	 
	Group  G0  G1  G2  G3  G4  G5
----------------------------------	
	Round  
	  1.   AP  AZ  AR  BP  BZ  BR               
	  2.   BZ  BP  BR  AZ  AP  AR                     
	  3.   AR  AP  AZ  BR  BP  BZ          
	  4.   BR  BZ  BP  AR  AZ  AP          
	  5.   AZ  AR  AP  BZ  BR  BP           
	  6.   BP  BR  BZ  AP  AR  AZ           

	 */
	
	/* the first letter means sector, the second means role */
	String template[][] = {
			{"AP", "AZ", "AR", "BP", "BZ", "BR"},
			{"BZ", "BP", "BR", "AZ", "AP", "AR"},                     
			{"AR", "AP", "AZ", "BR", "BP", "BZ"},          
		    {"BR", "BZ", "BP", "AR", "AZ", "AP"},          
		    {"AZ", "AR", "AP", "BZ", "BR", "BP"},           
		    {"BP", "BR", "BZ", "AP", "AR", "AZ"},           
		};
	
	
	private void createPlan(Team t, int teamIndex) throws ContestDrawException {

		ArrayList<Round> roundPlan = t.getRoundPlan();
		int group = teamIndex % template[0].length;   // all rounds has to have equal number of groups, e.g. I can take the first one.
		
		for (int roundNumber = 0; roundNumber < NUM_ROUNDS; roundNumber++) {
			Round r = new Round(roundNumber);
			r.setSector(template[roundNumber][group].substring(0,1));
			r.setRole(template[roundNumber][group].substring(1,2));
			roundPlan.set(roundNumber, r);
		}
		
		

	}
	
	
	private Boats findBoat(Iterator<Boats> iter, int round, String sector, int location) {

		Boats b;
		while (iter.hasNext()) {
			b = iter.next();
			if (b.round == round && b.sector.equals(sector) && b.location == location)
				return b;
		}
		
		return null;
	}
	
	
	
	public List<?> reportBoats() {
		
		TreeSet<Boats> selection = new TreeSet<Boats>(new BoatsComparator());
		
		for (Team t : teams) {

			for (Round r: t.getRoundPlan()) {
			
				// we are not putting boys from dam to a boat ;-)
				if (r.getSector().equals("H")) 
					continue;
				
				Boats b = findBoat(selection.iterator(), r.getRound(), r.getSector(), r.getLocation());
				if (b == null) {
					b = new Boats();
					b.round = r.getRound();
					b.sector = r.getSector();
					b.location = r.getLocation();
					selection.add(b);
				}

				if (r.getRole().equals("R"))
					b.referee = t.getName();
				else if (r.getRole().equals("P"))
					b.front = t.getName();
				else if (r.getRole().equals("Z"))
					b.rear = t.getName();

			}	
		}

		return new ArrayList<Object>(selection);
	}
	
	
	public void roundResultsCalculation(int round, boolean cleanResults) {
		
		// first delete the old round result data
		if (cleanResults) {
			if (results != null) {
				results.clear();
			}
			else {
				results = new ArrayList<Result>();
			}
		}

		// calculation itself starts here
		for (Team t: teams) {
			boolean teamCaughtInRound = false;
			for (Catch c: t.getCatched()) {
				if (c.getRound() == round) {
					teamCaughtInRound = true;
					Result tr = findTeamResult(round, c.getTeamId().longValue());
					if (tr != null) {
						tr.setAmount(tr.getAmount() + 1);
						tr.setCips(tr.getCips() + c.getCips());
						if (tr.getMax() < c.getLength()) {
							tr.setMax(c.getLength());
						}
					}
					else {
						tr = new Result();
						tr.setRound(round);
						tr.setSector(c.getSector());
						tr.setTeamId(c.getTeamId());
						tr.setName(t.getName());
						tr.setOrganisation(t.getOrganisation());
						tr.setCips(c.getCips());
						tr.setAmount(1);
						tr.setMax(c.getLength());
						results.add(tr);
					}
					
				}
			}
			if (! teamCaughtInRound) {
				String sector = t.getPlannedSector(round - 1);
				String role = t.getPlannedRole(round - 1);
				
				if (role == null || !role.equals("R")) {
					Result tr = new Result();
					tr.setRound(round);
					tr.setSector(sector);
					tr.setTeamId(t.getId());
					tr.setName(t.getName());
					tr.setOrganisation(t.getOrganisation());
					tr.setCips(0);
					tr.setAmount(0);
					tr.setMax(0);
					results.add(tr);
				}
			}
		}
		

		// it is universal, so we can sort all results (including other rounds if they are there) 
		Collections.sort(results, new RoundResultsComparator());

		Comparator<Result> helperComparator = new RoundResultsComparator();
		
		// final order number assignments
		for (String sector: new String[] {"A", "B", "H"}) {

			int order = 1;
			Result lastResult = results.get(0);  // in case of the same result we have to setOrderPoints to lastOrder (minimal index for all belonging to the group of the same result) 
			int lastOrder = order;
			
			for (int i = 0; i < results.size(); i++) {
				Result res = results.get(i);
				if (res.getRound() == round && res.getSector().equals(sector)) {
					res.setOrder(order);
					
					if (res.getAmount() > 0) {
						
						if (helperComparator.compare(lastResult, res) == 0) {
							res.setOrderPoints(lastOrder);
						}
						else {
							res.setOrderPoints(order);
							lastOrder = order;
							lastResult = res;
						}
					}
					else {
						res.setOrderPoints(teams.size() / NUM_ROUNDS * 2);  // because each team is 2x in the sector as front and rear
					}
					order++;
				}
			}
		}
		
		
	}
	
	public Result findTeamResult(int round, long teamId) {
		
		Result res = null;
		for (Result r: results) {
			if (r.getRound() == round && r.getTeamId() == teamId) {
				res = r;
				break;
			}
		}
		
		return res;
	}
	
	public Team findTeamById(long id) {
		Team res = null;
		for (Team t: teams) {
			if (t.getId().longValue() == id) {
				res = t;
				break;
			}
		}
			
		return res;
	}


	public ArrayList<Result> getResults() {
		return results;
	}
	
	
	public void finalResultsCalculation(boolean registratedOnly) {
		
		finalResults.clear();
		results.clear();
		
		for (int round = 1; round <= NUM_ROUNDS; round++ ) {
			roundResultsCalculation(round, false);
		}

		for (Team t: teams) {
			
			FinalResult fr = new FinalResult();
			fr.setTeamId(t.getId());
			fr.setName(t.getName());
			fr.setOrganisation(t.getOrganisation());
			finalResults.add(fr);
			
			int checkPoint = 0;
			for (Result r: results) {
				if (r.getTeamId() == t.getId().longValue()) {
					if (r.getRound() == 1) {
						checkPoint++;
						fr.setR1loc(r.getSector());
						fr.setR1cips(r.getCips());
						fr.setR1amount(r.getAmount());
						fr.setR1max(r.getMax());
						fr.setR1orderPoints(r.getOrderPoints());
					}
					else if (r.getRound() == 2) {
						checkPoint++;
						fr.setR2loc(r.getSector());
						fr.setR2cips(r.getCips());
						fr.setR2amount(r.getAmount());
						fr.setR2max(r.getMax());
						fr.setR2orderPoints(r.getOrderPoints());
					}
					else if (r.getRound() == 3) {
						checkPoint++;
						fr.setR3loc(r.getSector());
						fr.setR3cips(r.getCips());
						fr.setR3amount(r.getAmount());
						fr.setR3max(r.getMax());
						fr.setR3orderPoints(r.getOrderPoints());
					}
					else if (r.getRound() == 4) {
						checkPoint++;
						fr.setR4loc(r.getSector());
						fr.setR4cips(r.getCips());
						fr.setR4amount(r.getAmount());
						fr.setR4max(r.getMax());
						fr.setR4orderPoints(r.getOrderPoints());
					}
					else if (r.getRound() == 5) {
						checkPoint++;
						fr.setR5loc(r.getSector());
						fr.setR5cips(r.getCips());
						fr.setR5amount(r.getAmount());
						fr.setR5max(r.getMax());
						fr.setR5orderPoints(r.getOrderPoints());
					}
					else if (r.getRound() == 6) {
						checkPoint++;
						fr.setR6loc(r.getSector());
						fr.setR6cips(r.getCips());
						fr.setR6amount(r.getAmount());
						fr.setR6max(r.getMax());
						fr.setR6orderPoints(r.getOrderPoints());
					}
				}
			}
			
			fr.calculateSummary();

			if (checkPoint > NUM_ROUNDS - 1) {
				// something is very wrong
				throw new RuntimeException("CheckPoint failed on team " + t + ". More that " + (NUM_ROUNDS - 1) + " in round results.");
			}
			
			
		}

		/*
		System.out.println("Before sort:");
		for (FinalResult fr: finalResults)
			System.out.println(fr);
		*/

		// delete dummies from final results
		// and possible non-registered guys (official Slovak LRU mucha competitor)
		ArrayList<FinalResult> deleted = new ArrayList<FinalResult>();
		for (FinalResult fr: finalResults) {
			long teamId = fr.getTeamId();
			Team t = findTeamById(teamId);
			if (t.isDummy()) {
				deleted.add(fr);
			}
			if (registratedOnly) {
				for (Integer non: NonRegistered.nonRegisteredList) {
					long nr = non.longValue();
					if (nr == teamId) {
						deleted.add(fr);
					}
				}
			}
		}
		finalResults.removeAll(deleted);
		
		// sort final results
		Collections.sort(finalResults, new FinalResultsComparator());
		
		int order = 1;
		for (FinalResult fr: finalResults) {
			fr.setOrder(order++);
		}
		
	}


	public ArrayList<FinalResult> getFinalResults() {
		return finalResults;
	}
	
}