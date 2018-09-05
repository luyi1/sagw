package com.ge.digital.gearbox.mapper;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.Bytes;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

@Component
public class CustomMongoRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	public DBCursor getDBCursor(Date date, String field, String collectionName) {
		DBObject query = new BasicDBObject(); // setup the query criteria 设置查询条件
		query.put(field, (new BasicDBObject("$lte", date)));
		DBCollection dbCollection = mongoTemplate.getCollection(collectionName);
		DBCursor dbCursor = dbCollection.find(query).addOption(Bytes.QUERYOPTION_NOTIMEOUT);
		return dbCursor;
	}

	public DBCursor getDBCursor(Date startTime, Date endTime, String collectionName) {
		String field = "timestamp";
		if (collectionName.equals("productionProc")) {
			field = "lineExitDate";
		}
		DBObject query = new BasicDBObject(); // setup the query criteria 设置查询条件
		query.put(field, BasicDBObjectBuilder.start("$lt", endTime).add("$gte", startTime).get());
		DBCollection dbCollection = mongoTemplate.getCollection(collectionName);
		DBCursor dbCursor = dbCollection.find(query).addOption(Bytes.QUERYOPTION_NOTIMEOUT);
		return dbCursor;
	}

	public WriteResult remove(Date date, String field, String collectionName) {
		DBObject query = new BasicDBObject(); // setup the query criteria 设置查询条件
		query.put(field, (new BasicDBObject("$lte", date)));
		DBCollection dbCollection = mongoTemplate.getCollection(collectionName);
		return dbCollection.remove(query);
	}

	public WriteResult remove(Date startTime, Date endTime, String collectionName) {
		String field = "timestamp";
		if (collectionName.equals("productionProc"))
			field = "lineExitDate";
		DBObject query = new BasicDBObject();
		query.put(field, BasicDBObjectBuilder.start("$lt", endTime).add("$gte", startTime).get());
		DBCollection dbCollection = mongoTemplate.getCollection(collectionName);
		return dbCollection.remove(query);
	}

	public void save(DBObject object) {
		mongoTemplate.save(object);
	}

}
