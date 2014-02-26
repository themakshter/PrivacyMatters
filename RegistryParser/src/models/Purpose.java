package models;
import java.util.ArrayList;

public class Purpose {
	private String purpose, description, furtherDescription, transfers;
	private ArrayList<String> dataSubjects, dataDisclosees, dataClasses;

	public Purpose() {
		dataSubjects = new ArrayList<String>();
		dataClasses = new ArrayList<String>();
		dataDisclosees = new ArrayList<String>();
		description = "(not given)";
		furtherDescription = "(not given)";
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setFurtherDescription(String further) {
		furtherDescription = further;
	}

	public String getFurtherDescription() {
		return furtherDescription;
	}

	public void setDataSubjects(ArrayList<String> subjects) {
		this.dataSubjects = subjects;
	}

	public ArrayList<String> getDataSubjects() {
		return dataSubjects;
	}

	public void setDataClasses(ArrayList<String> classes) {
		this.dataClasses = classes;
	}

	public ArrayList<String> getDataClasses() {
		return dataClasses;
	}

	public void setDataDisclosees(ArrayList<String> disclosees) {
		this.dataDisclosees = disclosees;
	}

	public ArrayList<String> getDataDisclosees() {
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
		String json = "{";
		// purpose
		String lists;
		int i;

		// purpose
		json += "'purpose' : '" + purpose + quote + comma;

		// description
		json += "'description' : '" + description + quote + comma;
		// further description
		json += "'furtherDescription' : '" + furtherDescription + quote + comma;

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
		json += "'transfer' : '" + transfers + quote + "}";
		return json;
	}

}
