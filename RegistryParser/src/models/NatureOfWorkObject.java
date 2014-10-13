package models;

import java.util.ArrayList;

import util.Util;

public class NatureOfWorkObject extends AdvancedStatisticObject {
	private int medianSensitiveData;
			
	private ArrayList<Integer> sensitiveData;

	public NatureOfWorkObject() {
		initialiseArrays();
	}

	@Override
	public void initialiseArrays() {
		dataClasses = new ArrayList<Integer>();
		sensitiveData = new ArrayList<Integer>();
		dataSubjects = new ArrayList<Integer>();
		dataDisclosees = new ArrayList<Integer>();
	}
	
	@Override
	public void calculateMedians() {
		medianDataClasses = Util.calculateMedian(dataClasses);
		medianSensitiveData = Util.calculateMedian(sensitiveData);
		medianDataSubjects = Util.calculateMedian(dataSubjects);
		medianDataDisclosees = Util.calculateMedian(dataDisclosees);
		initialiseArrays();
	}

	public void addSensitiveData(int sensitive) {
		sensitiveData.add(sensitive);
	}

	public int getMedianSensitiveData() {
		return medianSensitiveData;
	}

	public void setMedianSensitiveData(int medianSensitiveData) {
		this.medianSensitiveData = medianSensitiveData;
	}

	public void setSensitiveData(ArrayList<Integer> sensitiveData) {
		this.sensitiveData = sensitiveData;
	}

}
