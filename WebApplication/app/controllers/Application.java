package controllers;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import play.*;
import play.libs.Json;
import play.mvc.*;
import views.html.*;
import com.mongodb.*;
import models.*;


public class Application extends Controller {
	private static MongoClient client;

	  public static DBCollection connectToDB(String collection) throws UnknownHostException{
  	   	MongoClientURI dbURI = new MongoClientURI("mongodb://admin:incorrect@ds033629.mongolab.com:33629/data_controllers");
  		client = new MongoClient(dbURI);
  		DB database = client.getDB(dbURI.getDatabase());
  		DBCollection collectionRetrived = database.getCollection(collection);
  		return collectionRetrived;
	  }
  
	  public static void closeDB(){
		  client.close();
	  }	
	
    public static Result index() {
        return ok(index.render("Privacy Matters"));
    }
    
    public static Result getController(){
    	String json = "Could not connect";
    	String result = "(could not find)";
    	try{
    		DBCollection registry = connectToDB("registry");
    		DBCursor cursor = registry.find();
    		json = cursor.next().toString();
    		JsonNode node = Json.parse(json);
    		result = node.findPath("organisationName").getTextValue();
    		closeDB();
    	}catch(Exception e){
    		System.out.println(e);
    	}
    	return ok(result);
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
    		DBCollection registry = connectToDB("registry");
    		DBCursor cursor = registry.find();
    		while(cursor.hasNext()){
    			controller = cursor.next();
    			json = controller.toString();
    			node = Json.parse(json);
    			String regNo = node.findPath("registrationNumber").getTextValue();
    			String name = node.findPath("organisationName").getTextValue();
    			regList.add(new RegistryListItem(regNo,name));
    		};
    		closeDB();
    	}catch(Exception e){
    		System.out.println(e);
    	}
    	return regList;
    }
    
    public static Result registryJSON(){
    	ArrayList<RegistryListItem> regList = getRegistry();
    	return ok(Json.toJson(regList));
    }
    
    public static DataController getDataController(String registrationNumber){
    	String result = "Data Controller doesn't exist";
    	DataController controller = new DataController();
		
    	try{
    		DBCollection registry = connectToDB("registry");
    		BasicDBObject query = new BasicDBObject("registrationNumber",registrationNumber);
    		DBCursor cursor = registry.find(query);
    		String json = cursor.next().toString();
    		closeDB();
    		JsonNode node = Json.parse(json);
    		
    		//set main variables
    		controller.setRegistrationNumber(node.findPath("registrationNumber").getTextValue());
    		controller.setOrganisationName(node.findPath("organisationName").getTextValue());
	    	controller.setCompaniesHouseNumber(node.findPath("companiesHouseNumber").getTextValue());
	    	controller.setAddress(node.findPath("address").getTextValue());
	    	controller.setPostcode(node.findPath("postcode").getTextValue());
	    	controller.setCountry(node.findPath("country").getTextValue());
	    	controller.setFoiFlag(node.findPath("foiFlag").getTextValue());
	    	controller.setStartDate(node.findPath("startDate").getTextValue());
	    	controller.setEndDate(node.findPath("endDate").getTextValue());
	    	controller.setExemptFlag(node.findPath("exemptFlag").getTextValue());
	    	controller.setTradingName(node.findPath("tradingName").getTextValue());
	    	controller.setUkContact(node.findPath("ukContact").getTextValue());
	    	controller.setSubjectAccess(node.findPath("subjectAccess").getTextValue());
	    	controller.setFormat(node.findPath("format").getTextValue());
    		String format = controller.getFormat();
	    	
    		//handling it differently
    		if(format.equals("old")){
    			ArrayList<Purpose> purposes = new ArrayList<Purpose>();
    			JsonNode arrNode = new ObjectMapper().readTree(json).get("purposes");
    			result = "" + arrNode.size();
    			Purpose purpose;
    			for(JsonNode n : arrNode){
    				purpose = new Purpose();
    				purpose.setPurpose(n.findPath("purpose").getTextValue());
    				purpose.setDescription(n.findPath("description").getTextValue());
    				purpose.setFurtherDescription(n.findPath("furtherDescription").getTextValue());
    				purpose.setTransfers(n.findPath("transfer").getTextValue());
    				ArrayList<String> dataSubjects,dataClasses,dataDisclosees;
    				JsonNode subNode = new ObjectMapper().readTree(n.toString()).get("dataSubjects");
    				dataSubjects = new ArrayList<String>();
    				for(JsonNode sNode:subNode){
    					dataSubjects.add(sNode.findPath("dataSubject").getTextValue());
    				}
    				purpose.setDataSubjects(dataSubjects);
    				JsonNode classNode = new ObjectMapper().readTree(n.toString()).get("dataClasses");
    				dataClasses = new ArrayList<String>();
    				for(JsonNode cNode:classNode){
    					dataClasses.add(cNode.findPath("dataClass").getTextValue());
    				}
    				purpose.setDataClasses(dataClasses);
    				JsonNode discNode = new ObjectMapper().readTree(n.toString()).get("dataDisclosees");
    				dataDisclosees = new ArrayList<String>();
    				for(JsonNode dNode:discNode){
    					dataDisclosees.add(dNode.findPath("dataDisclosee").getTextValue());
    				}
    				purpose.setDataDisclosees(dataDisclosees);
    				purposes.add(purpose);
    			}
    			controller.setOldFormat(purposes);
	    	}else if(format.equals("new")){
	    		NewFormat newFormat = new NewFormat();
	    		newFormat.setNatureOfWork(node.findPath("natureOfWork").getTextValue());
	    		newFormat.setTransfers(node.findPath("transfer").getTextValue());
	    		ArrayList<String> purposes,dataSubjects,dataClasses,dataDisclosees;
	    		JsonNode purpNode = new ObjectMapper().readTree(json).get("purposes");
				purposes = new ArrayList<String>();
				for(JsonNode pNode:purpNode){
					purposes.add(pNode.findPath("purpose").getTextValue());
				}
				newFormat.setPurposes(purposes);	    		
	    		JsonNode subNode = new ObjectMapper().readTree(json).get("dataSubjects");
				dataSubjects = new ArrayList<String>();
				for(JsonNode sNode:subNode){
					dataSubjects.add(sNode.findPath("dataSubject").getTextValue());
				}
				newFormat.setDataSubjects(dataSubjects);
				JsonNode classNode = new ObjectMapper().readTree(json).get("dataClasses");
				dataClasses = new ArrayList<String>();
				for(JsonNode cNode:classNode){
					dataClasses.add(cNode.findPath("dataClass").getTextValue());
				}
				newFormat.setDataClasses(dataClasses);
				JsonNode discNode = new ObjectMapper().readTree(json).get("dataDisclosees");
				dataDisclosees = new ArrayList<String>();
				for(JsonNode dNode:discNode){
					dataDisclosees.add(dNode.findPath("dataDisclosee").getTextValue());
				}
				newFormat.setDataDisclosees(dataDisclosees);
				controller.setNewFormat(newFormat);
	    	}
  			
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
