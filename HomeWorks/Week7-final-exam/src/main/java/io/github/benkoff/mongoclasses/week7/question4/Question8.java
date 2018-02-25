package io.github.benkoff.mongoclasses.week7.question4;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by Ben Novikov on 2018, February, 26
 */
public class Question8 {
    public static void main(String[] args) {
        MongoClient c =  new MongoClient();
        MongoDatabase db = c.getDatabase("test");
        MongoCollection<Document> animals = db.getCollection("animals");
        animals.drop();

        Document animal = new Document("animal", "monkey");

        try {
            animals.insertOne(animal);
            animal.remove("animal");
            animal.append("animal", "cat");
            animals.insertOne(animal);
            animal.remove("animal");
            animal.append("animal", "lion");
            animals.insertOne(animal);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("Animals collection contains: " + animals.count() + " animals");
    }
}
