/**
 * 
 */
package org.fc.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;

import org.fc.entity.Catch;
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

	public static String CONTEST_DATE = "24.09.2011";
	public static final String FISH_TYPE = "Pd";
	public static final String SECTOR = "A";
	public static final int ROUND = 1;
	
	private int NUM_ROUNDS;
	
	private Long dataSeq = 1L;
	
	private ArrayList<Team> teams = new ArrayList<Team>();
	
	private ArrayList<ArrayList<Catch>> roundCatch;
	
	private ArrayList<Result> results = new ArrayList<Result>();
	
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
		
		
		XMLStreamWriter xmlw = 
		         xmlof.createXMLStreamWriter(new FileOutputStream(fileName));
		
		
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
	
	
	public void roundResultsCalculation(int round) {
		
		// first delete the old round result data
		if (results != null) {
			for (Result result: results) {
				if (result.getRound() == round) {
					results.remove(result);
				}
			}
		}
		else {
			results = new ArrayList<Result>();
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

		// final order number assignments
		for (String sector: new String[] {"A", "B", "H"}) {

			int order = 1;

			for (int i = 0; i < results.size(); i++) {
				Result res = results.get(i);
				if (res.getRound() == round && res.getSector().equals(sector)) {
					res.setOrder(order);
					
					if (res.getAmount() > 0) {
						res.setOrderPoints(order);
					}
					else {
						res.setOrderPoints(teams.size() / NUM_ROUNDS);
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
	
	
}