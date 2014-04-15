package controllers;

import java.util.ArrayList;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.*;

import play.data.DynamicForm;
import static play.data.Form.*;
import play.libs.Json;
import play.mvc.*;
import views.html.*;
import com.mongodb.*;
import models.*;

public class Application extends Controller {

	 private static DB database = Util.connectToDB();
	
	public static Result index() {	  
        return ok(index.render("Privacy Matters"));
    }
	
	public static Result similar(String registrationNumber,String type,String information){
		information = information.replaceAll("_", " ");
		information = information.replaceAll("-","/");
		ArrayList<RegistryListItem> regList = new ArrayList<RegistryListItem>();
		try{
			DBObject controller;
			String json;
			DBCollection registry = database.getCollection(type+"Stats");
			BasicDBObject query = new BasicDBObject("type",information);
			DBCursor cursor = registry.find(query);
			while(cursor.hasNext()){
				controller = cursor.next();
				json = controller.toString();
				JsonNode arrNode = new ObjectMapper().readTree(json).get("companies");
				for(JsonNode node : arrNode){
					String regNo = node.findPath("registrationNumber").getTextValue();
					if(registrationNumber.equals(regNo)){
						continue;
					}
	    			String name = node.findPath("organisationName").getTextValue();
					regList.add(new RegistryListItem(regNo,name));
				}
			}
			
		}catch(Exception e){
			
		}
		String message ="";
		switch(type){
		case "dataClass":
			message = "Controllers also collecting data class";
			break;
		case "sensitiveData":
			message = "Controllers also collecting sensitive data class";
			break;
		case "dataSubject":
			message = "Controllers also collecting from data subject";
			break;
		case "dataDisclosee":
			message = "Controllers also sharing with data disclosee";
			break;
		case "purpose":
			message = "Controllers also collecting information for purpose";
			break;
		case "natureOfWork":
			message = "Controllers of the same nature of work";
			break;
		}
		
		return ok(similar.render(regList, message + " \"" + information + "\""));
	}
	
	
	public static Result search(){
		ArrayList<RegistryListItem> regList = new ArrayList<RegistryListItem>();
		DynamicForm requestData = form().bindFromRequest();
		String queryString= (requestData.get("dataController"));
	    try{
	    	DBObject controller;
    		String json;
    		JsonNode node;
	    	DBCollection registry = database.getCollection("registry");
			BasicDBObject query = new BasicDBObject();
			query.put("organisationName", java.util.regex.Pattern.compile(queryString.toUpperCase()));
			DBCursor cursor = registry.find(query);
			while(cursor.hasNext()){
				controller = cursor.next();
    			json = controller.toString();
    			node = Json.parse(json);
    			String regNo = node.findPath("registrationNumber").getTextValue();
    			String name = node.findPath("organisationName").getTextValue();
    			regList.add(new RegistryListItem(regNo,name));
			}
	    }catch(Exception e){
	    	
	    }
	    return ok(searchResult.render(regList,queryString));
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
        	return regList;
    	}catch(Exception e){
    		return regList;
    	}
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
			if (size > median) {
				statement += "This is greater than the median for all data controllers, which is "
						+ median + ".";
			} else if (size < median) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statement;
	}

	public static DataController getDataController(String registrationNumber){
    	DataController controller = new DataController();
    	try{
    		DBCollection registry = database.getCollection("registry");
    		BasicDBObject query = new BasicDBObject("registrationNumber",registrationNumber);
    		DBCursor cursor = registry.find(query);
    		String json = cursor.next().toString();
    		Gson gson = new Gson();
    		controller = gson.fromJson(json, DataController.class);
    		controller.fixName();
    		return controller;
    	}catch(Exception e){
    		return null;
    	}
    }
    
    public static Result dataController(String registrationNumber){
    	DataController controller = getDataController(registrationNumber);
    	if(controller == null){
    		return notFound("<h1>DataController not found</h1>").as("text/html");
    	}else{
    		return ok(dataController.render(controller));
    	}
    }
    
    public static Result dataControllerJSON(String registrationNumber){
    	DataController controller = getDataController(registrationNumber);
    	return ok(Json.toJson(controller));
    }
  
}
