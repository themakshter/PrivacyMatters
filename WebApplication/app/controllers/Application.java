package controllers;

import org.codehaus.jackson.JsonNode;

import play.*;
import play.libs.Json;
import play.mvc.*;
import views.html.*;
import com.mongodb.*;


public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Privacy Matters"));
    }
    
    public static Result getController(){
    	String json = "Could not connect";
    	String result = "(could not find)";
    	try{
    		MongoClient client = new MongoClient("localhost",27017);
    		DB database = client.getDB("dataControllers");
    		DBCollection registry = database.getCollection("registry");
    		DBCursor cursor = registry.find();
    		json = cursor.next().toString();
    		client.close();
    		JsonNode node = (JsonNode) Json.parse(json);
    		result = node.findPath("organisationName").getTextValue();
    	}catch(Exception e){
    		System.out.println(e);
    	}
    	return ok(result);
    }
  
}
