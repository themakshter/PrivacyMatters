package controllers;

import play.*;
import play.mvc.*;
import views.html.*;
import com.mongodb.*;
public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Privacy Matters"));
    }
    
    public static Result getController(){
    	try{
    		MongoClient mongoClient = new MongoClient("localhost",27017);
    	}catch(Exception e){
    		
    	}
    	String json = "{\"x\" : \"1\"}";
    	return ok(json);
    }
  
}
