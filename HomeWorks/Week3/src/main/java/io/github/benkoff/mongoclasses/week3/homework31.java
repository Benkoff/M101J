package io.github.benkoff.mongoclasses.week3;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;

/*
 * Created by Ben Novikov on 2018-01-30
 */
public class homework31 {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("school");
        MongoCollection<Document> students = db.getCollection("students");

        try(MongoCursor<Document> cursor = students.find().iterator()) {

            // Get all the students
            while (cursor.hasNext()) {
                Document student = cursor.next();
                List<Document> scores = (List<Document>) student.get("scores");

                // Find the lowest homework score
                Document minScore = scores.stream()
                        .filter(o -> o.getString("type").equals("homework"))
                        .reduce((a, b) -> a.getDouble("score") < b.getDouble("score") ? a : b)
                        .orElse(null);

                // Remove the lowest score
                if (minScore != null)
                    scores.remove(minScore);

                // Update the record
                students.updateOne(Filters.eq("_id", student.get("_id")),
                        new Document("$set", new Document("scores", scores)));
            }

            // Get the student with the highest average in the class
            AggregateIterable<Document> results = students.aggregate(Arrays.asList(
                    Aggregates.unwind("$scores"),
                    Aggregates.group("$_id", Accumulators.avg("average", "$scores.score")),
                    Aggregates.sort(Sorts.descending("average")), Aggregates.limit(1)));

            // There should be only one result. Print it
            System.out.println("Solution : " + results.iterator().next().toJson());
        }

        client.close();
    }
}
