package controllers;

import java.net.UnknownHostException;
import java.util.ArrayList;


import org.codehaus.jackson.JsonNode;

import com.google.gson.*;
import play.libs.Json;
import play.mvc.*;
import views.html.*;
import com.mongodb.*;
import models.*;


public class Application extends Controller {
	private static MongoClient client;
	private static DB database;
	  
	public static void connectToDB() throws UnknownHostException{
  	   	MongoClientURI dbURI = new MongoClientURI("mongodb://admin:incorrect@ds033629.mongolab.com:33629/data_controllers");
  		client = new MongoClient(dbURI);
  		database = client.getDB(dbURI.getDatabase());
	  }
	
	  public static void closeDB(){
		  client.close();
	  }	
	
    public static Result index() {
        return ok(index.render("Privacy Matters"));
    }
    
    public static Result registry(){
    	ArrayList<RegistryListItem> regList = getRegistry();
    	return ok(registry.render(regList));
    }
    
    public static ArrayList<RegistryListItem> getRegistry(){
    	ArrayList<RegistryListItem> regList = new ArrayList<RegistryListItem>();
    	try{
    		DBObject controller;
    		String json;
    		JsonNode node;
    		connectToDB();
    		DBCollection registry = database.getCollection("registry");
    		DBCursor cursor = registry.find();
    		while(cursor.hasNext()){
    			controller = cursor.next();
    			json = controller.toString();
    			node = Json.parse(json);
    			String regNo = node.findPath("registrationNumber").getTextValue();
    			String name = node.findPath("organisationName").getTextValue();
    			regList.add(new RegistryListItem(regNo,name));
    		}
    		closeDB();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return regList;
    }
    
    public static Result registryJSON(){
    	ArrayList<RegistryListItem> regList = getRegistry();
    	return ok(Json.toJson(regList));
    }
    
	public static String getNewFormatMedianStatistic(String type,
			NewFormat newFormat) {
		int median;
		String statement = "(cannot connect)";
		String json;
		try {
			connectToDB();
			Gson gson = new Gson();
			DBCollection collection = database.getCollection("generalStats");
			DBCursor cursor = collection.find();
			json = cursor.next().toString();
			GeneralStatistics generalStats = gson.fromJson(json,
					GeneralStatistics.class);
			collection = database.getCollection("natureOfWorkStats");
			BasicDBObject query = new BasicDBObject("type",
					newFormat.getNatureOfWork());
			cursor = collection.find(query);
			json = cursor.next().toString();
			NatureOfWorkObject nat = gson.fromJson(json,
					NatureOfWorkObject.class);
			int natMedian;
			int size;
			switch (type) {
			case "dataClass":
				median = generalStats.getMedianDataClasses();
				natMedian = nat.getMedianDataClasses();
				size = newFormat.getDataClasses().size();
				statement = "This data controller collects "
						+ newFormat.getDataClasses().size()
						+ " different classes of information.";
				break;
			case "sensitiveData":
				median = generalStats.getMedianSensitiveData();
				natMedian = nat.getMedianSensitiveData();
				size = newFormat.getSensitiveData().size();
				statement = "This data controller collects " + size
						+ " different sensitive classes of information.";
				break;
			case "dataSubject":
				median = generalStats.getMedianDataSubjects();
				natMedian = nat.getMedianDataSubjects();
				size = newFormat.getDataSubjects().size();
				statement = "This data controller collects information from "
						+ size + " different types of people.";
				break;
			case "dataDisclosee":
				median = generalStats.getMedianDataDisclosees();
				natMedian = nat.getMedianDataDisclosees();
				size = newFormat.getDataDisclosees().size();
				statement = "This data controller shares information with "
						+ size + " different types of people.";
				break;
			default:
				natMedian = median = size = 0;
				break;
			}
			if (median > size) {
				statement += "This is greater than the median for all data controllers, which is "
						+ median + ".";
			} else if (median < size) {
				statement += "This is less than the median for all data controllers, which is "
						+ median + ".";
			} else {
				statement += "This is equal to the median for all data controllers.";
			}

			if (natMedian > size) {
				statement += "Moreover, this is greater than the median for all data controllers having the same nature of work, which is "
						+ natMedian + ".";
			} else if (natMedian < size) {
				statement += "Moreover, this is less than the median for all data controllers having the same nature of work, which is "
						+ natMedian + ".";
			} else {
				statement += "Moreover, this is equal to the median for all data controllers having the same nature of work.";
			}
			closeDB();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statement;
	}

    public static DataController getDataController(String registrationNumber){
    	DataController controller = new DataController();
    	try{
    		connectToDB();
    		DBCollection registry = database.getCollection("registry");
    		BasicDBObject query = new BasicDBObject("registrationNumber",registrationNumber);
    		DBCursor cursor = registry.find(query);
    		String json = cursor.next().toString();
    		closeDB();
    		Gson gson = new Gson();
    		controller = gson.fromJson(json, DataController.class);
    		controller.fixName();
    	}catch(Exception e){
    		System.out.println(e);
    	}
    	return controller;
    }
    
    public static Result dataController(String registrationNumber){
    	DataController controller = getDataController(registrationNumber);
    	return ok(dataController.render(controller));
    }
    
    public static Result dataControllerJSON(String registrationNumber){
    	DataController controller = getDataController(registrationNumber);
    	return ok(Json.toJson(controller));
    }
  
}
