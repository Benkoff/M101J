# Week 7. Final Exam
## Question 1
Please download the <a href = "https://s3.amazonaws.com/edu-downloads.10gen.com/enron/enron.zip">Enron email dataset (link here)</a>, unzip it and then restore it using mongorestore. Note that this is an abbreviated version of the full corpus. There should be 120,477 documents after restore.

The command for mongorestore is:

mongorestore --port <port number> -d enron -c messages <path to BSON file>
Inspect a few of the documents to get a basic understanding of the structure. Enron was an American corporation that engaged in a widespread accounting fraud and subsequently failed.

In this dataset, each document is an email message. Like all Email messages, there is one sender but there can be multiple recipients.

Construct a query to calculate the number of messages sent by Andrew Fastow, CFO, to Jeff Skilling, the president. Andrew Fastow's email addess was andrew.fastow@enron.com. Jeff Skilling's email was jeff.skilling@enron.com.

For reference, the number of email messages from Andrew Fastow to John Lavorato (john.lavorato@enron.com) was 1.

#### Compass query: </br>
``` {'headers.From': RegExp('andrew.fastow@enron.com'), 'headers.To': RegExp('jeff.skilling@enron.com')} ```

<details>
<summary>The answer is here</summary>
<p> 3 </p> 
</details>

## Question 2

Please use the Enron dataset you imported for the previous problem. For this question you will use the aggregation framework to figure out pairs of people that tend to communicate a lot. To do this, you will need to unwind the To list for each message.

This problem is a little tricky because a recipient may appear more than once in the To list for a message. You will need to fix that in a stage of the aggregation before doing your grouping and counting of (sender, recipient) pairs.

Which pair of people have the greatest number of messages in the dataset?

#### Shell query: </br>
``` 
db.messages.aggregate([
	{$project: 
		{from: "$headers.From", to: "$headers.To"}
	},
	{$unwind: "$to"},
	{$group : 
		{_id : 
			{_id: "$_id", from: "$from", to: "$to" }
		}
	},
	{$group : 
		{_id : 
			{from: "$_id.from", to: "$_id.to" }, 
			count: {$sum: 1}
		}
	},
	{$sort: {count: -1}},
	{$limit: 1}
], {allowDiskUse: true})
```

<details>
<summary>The answer is here</summary>
<p> { "_id" : { "from" : "susan.mara@enron.com", "to" : "jeff.dasovich@enron.com" }, "count" : 750 } </p> 
</details>

## Question 3
In this problem you will update a document in the Enron dataset to illustrate your mastery of updating documents from the shell.

The command for mongorestore is:
```mongorestore --port <port number> -d enron -c messages <path to BSON file>```
This will put the documents into the "enron.messages" namespace.

Please add the email address "mrpotatohead@mongodb.com" to the list of addresses in the "headers.To" array for the document 
with "headers.Message-ID" of "<8147308.1075851042335.JavaMail.evans@thyme>"

After you have completed that task, please download final3-validate-mongo-shell.js from the Download Handout link and run
```mongo final3-validate-mongo-shell.js```
to get the validation code and put it in the box below without any extra spaces. The validation script assumes that it is 
connecting to a simple mongo instance on the standard port on localhost.

Note: The validation script will look for the document in the "enron.messages" namespace. If the documents aren't there, 
then the validation will not work.

#### Mongo Shell command: </br>
```db.messages.update({"headers.Message-ID":"<8147308.1075851042335.JavaMail.evans@thyme>"},{$addToSet:{"headers.To":"mrpotatohead@mongodb.com"}})```

<details>
<summary>The answer is here</summary>
<p> Welcome to the Final Exam Q3 Checker. My job is to make sure you correctly updated the document
    Final Exam Q3 Validated successfully!
    Your validation code is: vOnRg05kwcqyEFSve96R </p> 
</details>

## Question 4

```
// from the mongo shell
use blog;
db.posts.drop();

// from command line
mongoimport --drop -d blog -c posts posts.json
```
run package io.github.benkoff.mongoclasses.week7.question4 **BlogController.main()**

<details>
<summary>The answer is here</summary>
<p> evaluated by MongoProc </p> 
</details>

## Question 5

The query against the collection uses all fields except _id: 
```
db.stuff.find({'a':{'$lt':10000}, 'b':{'$gt': 5000}}, {'a':1, 'c':1}).sort({'c':-1})
```
<details>
<summary>The answer is here</summary>
<p>so all the indicies except one (_id based) could be used to assist the query</p> 
</details>

## Question 6

