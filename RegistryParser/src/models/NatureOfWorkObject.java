package models;

import java.util.ArrayList;
import java.util.Collections;

public class NatureOfWorkObject extends StatisticObject {
	private int medianDataClasses,medianSensitiveData,medianDataSubjects,medianDataDisclosees;
	private ArrayList<Integer> dataClasses,sensitiveData,dataSubjects,dataDisclosees;
	
	public NatureOfWorkObject(){
		initialiseArrays();	
	}
	
	public void initialiseArrays(){
		dataClasses = new ArrayList<Integer>();
		sensitiveData = new ArrayList<Integer>();
		dataSubjects = new ArrayList<Integer>();
		dataDisclosees = new ArrayList<Integer>();
	}
	
	public void calculateMedians(){
		medianDataClasses = calculateMedian(dataClasses);
		medianSensitiveData = calculateMedian(sensitiveData);
		medianDataSubjects = calculateMedian(dataSubjects);
		medianDataDisclosees = calculateMedian(dataDisclosees);
		//initialiseArrays();
	}
	
	public int calculateMedian(ArrayList<Integer> list){
		int median = 0;
			Collections.sort(list);
			//even number in list
			if (list.size() % 2 == 0) {
				median = list.size() / 2;
				median = (list.get(median - 1) + list.get(median)) / 2;
			} else { //odd number
				median = Math.round(list.size() / 2);
				median = list.get(median);
			}
		return median;
		
	}
	
	public void addDataClass(int dataClass){
		dataClasses.add(dataClass);
	}
	
	public void addSensitiveData(int sensitive){
		sensitiveData.add(sensitive);
	}
	
	public void addDataSubject(int dataSubject){
		dataSubjects.add(dataSubject);
	}
	
	public void addDataDisclosee(int dataDisclosee){
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
