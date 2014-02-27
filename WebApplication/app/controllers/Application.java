package controllers;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;

import play.*;
import play.libs.Json;
import play.mvc.*;
import views.html.*;
import com.mongodb.*;
import models.*;


public class Application extends Controller {
	private static MongoClient client;

	
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
    
    public static Result registry(){
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
    	return ok(registry.render(regList));
    }
    
    public static Result dataController(String registrationNumber){
    	String result = "Data Controller doesn't exist";
    	try{
    		DBCollection registry = connectToDB("registry");
    		BasicDBObject query = new BasicDBObject("registrationNumber",registrationNumber);
    		DBCursor cursor = registry.find(query);
    		//JsonNode node = Json.parse(cursor.next().toString());
    		result = cursor.next().toString();
    		closeDB();
    	}catch(Exception e){
    		System.out.println(e);
    	}
    	return ok(result);
    }
  
}
