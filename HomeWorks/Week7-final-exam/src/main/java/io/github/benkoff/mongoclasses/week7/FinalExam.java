package io.github.benkoff.mongoclasses.week7;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/*
 * Created by Ben Novikov on 2018-02-22
 */
public class FinalExam {

    public static void main(String[] args) {

        String databaseName = "";
        String collectionName = "";
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase(databaseName);
        MongoCollection<Document> students = db.getCollection(collectionName);

        client.close();
    }
}