package models;

public class GeneralStatistics {
	private int recordCount, companiesHouseCount, addressCount, postcodeCount,
			tradingNameCount, natureOfWorkCount, newBlobCount, oldBlobCount,
			neitherBlobCount, purposesCount, dataClassesCount,
			sensitiveDataCount, dataSubjectsCount, dataDiscloseesCount;

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public void setCompaniesHouseCount(int companiesHouseCount) {
		this.companiesHouseCount = companiesHouseCount;
	}

	public void setAddressCount(int addressCount) {
		this.addressCount = addressCount;
	}

	public void setPostcodeCount(int postcodeCount) {
		this.postcodeCount = postcodeCount;
	}

	public void setTradingNameCount(int tradingNameCount) {
		this.tradingNameCount = tradingNameCount;
	}

	public void setNatureOfWorkCount(int natureOfWorkCount) {
		this.natureOfWorkCount = natureOfWorkCount;
	}

	public void setNewBlobCount(int newBlobCount) {
		this.newBlobCount = newBlobCount;
	}

	public void setOldBlobCount(int oldBlobCount) {
		this.oldBlobCount = oldBlobCount;
	}

	public void setNeitherBlobCount(int neitherBlobCount) {
		this.neitherBlobCount = neitherBlobCount;
	}

	public void incrementRecordCount() {
		this.recordCount++;
	}

	public void incrementCompaniesHouseCount() {
		this.companiesHouseCount++;
	}

	public void incrementAddressCount() {
		this.addressCount++;
	}

	public void incrementPostcodeCount() {
		this.postcodeCount++;
	}

	public void incrementTradingNameCount() {
		this.tradingNameCount++;
	}

	public void incrementNatureOfWorkCount() {
		this.natureOfWorkCount++;
	}

	public void incrementNewBlobCount() {
		this.newBlobCount++;
	}

	public void incrementOldBlobCount() {
		this.oldBlobCount++;
	}

	public void incrementNeitherBlobCount() {
		this.neitherBlobCount++;
	}

	public void setPurposesCount(int count) {
		this.purposesCount = count;
	}

	public void setDataClassesCount(int count) {
		this.dataClassesCount = count;
	}

	public void setSensitiveDataCount(int count) {
		this.sensitiveDataCount = count;
	}

	public void setDataSubjectsCount(int count) {
		this.dataSubjectsCount = count;
	}

	public void setDataDiscloseesCount(int count) {
		this.dataDiscloseesCount = count;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public int getCompaniesHouseCount() {
		return companiesHouseCount;
	}

	public int getAddressCount() {
		return addressCount;
	}

	public int getPostcodeCount() {
		return postcodeCount;
	}

	public int getTradingNameCount() {
		return tradingNameCount;
	}

	public int getNatureOfWorkCount() {
		return natureOfWorkCount;
	}

	public int getNewBlobCount() {
		return newBlobCount;
	}

	public int getOldBlobCount() {
		return oldBlobCount;
	}

	public int getNeitherBlobCount() {
		return neitherBlobCount;
	}

	public int getPurposesCount() {
		return purposesCount;
	}

	public int getDataClassesCount() {
		return dataClassesCount;
	}

	public int getSensitiveDataCount() {
		return sensitiveDataCount;
	}

	public int getDataSubjectsCount() {
		return dataSubjectsCount;
	}

	public int getDataDiscloseesCount() {
		return dataDiscloseesCount;
	}

}
