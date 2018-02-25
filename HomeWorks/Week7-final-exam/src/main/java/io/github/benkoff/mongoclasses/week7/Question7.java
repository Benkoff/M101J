package io.github.benkoff.mongoclasses.week7;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * Created by Ben Novikov on 2018-02-22
 */
public class Question7 {

    public static void main(String[] args) {

        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("q7");
        MongoCollection<Document> albums = db.getCollection("albums");
        MongoCollection<Document> images = db.getCollection("images");

        try {
            // get all the albums from the collection and add to an images set
            List<Document> albumsList = (List<Document>) albums.find().into(new ArrayList<Document>());
            Set<Integer> imagesSet = new HashSet<>();
            for (Document doc : albumsList) {
                imagesSet.addAll((List<Integer>) doc.get("images"));
            }
            System.out.println("Images in albums: " + imagesSet.size());

            // remove duplicates from images collection
            Map<Integer, Boolean> imagesInAlbums = new HashMap<>();
            imagesSet.forEach(x -> imagesInAlbums.put(x, false));
            try (MongoCursor<Document> cursor = images.find().iterator()) {
                while (cursor.hasNext()) {
                    Document image = cursor.next();
                    Integer id = (Integer) image.get("_id");
                    if (imagesInAlbums.containsKey(id) && !imagesInAlbums.get(id)) {
                        imagesInAlbums.put(id, true);
                    } else {
                        images.deleteOne(image);
                    }
                }
            }
            System.out.println("Images after orphans removal: " + images.count());

            System.out.println("Images containing 'sunrises' tag: " + images.count(new Document("tags", "sunrises")));

        } finally {
            client.close();
        }
    }
}