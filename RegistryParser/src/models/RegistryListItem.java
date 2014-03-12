package models;

public class RegistryListItem {

	private String registrationNumber, organisationName;

	public RegistryListItem(String regNo, String orgName) {
		registrationNumber = regNo;
		organisationName = orgName;

	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public String getOrganisationName() {
		return organisationName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((organisationName == null) ? 0 : organisationName.hashCode());
		result = prime
				* result
				+ ((registrationNumber == null) ? 0 : registrationNumber
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistryListItem other = (RegistryListItem) obj;
		if (organisationName == null) {
			if (other.organisationName != null)
				return false;
		} else if (!organisationName.equals(other.organisationName))
			return false;
		if (registrationNumber == null) {
			if (other.registrationNumber != null)
				return false;
		} else if (!registrationNumber.equals(other.registrationNumber))
			return false;
		return true;
	}
}
