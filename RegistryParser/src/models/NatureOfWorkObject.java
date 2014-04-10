package models;

import java.util.ArrayList;
import java.util.Collections;

import util.Util;

public class NatureOfWorkObject extends StatisticObject {
	private int medianDataClasses, medianSensitiveData, medianDataSubjects,
			medianDataDisclosees;
	private ArrayList<Integer> dataClasses, sensitiveData, dataSubjects,
			dataDisclosees;

	public NatureOfWorkObject() {
		initialiseArrays();
	}

	public void initialiseArrays() {
		dataClasses = new ArrayList<Integer>();
		sensitiveData = new ArrayList<Integer>();
		dataSubjects = new ArrayList<Integer>();
		dataDisclosees = new ArrayList<Integer>();
	}

	public void calculateMedians() {
		medianDataClasses = Util.calculateMedian(dataClasses);
		medianSensitiveData = Util.calculateMedian(sensitiveData);
		medianDataSubjects = Util.calculateMedian(dataSubjects);
		medianDataDisclosees = Util.calculateMedian(dataDisclosees);
		initialiseArrays();
	}

	public void addDataClass(int dataClass) {
		dataClasses.add(dataClass);
	}

	public void addSensitiveData(int sensitive) {
		sensitiveData.add(sensitive);
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

	public int getMedianSensitiveData() {
		return medianSensitiveData;
	}

	public void setMedianSensitiveData(int medianSensitiveData) {
		this.medianSensitiveData = medianSensitiveData;
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

	public ArrayList<Integer> getSensitiveData() {
		return sensitiveData;
	}

	public void setSensitiveData(ArrayList<Integer> sensitiveData) {
		this.sensitiveData = sensitiveData;
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
