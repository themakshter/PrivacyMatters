package models;
import java.util.ArrayList;

public class NatureOfWork {
	private ArrayList<String> purposes, dataSubjects, dataClasses,
			dataDisclosees;

	private String natureOfWork, transfers;

	public NatureOfWork() {
		purposes = new ArrayList<String>();
		dataSubjects = new ArrayList<String>();
		dataClasses = new ArrayList<String>();
		dataDisclosees = new ArrayList<String>();
	}

	public void setNatureOfWork(String nature) {
		natureOfWork = nature.split("-")[1];
	}

	public String getNatureOfWork() {
		return natureOfWork;
	}

	public void setPurposes(ArrayList<String> purposes) {
		this.purposes = purposes;
	}

	public ArrayList<String> getPurposes() {
		return purposes;
	}

	public void setSubjects(ArrayList<String> subjects) {
		this.dataSubjects = subjects;
	}

	public ArrayList<String> getSubjects() {
		return dataSubjects;
	}

	public void setClasses(ArrayList<String> classes) {
		this.dataClasses = classes;
	}

	public ArrayList<String> getClasses() {
		return dataClasses;
	}

	public void setDisclosees(ArrayList<String> disclosees) {
		this.dataDisclosees = disclosees;
	}

	public ArrayList<String> getDisclosees() {
		return dataDisclosees;
	}

	public void setTransfers(String transfers) {
		this.transfers = transfers;
	}

	public String getTransfers() {
		return transfers;
	}

	public String toJSON() {
		String quote = "'";
		String comma = ",";
		String json = "";
		int i = 0;
		String lists;
		// Nature of work
		json += "'natureOfWork' : '" + natureOfWork + quote + comma;

		// Purposes
		i = 0;
		lists = "'purposes' : [{'purpose' : '" + purposes.get(i) + "'}";
		for (i = 1; i < purposes.size(); i++) {
			lists += ",{'purpose' : '" + purposes.get(i) + quote + "}";
		}
		lists += "],";
		json += lists;

		// subjects
		i = 0;
		lists = "'dataSubjects' : [{'dataSubject' : '" + dataSubjects.get(i)
				+ "'}";
		for (i = 1; i < dataSubjects.size(); i++) {
			lists += ",{'dataSubject' : '" + dataSubjects.get(i) + quote + "}";
		}
		lists += "],";
		json += lists;

		// classes
		i = 0;
		lists = "'dataClasses' : [{'dataClass' : '" + dataClasses.get(i) + "'}";
		for (i = 1; i < dataClasses.size(); i++) {
			lists += ",{'dataClass' : '" + dataClasses.get(i) + quote + "}";
		}
		lists += "],";
		json += lists;

		// disclosees
		i = 0;
		lists = "'dataDisclosees' : [{'dataDisclosee' : '"
				+ dataDisclosees.get(i) + "'}";
		for (i = 1; i < dataDisclosees.size(); i++) {
			lists += ",{'dataDisclosee' : '" + dataDisclosees.get(i) + quote
					+ "}";
		}
		lists += "],";
		json += lists;

		// transfer
		json += "'transfer' : '" + transfers + quote;
		return json;
	}

}
