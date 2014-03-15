package parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import models.NewFormat;
import models.OtherPurpose;
import models.Purpose;
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
		out = new PrintWriter(new BufferedWriter(new FileWriter(
				"files/other/builderStatistics.txt")));
		dbURI = new MongoClientURI(
				"mongodb://admin:incorrect@ds033629.mongolab.com:33629/data_controllers");
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
				"dataDiscloseeStats","sensitiveDataStats" };
		dropCollections(collections);
	}

	public void buildStatistics() {
		String json;
		collection = database.getCollection("registry");
		DBCursor cursor = collection.find();
		generalStats.setRecordCount(cursor.size());

		while (cursor.hasNext()) {
			json = cursor.next().toString();
			dataController = gson.fromJson(json, DataController.class);
			analyseController(dataController);
		}

		generalStats.setPurposesCount(dataPurposeSet.size());
		generalStats.setDataClassesCount(dataClassSet.size());
		generalStats.setSensitiveDataCount(sensitiveDataSet.size());
		generalStats.setDataSubjectsCount(dataSubjectSet.size());
		generalStats.setDataDiscloseesCount(dataDiscloseeSet.size());

		addMapToDB(natureOfWorkMap, "natureOfWorkStats");
		addMapToDB(sensitiveDataMap, "sensitiveDataStats");
		addMapToDB(dataPurposeMap, "purposesStats");
		addMapToDB(dataClassMap, "dataClassesStats");
		addMapToDB(dataSubjectMap, "dataSubjectsStats");
		addMapToDB(dataDiscloseeMap, "dataDiscloseeStats");
		
		collection = database.getCollection("generalStats");
		BasicDBObject document = (BasicDBObject) JSON.parse(gson.toJson(generalStats));
		collection.insert(document);
		client.close();
		out.close();
		System.out.println("Added statistics");
	}

	public void analyseController(DataController controller) {
		// Companies House
		if (controller.getCompaniesHouseNumber().length() == 8) {
			generalStats.incrementCompaniesHouseCount();
		}

		// Trading Name
		if (!controller.getTradingName().equals("(not given)")) {
			generalStats.incrementTradingNameCount();
		}

		// Address
		if (controller.getAddress().size() > 0) {
			generalStats.incrementAddressCount();
		}

		// Postcode
		if (!controller.getPostcode().equals("(not given)")) {
			generalStats.incrementPostcodeCount();
		}

		String format = controller.getFormat();
		if (format.equals("old")) {
			generalStats.incrementOldBlobCount();
			for (Purpose purpose : controller.getOldFormat()) {
				// purpose
				dataPurposeSet.add(purpose.getPurpose());
				addToMap(dataPurposeMap, purpose.getPurpose());

				// data classes
				for (String dataClass : purpose.getDataClasses()) {
					dataClassSet.add(dataClass);
					addToMap(dataClassMap, dataClass);
				}

				// data subjects
				for (String dataSubject : purpose.getDataSubjects()) {
					dataSubjectSet.add(dataSubject);
					addToMap(dataSubjectMap, dataSubject);
				}

				// data disclosees
				for (String dataDisclosee : purpose.getDataDisclosees()) {
					dataDiscloseeSet.add(dataDisclosee);
					addToMap(dataDiscloseeMap, dataDisclosee);
				}

			}

		} else if (format.equals("new")) {
			generalStats.incrementNewBlobCount();

			NewFormat newFormat = controller.getNewFormat();

			addToMap(natureOfWorkMap, newFormat.getNatureOfWork());
			natureOfWorkSet.add(newFormat.getNatureOfWork());

			// data classes
			for (String dataClass : newFormat.getDataClasses()) {
				if (checkValue(dataClass, newFormat.getDataClasses())) {
					dataClassSet.add(dataClass);
					addToMap(dataClassMap, dataClass);
				}
			}

			// sensitive data
			for (String sensitiveData : newFormat.getSensitiveData()) {
				sensitiveDataSet.add(sensitiveData);
				addToMap(sensitiveDataMap, sensitiveData);
			}
			// data subjects
			for (String dataSubject : newFormat.getDataSubjects()) {
				if (checkValue(dataSubject, newFormat.getDataSubjects())) {
					dataSubjectSet.add(dataSubject);
					addToMap(dataSubjectMap, dataSubject);
				}
			}

			// data disclosees
			for (String dataDisclosee : newFormat.getDataDisclosees()) {
				if (checkValue(dataDisclosee, newFormat.getDataDisclosees())) {
					dataDiscloseeSet.add(dataDisclosee);
					addToMap(dataDiscloseeMap, dataDisclosee);
				}
			}

			// Other purposes
			for (OtherPurpose purpose : newFormat.getOtherPurposes()) {
				dataPurposeSet.add(purpose.getPurpose());
				addToMap(dataPurposeMap, purpose.getPurpose());
			}
		}
	}

	public boolean checkValue(String item, ArrayList<String> list) {
		return (item.split(" ").length < 15 && list.size() > 1);
	}

	public void addMapToDB(HashMap<String, StatisticObject> map,
			String collectionName) {
		System.out.println("Adding statisic map to database for "
				+ collectionName.replace("Stats", "") + "... (" + map.size()
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
