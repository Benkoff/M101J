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
<details>
<summary>The answer is here</summary>
<p> </p> 
</details>

## Question 4
<details>
<summary>The answer is here</summary>
<p> </p> 
</details>

## Question 5
<details>
<summary>The answer is here</summary>
<p> </p> 
</details>

## Question 6
<details>
<summary>The answer is here</summary>
<p> </p> 
</details>

## Question 7
<details>
<summary>The answer is here</summary>
<p> </p> 
</details>

## Question 8
<details>
<summary>The answer is here</summary>
<p> </p> 
</details>

## Question 9
<details>
<summary>The answer is here</summary>
<p> </p> 
</details>

## Question 10
<details>
<summary>The answer is here</summary>
<p> </p> 
</details>

