package com.cisco.cmad.mongoapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.cisco.cmad.api.Interest;
import com.cisco.cmad.api.Post;
import com.cisco.cmad.api.Tag;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.UpdateResult;

import static com.mongodb.client.model.Filters.eq;


public class Mongoapi {

	public MongoCollection<Document> collection;
	
	public Mongoapi() {
		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("glarimy-mongo");
		collection = db.getCollection("user-posts");
	}
	
	public void addPost (String post,String username, String title) {
	
		Document doc = new Document();
		doc.put("post", post);
		doc.put("title", title);
		doc.put("Username", username);
		doc.put("Tags", Arrays.asList("movies","games"));
		collection.insertOne(doc);
		
	}
	
	public void findpost (String title, String username) {
		System.out.println(title);
		System.out.println(username);
		collection.find(eq("title", title)).forEach((Document d) -> System.out.println(d.toJson()));
		
	}

	public void createPost(Post post) {
		Document doc = new Document();
		List<String> Tags = new ArrayList<String>();
		
		for (Tag tag : post.getTags()) {
			Tags.add(tag.getTagName());
		}
		doc.put("username", post.getUser().getUsername());
		doc.put("tittle", post.getTitle());
		doc.put("post", post.getPostText());
		doc.put("interest", post.getTopic().toString());
		doc.put("Tags", Tags);
		collection.insertOne(doc);
		//collection.find(eq("title", post.getTitle())).forEach((Document d) -> System.out.println(d.toJson()));
	}
	
	public FindIterable<Document> getPosts() {
		FindIterable<Document> doc  = collection.find();
		return doc;
	}
	
	public FindIterable<Document> getPostId (String id) {
		BasicDBObject query = new BasicDBObject();
	    query.put("_id", new ObjectId(id));
	    FindIterable<Document> doc = collection.find(query);
	    return doc;
	}
	
	public FindIterable<Document> getPostsByTag (String tag) {
		FindIterable<Document> doc  = collection.find(eq("Tags",tag));
		for (Document d1 : doc) {
			System.out.println(d1.toJson());
		}
		return doc;
	}
	
	public FindIterable<Document> getPostsByInterest (Interest interest) {
		FindIterable<Document> doc  = collection.find(eq("interest", interest.toString()));
		for (Document d1 : doc) {
			System.out.println(d1.toJson());
		}
		return doc;
	}
	
	public FindIterable<Document> searcPost (String key) {
		  
		collection.createIndex(Indexes.text("post"));
		FindIterable<Document> doc = collection.find(Filters.text(key));
		for (Document d1 : doc) {
			System.out.println(d1.toJson());
		}
		collection.dropIndex(Indexes.text("post"));
		return doc;
	}
	
	public void addComment (String id, String comment ) {
		BasicDBObject query = new BasicDBObject();
	    query.put("_id", new ObjectId(id));
	    System.out.println(comment + "&" + id);
	    BasicDBObject modifyComment = new BasicDBObject();
	    modifyComment.put("$push", new BasicDBObject().append("Comments", comment));
	    UpdateResult result = collection.updateOne(query,modifyComment);
	    System.out.println(result);
	}
	
	public List<String> getComments (String id) {
		FindIterable<Document> doc = getPostId(id);
		List <String>comments = new ArrayList<String>();
		for (Document d : doc) {
			comments = (List<String> )d.get("Comments");
			System.out.println(comments);
		}
		return comments;
	}
	
	public void markFavourite(String post_id, String username) {
		BasicDBObject query = new BasicDBObject();
	    query.put("_id", new ObjectId(post_id));
	    System.out.println(username + "&" + post_id);
	    BasicDBObject modifyFavUsers = new BasicDBObject();
	    modifyFavUsers.put("$push", new BasicDBObject().append("Favourated_users", username));
	    UpdateResult result = collection.updateOne(query,modifyFavUsers);
	    System.out.println(result);
	}
	
	public void unmarkFavourite(String post_id, String username) {
		BasicDBObject query = new BasicDBObject();
	    query.put("_id", new ObjectId(post_id));
	    System.out.println(username + "&" + post_id);
	    BasicDBObject modifyFavUsers = new BasicDBObject();
	    modifyFavUsers.put("$pull", new BasicDBObject().append("Favourated_users", username));
	    UpdateResult result = collection.updateOne(query,modifyFavUsers);
	    System.out.println(result);
	}
}

