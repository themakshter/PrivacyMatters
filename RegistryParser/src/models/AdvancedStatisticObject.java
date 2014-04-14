package models;

import java.util.ArrayList;

import util.Util;

public class AdvancedStatisticObject extends StatisticObject{
	protected int medianDataClasses;
	protected int medianDataSubjects;
	protected int medianDataDisclosees;
	protected ArrayList<Integer> dataClasses, dataSubjects,
	dataDisclosees;
	

	public AdvancedStatisticObject() {
		initialiseArrays();
	}

	public void initialiseArrays() {
		dataClasses = new ArrayList<Integer>();
		dataSubjects = new ArrayList<Integer>();
		dataDisclosees = new ArrayList<Integer>();
	}

	public void calculateMedians() {
		medianDataClasses = Util.calculateMedian(dataClasses);
		medianDataSubjects = Util.calculateMedian(dataSubjects);
		medianDataDisclosees = Util.calculateMedian(dataDisclosees);
		initialiseArrays();
	}

	public void addDataClass(int dataClass) {
		dataClasses.add(dataClass);
	}

	public void addDataSubject(int dataSubject) {
		dataSubjects.add(dataSubject);
	}

	public void addDataDisclosee(int dataDisclosee) {
		dataDisclosees.add(dataDisclosee);
	}

	public int getMedianDataClasses() {
		return medianDataClasses;
	}

	public void setMedianDataClasses(int medianDataClasses) {
		this.medianDataClasses = medianDataClasses;
	}

	public int getMedianDataSubjects() {
		return medianDataSubjects;
	}

	public void setMedianDataSubjects(int medianDataSubjects) {
		this.medianDataSubjects = medianDataSubjects;
	}

	public int getMedianDataDisclosees() {
		return medianDataDisclosees;
	}

	public void setMedianDataDisclosees(int medianDataDisclosees) {
		this.medianDataDisclosees = medianDataDisclosees;
	}

	public ArrayList<Integer> getDataClasses() {
		return dataClasses;
	}

	public void setDataClasses(ArrayList<Integer> dataClasses) {
		this.dataClasses = dataClasses;
	}

	public ArrayList<Integer> getDataSubjects() {
		return dataSubjects;
	}

	public void setDataSubjects(ArrayList<Integer> dataSubjects) {
		this.dataSubjects = dataSubjects;
	}

	public ArrayList<Integer> getDataDisclosees() {
		return dataDisclosees;
	}

	public void setDataDisclosees(ArrayList<Integer> dataDisclosees) {
		this.dataDisclosees = dataDisclosees;
	}

	
}
