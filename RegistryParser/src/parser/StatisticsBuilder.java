package parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;

import models.DataController;
import models.GeneralStatistics;
import models.RegistryListItem;
import models.StatisticObject;

public class StatisticsBuilder {

	private PrintWriter out;

	private HashSet<String> natureOfWorkSet, dataPurposeSet, dataClassSet,
			sensitiveDataSet, dataSubjectSet, dataDiscloseeSet;
	private HashMap<String, StatisticObject> natureOfWorkMap, dataPurposeMap,
			dataClassMap, dataSubjectMap, sensitiveDataMap, dataDiscloseeMap;
	private static MongoClientURI dbURI;
	private MongoClient client;
	private DB database;
	private DBCollection collection;
	private GeneralStatistics generalStats;
	private DataController dataController;
	private Gson gson;

	public StatisticsBuilder() throws Exception {
		gson = new Gson();
		generalStats = new GeneralStatistics();
		out = new PrintWriter(new BufferedWriter(new FileWriter("files/other/builderStatistics.txt")));
		dbURI = new MongoClientURI("mongodb://admin:incorrect@ds033629.mongolab.com:33629/data_controllers");
		client = new MongoClient(dbURI);
		natureOfWorkSet = new HashSet<String>();
		dataPurposeSet = new HashSet<String>();
		dataSubjectSet = new HashSet<String>();
		dataClassSet = new HashSet<String>();
		sensitiveDataSet = new HashSet<String>();
		dataDiscloseeSet = new HashSet<String>();
		natureOfWorkMap = new HashMap<String, StatisticObject>();
		dataPurposeMap = new HashMap<String, StatisticObject>();
		dataClassMap = new HashMap<String, StatisticObject>();
		dataSubjectMap = new HashMap<String, StatisticObject>();
		sensitiveDataMap = new HashMap<String, StatisticObject>();
		dataDiscloseeMap = new HashMap<String, StatisticObject>();
		// client = new MongoClient("localhost",27017);
		// database = client.getDB("dataControllers");
		database = client.getDB(dbURI.getDatabase());
		String[] collections = { "generalStatistics", "natureOfWorkStats",
				"purposesStats", "dataClassesStats", "dataSubjectsStats",
				"dataDiscloseeStats" };
		dropCollections(collections);
	}

	public void buildStatistics() {
		String json;
		collection = database.getCollection("registry");
		DBCursor cursor = collection.find();
		while(cursor.hasNext()){
			json = cursor.next().toString();
			dataController = gson.fromJson(json, DataController.class);
			analyseController(dataController);
		}
	}
	
	public void analyseController(DataController controller){
		String format = controller.getFormat();
		if(format.equals("old")){
			
		}else if(format.equals("new")){
			
		}
	}
	

	public void addMapToDB(HashMap<String, StatisticObject> map,
			String collectionName) {
		System.out.println("Adding statisic map to database... (" + map.size()
				+ " items)");
		collection = database.getCollection(collectionName);
		for (Map.Entry<String, StatisticObject> entry : map.entrySet()) {
			BasicDBObject document = (BasicDBObject) JSON.parse(gson
					.toJson(entry.getValue()));
			collection.insert(document);
		}
	}

	public void addToMap(HashMap<String, StatisticObject> map, String text) {
		RegistryListItem company = new RegistryListItem(
				dataController.getRegistrationNumber(),
				dataController.getOrganisationName());
		if (!map.containsKey(text)) {
			StatisticObject statObject = new StatisticObject();
			statObject.setType(text);
			statObject.addCompany(company);
			map.put(text, statObject);
		} else {
			map.get(text).addCompany(company);
		}
	}

	public void dropCollections(String[] collectionNames) {
		for (String collectionName : collectionNames) {
			collection = database.getCollection(collectionName);
			collection.drop();
		}
	}

}
