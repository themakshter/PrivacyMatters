package parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.Util;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;

import models.AdvancedStatisticObject;
import models.DataController;
import models.GeneralStatistics;
import models.NatureOfWorkObject;
import models.NewFormat;
import models.OtherPurpose;
import models.Purpose;
import models.RegistryListItem;
import models.StatisticObject;

/**
 * Class to add statistics from the created data controller registry
 * 
 * @author Mohammad Ali
 * 
 */
public class StatisticsBuilder {
	private PrintWriter out;

	private HashMap<String, StatisticObject> dataClassMap, dataSubjectMap,
			sensitiveDataMap, dataDiscloseeMap;
	private HashMap<String, NatureOfWorkObject> natureOfWorkMap;
	private HashMap<String, AdvancedStatisticObject> dataPurposeMap;
	private ArrayList<Integer> dataClasses, sensitiveData, dataSubjects,
			dataDisclosees;

	private static MongoClientURI dbURI;
	private MongoClient client;
	private DB database;
	private DBCollection collection;
	private GeneralStatistics generalStats;
	private DataController dataController;
	private Gson gson;

	/**
	 * Constructor for class
	 * 
	 * @throws Exception
	 *             connection or IO errors
	 */
	public StatisticsBuilder() throws Exception {
		// To convert easily b/w JSON
		gson = new Gson();

		generalStats = new GeneralStatistics();

		out = new PrintWriter(new BufferedWriter(new FileWriter(
				"files/other/builderStatistics.txt")));

		// connect to db
		dbURI = new MongoClientURI(
				"mongodb://admin:incorrect@ds033629.mongolab.com:33629/data_controllers");
		client = new MongoClient(dbURI);

		natureOfWorkMap = new HashMap<String, NatureOfWorkObject>();
		dataPurposeMap = new HashMap<String, AdvancedStatisticObject>();
		dataClassMap = new HashMap<String, StatisticObject>();
		dataSubjectMap = new HashMap<String, StatisticObject>();
		sensitiveDataMap = new HashMap<String, StatisticObject>();
		dataDiscloseeMap = new HashMap<String, StatisticObject>();

		dataClasses = new ArrayList<Integer>();
		sensitiveData = new ArrayList<Integer>();
		dataSubjects = new ArrayList<Integer>();
		dataDisclosees = new ArrayList<Integer>();

		// client = new MongoClient("localhost",27017);
		// database = client.getDB("dataControllers");

		// get the database
		database = client.getDB(dbURI.getDatabase());

		// clean all the statistics collections (by dropping them)
		String[] collections = { "generalStats", "natureOfWorkStats",
				"purposeStats", "dataClassStats", "dataSubjectStats",
				"dataDiscloseeStats", "sensitiveDataStats" };
		dropCollections(collections);
	}

	/**
	 * Method for building statistics for the registry
	 */
	public void buildStatistics() {
		String json;
		collection = database.getCollection("registry");
		DBCursor cursor = collection.find();
		generalStats.setRecordCount(cursor.size());

		// Iterate through the registry
		while (cursor.hasNext()) {
			json = cursor.next().toString();
			dataController = gson.fromJson(json, DataController.class);
			analyseController(dataController);
		}

		generalStats.setPurposesCount(dataPurposeMap.size());
		generalStats.setDataClassesCount(dataClassMap.size());
		generalStats.setSensitiveDataCount(sensitiveDataMap.size());
		generalStats.setDataSubjectsCount(dataSubjectMap.size());
		generalStats.setDataDiscloseesCount(dataDiscloseeMap.size());

		generalStats.setMedianDataClasses(Util.calculateMedian(dataClasses));
		generalStats
				.setMedianSensitiveData(Util.calculateMedian(sensitiveData));
		generalStats.setMedianDataSubjects(Util.calculateMedian(dataSubjects));
		generalStats.setMedianDataDisclosees(Util
				.calculateMedian(dataDisclosees));

		addPurposesToDB();
		addNatureOfWorkToDB();

		addMapToDB(sensitiveDataMap, "sensitiveDataStats");

		addMapToDB(dataClassMap, "dataClassStats");
		addMapToDB(dataSubjectMap, "dataSubjectStats");
		addMapToDB(dataDiscloseeMap, "dataDiscloseeStats");

		collection = database.getCollection("generalStats");
		BasicDBObject document = (BasicDBObject) JSON.parse(gson
				.toJson(generalStats));
		collection.insert(document);
		client.close();
		out.close();
		System.out.println("Added statistics");
	}

