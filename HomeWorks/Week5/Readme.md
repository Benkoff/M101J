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
```


<details>
<summary>Nothing here</summary>
<p>null</p> 
</details>
