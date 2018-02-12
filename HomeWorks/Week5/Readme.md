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

HomeWork 5.1
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
