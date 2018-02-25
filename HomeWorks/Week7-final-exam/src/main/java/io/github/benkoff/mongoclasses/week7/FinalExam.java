package io.github.benkoff.mongoclasses.week7;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
 * Created by Ben Novikov on 2018-02-22
 */
public class FinalExam {

    public static void main(String[] args) {

        String databaseName = "enron";
        String collectionName = "messages";
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase(databaseName);
        MongoCollection<Document> messages = db.getCollection(collectionName);
        try {
            List<Document> headers = new ArrayList<>();

//            try (MongoCursor<Document> cursor = messages.find().iterator()) {
//                while (cursor.hasNext()) {
//                    Document message = cursor.next();
//
//                }
//            }

            System.out.println(headers.isEmpty() ? "nothing found" : headers.size() + " entries");
        } finally {
            client.close();
        }
    }
}