package controllers;

import java.net.UnknownHostException;

import models.GeneralStatistics;
import models.NatureOfWorkObject;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import play.mvc.Controller;

public class Values extends Controller{

	public static int getMedian(String type) {
		try {
			DB database;
			String json;
			database = Util.connectToDB();
			DBCollection collection = database.getCollection("generalStats");
			json = collection.find().next().toString();
			Util.closeDB();
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
		} catch (UnknownHostException e) {
			return 0;
		}
	}

	public static int getNatureOfWorkMedian(String type,String natureOfWork){
		try{
			DB database;
			String json;
			Gson gson = new Gson();
			database = Util.connectToDB();
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
	
	public static int getNumberOfRecords(){
		try{
			DB database;
			database = Util.connectToDB();
			DBCollection collection = database.getCollection("registry");
			return (int) collection.count();
		}catch(Exception e){
			return 0;
		}
	}
}
