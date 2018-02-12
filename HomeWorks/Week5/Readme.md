# Week 5. Aggregation Framework

Download the zips.json file from the Download Handout link and import it into your local Mongo instance with this command:
```
mongoimport --drop -d agg -c zips zips.json
mongo
```
In mongo shell Quiz queries:
```
use agg
db.zips.aggregate([{"$group":{"_id":"$state", "population":{$sum:"$pop"}}}])
db.zips.aggregate([{"$group":{"_id":"$state", "average_pop":{$avg:"$pop"}}}])
db.zips.aggregate([{"$group":{"_id":"$city", "postal_codes":{"$addToSet":"$_id"}}}])
db.zips.aggregate([{"$group":{"_id":"$city", "postal_codes":{"$push":"$_id"}}}])
db.zips.aggregate([{"$group":{"_id":"$state", "pop":{"$max":"$pop"}}}])

db.zips.aggregate([{$project:{_id:0,"city": {$toLower:"$city"},	"pop" : 1, "state": 1, "zip":"$_id"}}])
db.zips.aggregate([{$match:{"pop":{$gt:100000}}}])
db.zips.aggregate([{$sort:{state:1, city: 1}}])
```

## HomeWork 5.1
<a href = "https://university.mongodb.com/static/MongoDB_2018_M101J_January/handouts/posts.json">
Get here</a> and import the data to the database

```
mongoimport --drop -d blog -c posts posts.json
mongo

use blog
db.posts.aggregate({$unwind:"$comments"},{$group:{_id:"$comments.author", num:{$sum:1}}},{$sort:{num:-1}},{$limit:1})
```
<details>
<summary>HW 5.1 answer is here</summary>
<p>{ "_id" : "Elizabet Kleine", "num" : 503 }</p> 
</details>

## HomeWork 5.2
<a href = "https://university.mongodb.com/static/MongoDB_2018_M101J_January/handouts/small_zips.json">
Download</a> and import data: 

```
mongoimport --drop -d test -c zips small_zips.json
mongo

use test
db.posts.aggregate({$match:{state:{$or:[{"CA"},{"NY"}]}}},{$group:{_id:{state:"$state",city:"$city"},pop:{$sum:"$pop"}}},
{$match:{pop:{$gt:25000}}},{$group:{_id : null,pop:{$avg:"$pop"}}})
```
<details>
<summary>HW 5.2 answer is here</summary>
<p>{ "_id" : 0, "pop" : 44804.782608695656 } </p> 
</details>

## HomeWork 5.3

Before you run Homework53.java, 
<a href="https://university.mongodb.com/static/MongoDB_2018_M101J_January/handouts/Small_grades_file.zip">
download</a> and import data to your database:

```
mongoimport --drop -d test -c grades2 grades.json
```
<details>
<summary>HW 5.3 answer is here</summary>
<p>{ "_id" : 1, "avg" : 64.50642324269174 } </p> 
</details>

## HomeWork 5.4

<a href="https://university.mongodb.com/static/MongoDB_2018_M101J_January/handouts/zips.json">
Download Handouts</a> to the database.

```
mongoimport --drop -d test -c zips zips.json

db.zips.aggregate([
    {$project: {first_char: {$substr : ["$city",0,1]},pop : 1, city : "$city", zip : "$_id", state : 1}},
	{$match: {$or: [
	    {first_char:"B"}, 
	    {first_char:"D"}, 
	    {first_char:"O"}, 
	    {first_char:"G"}, 
	    {first_char:"N"},
	    {first_char:"M"}]}},
    {$group: {_id: null, pop: {$sum: "$pop"}}}
])
```

<details>
<summary>HW 5.4 answer is here</summary>
<p>{ "_id" : null, "pop" : 76394871 } </p> 
</details>