Suppose you have a collection of students of the following form:
```
{
    "_id" : ObjectId("50c598f582094fb5f92efb96"),
    "first_name" : "John",
    "last_name" : "Doe",
    "date_of_admission" : ISODate("2010-02-21T05:00:00Z"),
    "residence_hall" : "Fairweather",
    "has_car" : true,
    "student_id" : "2348023902",
    "current_classes" : [
        "His343",
        "Math234",
        "Phy123",
        "Art232"
    ]
}
```
Now suppose that basic inserts into the collection, which only include the last name, first name and student_id, 
are too slow (we can't do enough of them per second from our program). 
And while there are many potential application/hardware solutions such as batching, increasing bandwidth (or RAM), etc., 
which of the following listed options could potentially **improve the speed of inserts**?

Check all that apply.
<details>
<summary>The answer is here</summary>
<p> 
* Add an index on last_name, first_name if one does not already exist. -- indexes affect reading not writing speed. 
Negative </br>
* Remove all indexes from the collection, leaving only the index on _id in place -- removing additional logic should 
probably speed the whole system up. Positive </br>
* Provide a hint to MongoDB that it should not use an index for the inserts -- lol. Negative </br>
* Set w=0, j=false on writes -- The w option requests acknowledgement that the write operation has propagated to a 
specified number of mongod instances or to mongod instances with specified tags. The j option requests acknowledgement 
from MongoDB that the write operation has been written to the journal. So waiting for nothing definitely takes less time 
than waiting for any response we could get at all. Positive </br>
* Build a replica set and insert data into the secondary nodes to free up the primary nodes -- A secondary maintains a 
copy of the primary's data set. Negative </br> </p> 
</details>

## Question 7

Your task is to write a program to remove every image from the images collection that appears in no album. 
Or put another way, if an image does not appear in at least one album, it's an orphan and should be removed from the 
images collection.

Download final7.zip from Download Handout link and use mongoimport to import the collections in albums.json and images.json.

When you are done removing the orphan images from the collection, there should be 89,737 documents in the images collection.

Hint: you might consider creating an index or two or your program will take a long time to run. As as a sanity check, 
there are 49,887 images that are tagged 'sunrises' before you remove the images.

What are the total number of images with the tag "sunrises" after the removal of orphans?

```
unzip
MongoDB shell>use q7
C:\...>cd C:\Workshop\Mongo\m101j_temp\final7__q7\final7 
C:\Workshop\Mongo\m101j_temp\final7__q7\final7>mongoimport -d q7 -c albums albums.json
C:\Workshop\Mongo\m101j_temp\final7__q7\final7>mongoimport -d q7 -c images images.json
```

Run package io.github.benkoff.mongoclasses.week7 **Question7.main()**

<details>
<summary>The answer is here</summary>
<p>
 Images in albums: 89737 </br>
 Images after orphans removal: 89737 </br>
 Images containing 'sunrises' tag: 44787
</p> 
</details>

## Question 8

Supposed we executed the following Java code. How many animals will be inserted into the "animals" collection?
```
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class InsertTest {
        public static void main(String[] args) {
            MongoClient c =  new MongoClient();
            MongoDatabase db = c.getDatabase("test");
            MongoCollection<Document> animals = db.getCollection("animals");

           Document animal = new Document("animal", "monkey");

            animals.insertOne(animal);
            animal.remove("animal");
            animal.append("animal", "cat");
            animals.insertOne(animal);
            animal.remove("animal");
            animal.append("animal", "lion");
            animals.insertOne(animal);
        }
}
```

Run package io.github.benkoff.mongoclasses.week7 **Question8.main()**

<details>
<summary>The answer is here</summary>
<p>
 E11000 duplicate key error collection: test.animals index: _id_ dup key: { : ObjectId('5a93415a4a21fa1838521803') } </br>
 Animals collection contains: 1 animals
</p> 
</details>

## Question 9

Imagine an electronic medical record database designed to hold the medical records of every individual in the United States. 
Because each person has more than 16MB of medical history and records, it's not feasible to have a single document for 
every patient. Instead, there is a patient collection that contains basic information on each person and maps the person 
to a patient_id, and a record collection that contains one document for each test or procedure. One patient may have 
dozens or even hundreds of documents in the record collection.

We need to decide on a shard key to shard the record collection. What's the best shard key for the record collection, 
provided that we are willing to run inefficient scatter-gather operations to do infrequent research and run studies on 
various diseases and cohorts? That is, think mostly about the operational aspects of such a system. And by operational, 
we mean, think about what the most common operations that this systems needs to perform day in and day out.

Choose the best answer:

* patient_id
* _id
* Primary care physician (your principal doctor that handles everyday problems)
* Date and time when medical record was created
* Patient first name
* Patient last name

<details>
<summary>The answer is here</summary>
<p> patient_id </p> 
</details>

## Question 10

Check below all the statements that are true about the way MongoDB handled this query.

Check all that apply:

* The query used an index to figure out which documents match the find criteria.
* The query avoided sorting the documents because it was able to use an index's ordering.
* The query returned 120,477 documents.
* The query scanned every document in the collection.

<details>
<summary>The answer is here</summary>
<p> 
 The query avoided sorting the documents because it was able to use an index's ordering. </br>
 The query scanned every document in the collection.
</p> 
</details>

