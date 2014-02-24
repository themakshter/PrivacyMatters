import java.util.ArrayList;

public class NatureOfWork {
	private ArrayList<String> purposes, subjects, classes, disclosees;

	private String natureOfWork, transfers;

	public NatureOfWork() {
		purposes = new ArrayList<String>();
		subjects = new ArrayList<String>();
		classes = new ArrayList<String>();
		disclosees = new ArrayList<String>();
	}

	public void setNatureOfWork(String nature) {
		natureOfWork = nature;
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
