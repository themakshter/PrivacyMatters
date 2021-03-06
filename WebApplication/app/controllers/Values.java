package controllers;

import models.AdvancedStatisticObject;
import models.GeneralStatistics;
import models.NatureOfWorkObject;
import models.StatisticObject;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import play.mvc.Controller;

public class Values extends Controller{
	private static DB database = Util.connectToDB();
	public static int getMedian(String type) {
			String json;
			DBCollection collection = database.getCollection("generalStats");
			json = collection.find().next().toString();
			Gson gson = new Gson();
			GeneralStatistics generalStats = gson.fromJson(json,
					GeneralStatistics.class);
			switch (type) {
			case "dataClass":
				return generalStats.getMedianDataClasses();
			case "sensitiveData":
				return generalStats.getMedianSensitiveData();
			case "dataSubject":
				return generalStats.getMedianDataSubjects();
			case "dataDisclosee":
				return generalStats.getMedianDataDisclosees();
			default:
				return 0;
			}
		
	}

	public static int getNatureOfWorkMedian(String type,String natureOfWork){
		try{
			String json;
			Gson gson = new Gson();
			DBCollection collection = database.getCollection("natureOfWorkStats");
			BasicDBObject query = new BasicDBObject("type",natureOfWork);
			json = collection.find(query).next().toString();
			NatureOfWorkObject nat = gson.fromJson(json,
					NatureOfWorkObject.class);
			switch (type) {
			case "dataClass":
				return nat.getMedianDataClasses();
			case "sensitiveData":
				return nat.getMedianSensitiveData();
			case "dataSubject":
				return nat.getMedianDataSubjects();
			case "dataDisclosee":
				return nat.getMedianDataDisclosees();
			default:
				return 0;
			}
		}catch(Exception e){
			return 0;
		}
	}
	
	public static int getPurposeMedian(String type,String purpose){
		try{
			String json;
			Gson gson = new Gson();
			DBCollection collection = database.getCollection("purposeStats");
			BasicDBObject query = new BasicDBObject("type",purpose);
			json = collection.find(query).next().toString();
			AdvancedStatisticObject nat = gson.fromJson(json,
					AdvancedStatisticObject.class);
			switch (type) {
			case "dataClass":
				return nat.getMedianDataClasses();
			case "dataSubject":
				return nat.getMedianDataSubjects();
			case "dataDisclosee":
				return nat.getMedianDataDisclosees();
			default:
				return 0;
			}
		}catch(Exception e){
			return 0;
		}
	}
	
	public static int getNumberOfControllers(String type,String queryString){
		String collectionName = type+"Stats";
		 try{
			 String json;
			 Gson gson= new Gson();
			 DBCollection collection= database.getCollection(collectionName);
			 BasicDBObject query = new BasicDBObject("type",queryString);
			 json = collection.find(query).next().toString();
			 StatisticObject statObject = gson.fromJson(json, StatisticObject.class);
			 return statObject.getSize();
		 }catch(Exception e){
			 return 0;
		 }
	}
	
	public static int getNumberOfRecords(){
		try{
			DBCollection collection = database.getCollection("registry");
			int count = (int) collection.count();
			return (int) count;
		}catch(Exception e){
			return 0;
		}
	}
}