	/**
	 * Add values from data controller for statistics
	 * 
	 * @param controller
	 *            data controller to retrieve values from
	 */
	public void analyseController(DataController controller) {

		// Companies House number
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
				addToPurpose(purpose.getPurpose());

				// data classes
				for (String dataClass : purpose.getDataClasses()) {
					addToMap(dataClassMap, dataClass);
				}
				addForMedianToPurpose("dataClass", purpose);

				// data subjects
				for (String dataSubject : purpose.getDataSubjects()) {
					addToMap(dataSubjectMap, dataSubject);
				}
				addForMedianToPurpose("dataSubject", purpose);

				// data disclosees
				for (String dataDisclosee : purpose.getDataDisclosees()) {
					addToMap(dataDiscloseeMap, dataDisclosee);
				}
				addForMedianToPurpose("dataDisclosee", purpose);

			}

			// adding overall numbers for stats
			dataClasses.add(controller.getNewFormat().getDataClasses().size());
			dataSubjects
					.add(controller.getNewFormat().getDataSubjects().size());
			dataDisclosees.add(controller.getNewFormat().getDataDisclosees()
					.size());

		} else if (format.equals("new")) {
			generalStats.incrementNewBlobCount();
			NewFormat newFormat = controller.getNewFormat();

			addToNatureOfWork();

			// data classes
			for (String dataClass : newFormat.getDataClasses()) {
				if (checkValue(dataClass, newFormat.getDataClasses())) {
					addToMap(dataClassMap, dataClass);
				}
			}
			addForMedianToNatureOfWork("dataClass");
			dataClasses.add(newFormat.getDataClasses().size());

			// sensitive data
			for (String sensitiveData : newFormat.getSensitiveData()) {
				addToMap(sensitiveDataMap, sensitiveData);
			}
			addForMedianToNatureOfWork("sensitiveData");
			sensitiveData.add(newFormat.getSensitiveData().size());

			// data subjects
			for (String dataSubject : newFormat.getDataSubjects()) {
				if (checkValue(dataSubject, newFormat.getDataSubjects())) {
					addToMap(dataSubjectMap, dataSubject);

				}
			}
			addForMedianToNatureOfWork("dataSubject");
			dataSubjects.add(newFormat.getDataSubjects().size());

			// data disclosees
			for (String dataDisclosee : newFormat.getDataDisclosees()) {
				if (checkValue(dataDisclosee, newFormat.getDataDisclosees())) {
					addToMap(dataDiscloseeMap, dataDisclosee);
				}
			}
			addForMedianToNatureOfWork("dataDisclosee");
			dataDisclosees.add(newFormat.getDataDisclosees().size());

			// Other purposes
			for (@SuppressWarnings("unused")
			OtherPurpose purpose : newFormat.getOtherPurposes()) {
				// addToMap(dataPurposeMap, purpose.getPurpose());
			}
		}
	}

	/**
	 * Checks if the number of words are less than 15 (is not a number of
	 * values, just one). 15 is just set as a limit, there might be some cases
	 * which might not be added
	 * 
	 * @param item
	 *            one value from the array
	 * @param list
	 *            the array itself
	 * @return true if this is singular item, false if not
	 */
	public boolean checkValue(String item, ArrayList<String> list) {
		return (item.split(" ").length < 15 && list.size() > 1);
	}

	/**
	 * Adds statistics for data controllers nature of work
	 */
	public void addNatureOfWorkToDB() {
		NatureOfWorkObject object;
		System.out.println("Adding statisic map to database for natureOfWork"
				+ "... (" + natureOfWorkMap.size() + " items)");
		collection = database.getCollection("natureOfWorkStats");
		for (Map.Entry<String, NatureOfWorkObject> entry : natureOfWorkMap
				.entrySet()) {
			object = entry.getValue();
			object.calculateMedians();
			BasicDBObject document = (BasicDBObject) JSON.parse(gson
					.toJson(object));
			collection.insert(document);
		}
	}

	/**
	 * Adds statistics for data controller data collection purposes
	 */
	public void addPurposesToDB() {
		AdvancedStatisticObject object;
		System.out.println("Adding statisic map to database for purposes"
				+ "... (" + dataPurposeMap.size() + " items)");
		collection = database.getCollection("purposeStats");
		for (Map.Entry<String, AdvancedStatisticObject> entry : dataPurposeMap
				.entrySet()) {
			object = entry.getValue();
			object.calculateMedians();
			BasicDBObject document = (BasicDBObject) JSON.parse(gson
					.toJson(object));
			collection.insert(document);
		}
	}

	/**
	 * Generic method to add statistics to the database
	 * 
	 * @param map
	 *            HashMap of the statistics to add
	 * @param collectionName
	 *            Collection on which to add the statistics
	 */
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

	/**
	 * Add company to statistic object
	 * 
	 * @param map
	 *            Map to add to
	 * @param text
	 *            Value to add
	 */
	public void addToMap(HashMap<String, StatisticObject> map, String text) {
		RegistryListItem company = new RegistryListItem(
				dataController.getRegistrationNumber(),
				dataController.getOrganisationName());
		// Make new statistic if it does not exist
		if (!map.containsKey(text)) {
			StatisticObject statObject = new StatisticObject();
			statObject.setType(text);
			statObject.addCompany(company);
			map.put(text, statObject);
		} else {
			map.get(text).addCompany(company);
		}
	}

	/**
	 * Add company to Nature of work statistics
	 */
	public void addToNatureOfWork() {
		String natureOfWork = dataController.getNewFormat().getNatureOfWork();
		RegistryListItem company = new RegistryListItem(
				dataController.getRegistrationNumber(),
				dataController.getOrganisationName());
		if (!natureOfWorkMap.containsKey(natureOfWork)) {
			NatureOfWorkObject statObject = new NatureOfWorkObject();
			statObject.setType(natureOfWork);
			statObject.addCompany(company);
			natureOfWorkMap.put(natureOfWork, statObject);
		} else {
			natureOfWorkMap.get(natureOfWork).addCompany(company);
		}
	}

	/**
	 * Add company to data collection purpose statistics
	 * 
	 * @param purpose
	 */
	public void addToPurpose(String purpose) {
		RegistryListItem company = new RegistryListItem(
				dataController.getRegistrationNumber(),
				dataController.getOrganisationName());
		if (!dataPurposeMap.containsKey(purpose)) {
			AdvancedStatisticObject statObject = new AdvancedStatisticObject();
			statObject.setType(purpose);
			statObject.addCompany(company);
			dataPurposeMap.put(purpose, statObject);
		} else {
			dataPurposeMap.get(purpose).addCompany(company);
		}
	}

	/**
	 * Add median values of data collection types to different types of
	 * companies (for new format)
	 * 
	 * @param type
	 *            type of data collection
	 */
	public void addForMedianToNatureOfWork(String type) {
		String natureOfWork = dataController.getNewFormat().getNatureOfWork();
		switch (type) {
		case "dataClass":
			natureOfWorkMap.get(natureOfWork).addDataClass(
					dataController.getNewFormat().getDataClasses().size());
			break;
		case "sensitiveData":
			natureOfWorkMap.get(natureOfWork).addSensitiveData(
					dataController.getNewFormat().getSensitiveData().size());
			break;
		case "dataSubject":
			natureOfWorkMap.get(natureOfWork).addDataSubject(
					dataController.getNewFormat().getDataSubjects().size());
			break;
		case "dataDisclosee":
			natureOfWorkMap.get(natureOfWork).addDataDisclosee(
					dataController.getNewFormat().getDataDisclosees().size());
			break;
		default:
			break;
		}
	}

	/**
	 * Add median values of data collection types to data collection purposes
	 * (for old format)
	 * 
	 * @param type
	 *            type of data collection
	 * @param purpose
	 *            data collection purpose
	 */
	public void addForMedianToPurpose(String type, Purpose purpose) {
		String purposeName = purpose.getPurpose();
		switch (type) {
		case "dataClass":
			dataPurposeMap.get(purposeName).addDataClass(
					purpose.getDataClasses().size());
			break;
		case "dataSubject":
			dataPurposeMap.get(purposeName).addDataSubject(
					purpose.getDataSubjects().size());
			break;
		case "dataDisclosee":
			dataPurposeMap.get(purposeName).addDataDisclosee(
					purpose.getDataDisclosees().size());
			break;
		default:
			break;
		}
	}

	/**
	 * Drops the collections specified in the MongoDB database
	 * 
	 * @param collectionNames
	 *            the collections to be dropped
	 */
	public void dropCollections(String[] collectionNames) {
		for (String collectionName : collectionNames) {
			collection = database.getCollection(collectionName);
			collection.drop();
		}
	}

}
