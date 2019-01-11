/**
 * @author 张鸿建
 * @time 2018年10月22日 上午9:16:48
 * @version MongoSave.java
 * @explain
 */

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.Word;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MongoSave {

    MongoClient client = new MongoClient("localhost", 27017);

    MongoCollection<Document> col = client.getDatabase("Avalon").getCollection("data");

    DBCollection dbcollection = client.getDB("Avalon").getCollection("data");
    // DBCollection  col2 = client.getDatabase("Avalon").getCollection("data");

    public void seg() {

        FindIterable<Document> iterable = col.find();

        for (Document doc : iterable) {

            String text = doc.getString("mainText");
            List<Word> list = null;
            try {
                list = WordSegmenter.segWithStopWords(text, SegmentationAlgorithm.MinimumMatching);
            } catch (Exception e) {
                System.err.println(e);
            }
            if (list != null) {
                col.updateOne(doc, new Document("$set", new Document("words", list.toString())));
            } else {
                col.updateOne(doc, new Document("$set", new Document("words", "")));
            }

        }

    }

    public Cursor queryFlag() {
       /* Document sub_group = new Document();
        sub_group.put("_id", "$flag");
        sub_group.put("count", new Document("$sum", 1));

        Document sub_match = new Document();
        sub_match.put("count", new Document("$gt", 1));

        Document sub_sort = new Document();
        sub_sort.put("count", -1);

        Document group = new Document("$group", sub_group);
        Document match = new Document("$match", sub_match);
        Document sort = new Document("$sort", sub_sort);

        List<Document> aggregatelist = new ArrayList<>();
        aggregatelist.add(match);
        aggregatelist.add(group);
        aggregatelist.add(sort);

        AggregateIterable<Document> resultset = col.aggregate(aggregatelist);
        return resultset;*/

        List<DBObject> aggregateQuery = new ArrayList<>();
        BasicDBObjectBuilder groupbuilder = new BasicDBObjectBuilder();
        groupbuilder.add("_id", "$flag");
        groupbuilder.add("count", new BasicDBObject("$sum", 1));

        BasicDBObjectBuilder matchbuilder = new BasicDBObjectBuilder();
        matchbuilder.add("count", new BasicDBObject("$gt", 1));

        //  BasicDBObjectBuilder sortbuilder = new BasicDBObjectBuilder();
        //   sortbuilder.add("$sort", new BasicDBObject("count",-1));

        aggregateQuery.add(new BasicDBObject("$group", groupbuilder.get()));
        aggregateQuery.add(new BasicDBObject("$match", matchbuilder.get()));
        //   aggregateQuery.add(new BasicDBObject("$sort",sortbuilder.get()));

        AggregationOptions aggregation = AggregationOptions.builder().allowDiskUse(true).build();

        Cursor cursor = dbcollection.aggregate(aggregateQuery, aggregation);

        return cursor;
    }

    public FindIterable queryMainText(String flag) {
        Document query = new Document("flag", flag);
        FindIterable<Document> iterable = col.find(query);
        return iterable;
    }

    public void setFlag(Document query, String flag) {
        Document document = new Document();
        document.append("flag", flag);
        col.updateOne(query, new Document("$set", document));

    }

    public void setHash(Map<String, Object> data) {
        Document document = new Document();
        document.append("hash", data.get("hash").toString());
        document.append("hash1", data.get("hash1").toString());
        document.append("hash2", data.get("hash2").toString());
        document.append("hash3", data.get("hash3").toString());
        document.append("hash4", data.get("hash4").toString());
        Object id = data.get("id");
//		String url=data.get("url").toString();
        col.updateOne(new Document("_id", id), new Document("$set", document));
    }

    public void setRep(Document document,String repFlag) {
        col.updateOne(document, new Document("$set", new Document("Rep",repFlag)));
    }
    public FindIterable getAllData() {
        FindIterable<Document> findIterable = col.find();
        return findIterable;
    }

//	public MongoCollection<Document> getDataCollection() {
//		return client.getDatabase("Avalon").getCollection("data");
//	}

    public static void main(String[] args) {
//		MongoCollection<Document> dataCollection = new MongoSave().col;
//		Document document = new Document();
//		Document query = new Document();
//		document.append("hash", "123");
//		query.append("title", "驻马店市驿城区交通运输局2018年涉贫农村公路、桥...");
//		FindIterable<Document> documents = dataCollection.find(query);
//		String id = documents.iterator().next().get("_id").toString();
//		System.out.println(id);
//		dataCollection.updateOne(Filters.eq("title","驻马店市驿城区交通运输局2018年涉贫农村公路、桥..."), new Document("$set",document));

//        new MongoSave().seg();
        FindIterable iterable = new MongoSave().queryMainText("40008");
        MongoCursor<Document> cursor = iterable.iterator();

        //   Cursor cursor = new MongoSave().queryFlag();
        try {
            while (cursor.hasNext()) {
                //    DBObject next = cursor.next();
                //    Object id = next.get("_id");
                Document next = cursor.next();
                String mainText =(String) next.get("mainText");
             //   System.out.println(id.toString());
                System.out.println(mainText);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }


}
