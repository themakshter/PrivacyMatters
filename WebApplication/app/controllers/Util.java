package controllers;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class Util {
	private static MongoClient client;
	
	public static DB connectToDB() throws UnknownHostException{
  	   	MongoClientURI dbURI = new MongoClientURI("mongodb://admin:incorrect@ds033629.mongolab.com:33629/data_controllers");
  		client = new MongoClient(dbURI);
  		DB database = client.getDB(dbURI.getDatabase());
  		return database;
	  }
	
	  public static void closeDB(){
		  client.close();
	  }	
	  
	  public static String getUniqueString(String st){
		  return st.replaceAll("/| |,","_");
	  }
}
