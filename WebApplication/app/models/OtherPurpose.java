package models;

public class OtherPurpose {
	private String purpose,statement;

	public String getUniqueName(){
		return purpose.split(" ")[0];
	}
	
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}
}
