package com.cisco.cmad.mongoapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.cisco.cmad.api.Interest;
import com.cisco.cmad.api.Message;
import com.cisco.cmad.api.Post;
import com.cisco.cmad.api.Tag;
import com.cisco.cmad.api.User;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.UpdateResult;
import com.sun.research.ws.wadl.Doc;

import static com.mongodb.client.model.Filters.eq;


public class Mongoapi {

	public MongoCollection<Document> postCollection;
	public MongoCollection<Document>userCollection;
	public MongoCollection<Document>msgCollection;
	
	public Mongoapi() {
		MongoClient mongo = new MongoClient("104.198.241.199", 27017);
		MongoDatabase db = mongo.getDatabase("rendezvous");
		postCollection = db.getCollection("user-posts");
		userCollection = db.getCollection("user-data");
		msgCollection = db.getCollection("user-msgs");
	}
	
	public void addPost (String post,String username, String title) {
	
		Document doc = new Document();
		doc.put("post", post);
		doc.put("title", title);
		doc.put("Username", username);
		doc.put("Tags", Arrays.asList("movies","games"));
		postCollection.insertOne(doc);
		
	}
	
	public void findpost (String title, String username) {
		System.out.println(title);
		System.out.println(username);
		postCollection.find(eq("title", title));
		
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
		postCollection.insertOne(doc);
		//collection.find(eq("title", post.getTitle())).forEach((Document d) -> System.out.println(d.toJson()));
	}
	
	public FindIterable<Document> getPosts() {
		FindIterable<Document> doc  = postCollection.find();
		return doc;
	}
	
	public FindIterable<Document>getPosts(int size) {
		return(postCollection.find().sort(new Document("_id",-1)).limit(size));
	}
	
	public FindIterable<Document> getPostId (String id) {
		BasicDBObject query = new BasicDBObject();
	    query.put("_id", new ObjectId(id));
	    FindIterable<Document> doc = postCollection.find(query);
	    return doc;
	}
	
	public FindIterable<Document> getPostsByTag (String tag) {
		FindIterable<Document> doc  = postCollection.find(eq("Tags",tag));
		for (Document d1 : doc) {
			System.out.println(d1.toJson());
		}
		return doc;
	}
	
	public FindIterable<Document> getPostsByInterest (Interest interest) {
		FindIterable<Document> doc  = postCollection.find(eq("interest", interest.toString()));
		for (Document d1 : doc) {
			System.out.println(d1.toJson());
		}
		return doc;
	}
	
	public FindIterable<Document> searcPost (String key) {
		  
		postCollection.createIndex(Indexes.text("post"));
		FindIterable<Document> doc = postCollection.find(Filters.text(key));
		for (Document d1 : doc) {
			System.out.println(d1.toJson());
		}
		//postCollection.dropIndex(Indexes.text("post"));
		return doc;
	}
	
	public void addComment (String id, String comment ) {
		BasicDBObject query = new BasicDBObject();
	    query.put("_id", new ObjectId(id));
	    System.out.println(comment + "&" + id);
	    BasicDBObject modifyComment = new BasicDBObject();
	    modifyComment.put("$push", new BasicDBObject().append("Comments", comment));
	    UpdateResult result = postCollection.updateOne(query,modifyComment);
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
	    UpdateResult result = postCollection.updateOne(query,modifyFavUsers);
	    System.out.println(result);
	}
	
	public void unmarkFavourite(String post_id, String username) {
		BasicDBObject query = new BasicDBObject();
	    query.put("_id", new ObjectId(post_id));
	    System.out.println(username + "&" + post_id);
	    BasicDBObject modifyFavUsers = new BasicDBObject();
	    modifyFavUsers.put("$pull", new BasicDBObject().append("Favourated_users", username));
	    UpdateResult result = postCollection.updateOne(query,modifyFavUsers);
	    System.out.println(result);
	}
	
	public FindIterable<Document>getFavouritePosts(String username) {
		BasicDBObject query = new BasicDBObject();
	    query.put("Favourated_users", username);
		FindIterable<Document> favPost = postCollection.find(query);
		return favPost;
		
	}
	public List<String> getTags() {
		DistinctIterable<String> documents = postCollection.distinct("Tags", String.class);
		List<String>listTags = new ArrayList<>();
		for (String document : documents) {
		    listTags.add(document);
		}
		//System.out.println(listTags);
		return (listTags);
		
	}
	
	
	public Document getUser(String username) {
		FindIterable<Document> user = userCollection.find(eq("username",username));
		if (user == null) {
			return null;
		}
		return (user.first());
	}
	
	public void createUser (User user) {
		Document userDoc = new Document("username",user.getUsername())
					    .append("password", user.getPassword())
					    .append("createdDate", user.getCreatedDate())
					    .append("userinfo",new Document("name", user.getUserInfo().getName())
					    		.append("phoneNumber", user.getUserInfo().getPhoneNumber())
					    		.append("interest", user.getUserInfo().getInterest().toString())
					    		.append("email", user.getUserInfo().getEmail()))
					    .append("updatedDate", user.getUpdatedDate())
					    .append("lastLoginIP", user.getLastLoginIP())
					    .append("lastLoginDate", user.getLastLoginDate());
						
		userCollection.insertOne(userDoc);
		return;
		
	}
	
	public Document getUserByEmail(String email) {
		FindIterable<Document> user = userCollection.find(eq("email",email));
		if (user == null) {
			return null;
		}
		return (user.first());
	}
	
	public Document validateUser(String username, String password) {
		BasicDBObject query = new BasicDBObject();
		query.put("username", username);
		query.put("password", password);
		FindIterable<Document> user = userCollection.find(query);
		if (user == null) {
			return null;
		}
		return(user.first());
	}
	
	public void createMessage(Message message) {
		User user = message.getUser();
		Document msg = new Document("messageText",message.getMessageText())
						.append("createdDate", message.getCreatedDate())
						.append("username", user.getUsername());
		msgCollection.insertOne(msg);
		return;
	}
	
	public FindIterable<Document>getMessage(int size) {
		return(msgCollection.find().sort(new Document("createdDate",-1)).limit(size));
	}
	
	public FindIterable<Document>getMessages() {
		return(msgCollection.find());
	}

	public Document update(User user) {
		BasicDBObject query = new BasicDBObject();
		Document userDoc = new Document("username",user.getUsername())
			    .append("password", user.getPassword())
			    .append("createdDate", user.getCreatedDate())
			    .append("userinfo",new Document("name", user.getUserInfo().getName())
			    		.append("phoneNumber", user.getUserInfo().getPhoneNumber())
			    		.append("interest", user.getUserInfo().getInterest().toString())
			    		.append("email", user.getUserInfo().getEmail()))
			    .append("updatedDate", user.getUpdatedDate())
			    .append("lastLoginIP", user.getLastLoginIP())
			    .append("lastLoginDate", user.getLastLoginDate());
		
		userCollection.updateOne(eq("username", user.getUsername()),userDoc);
		
		
		return userCollection.find(eq("username",user.getUsername())).first();
	}
}

