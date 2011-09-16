/**
 * 
 */
package org.fc.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;

import org.fc.entity.Catch;
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
		
		CONTEST.teams.clear();
		
		for (ArrayList<Catch> cl: CONTEST.roundCatch) {
		  cl.clear();
		}
		CONTEST.roundCatch.clear();
		
		
		CONTEST = new Contest();
	}
	
	/**
	 * Test data creation. Do not use in production ;-)
	 */
	public void createTestData() {
		
		for (int i = 0; i < 16; i++) {
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
	
}
