import java.util.ArrayList;

public class Purpose {
	private String purpose, description, furtherDescription, transfers;
	private ArrayList<String> subjects, disclosees, classes;

	public Purpose() {
		subjects = new ArrayList<String>();
		classes = new ArrayList<String>();
		disclosees = new ArrayList<String>();
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

	public void setSubjects(ArrayList<String> subjects) {
		this.subjects = subjects;
	}

	public ArrayList<String> getSubjects() {
		return subjects;
	}

	public void setClasses(ArrayList<String> classes) {
		this.classes = classes;
	}

	public ArrayList<String> getClasses() {
		return classes;
	}

	public void setDisclosees(ArrayList<String> disclosees) {
		this.disclosees = disclosees;
	}

	public ArrayList<String> getDisclosees() {
		return disclosees;
	}

	public void setTransfers(String transfers) {
		this.transfers = transfers;
	}

	public String getTransfers() {
		return transfers;
	}

}
