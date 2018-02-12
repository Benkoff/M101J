package io.github.benkoff.mongoclasses.week5;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Created by Ben Novikov on 2018-01-30
 *
 * Before you run this import data to your database:
 * mongoimport --drop -d test -c grades2 grades.json
 */
public class Homework53 {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("test");
        MongoCollection<Document> collection = db.getCollection("grades2");

        collection.aggregate(Arrays.asList(
                new Document("$unwind", "$scores"),
                new Document("$match", new Document("scores.type", new Document("$ne", "quiz"))),
                new Document("$group",
                        new Document("_id",
                                new Document("student_id", "$student_id").append("class_id", "$class_id"))
                                .append("avg", new Document("$avg", "$scores.score"))),
                new Document("$group",
                        new Document("_id", "$_id.class_id").append("avg", new Document("$avg", "$avg"))),
                new Document("$sort", new Document("avg", -1)),
                new Document("$limit", 1))
        ).into(new ArrayList<Document>()).stream().map(Document::toJson).forEach(System.out::println);

        client.close();
    }
}
