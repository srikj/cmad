package com.cisco.cmad.mongoapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.cisco.cmad.api.Comment;
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
		MongoClient mongo = new MongoClient("192.168.99.100", 27017);
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
		ObjectId oid = new ObjectId();
		doc.put("_id", oid);
		doc.put("post_id", oid.toString());
		doc.put("user", new Document("username", post.getUser().getUsername()));
		doc.put("title", post.getTitle());
		doc.put("postText", post.getPostText());
		doc.put("topic", post.getTopic().toString());
		doc.put("tags", Tags);
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
	
	public Document getPostId (String id) {
		BasicDBObject query = new BasicDBObject();
	    query.put("post_id", id);
	    FindIterable<Document> doc = postCollection.find(query);
	    return doc.first();
	}
	
	public FindIterable<Document> getPostsByTag (String tag) {
		FindIterable<Document> doc  = postCollection.find(eq("tags",tag));
		for (Document d1 : doc) {
			System.out.println(d1.toJson());
		}
		return doc;
	}
	
	public FindIterable<Document> getPostsByInterest (Interest interest) {
		FindIterable<Document> doc  = postCollection.find(eq("topic", interest.toString()));
		for (Document d1 : doc) {
			System.out.println(d1.toJson());
		}
		return doc;
	}
	
	public FindIterable<Document> searcPost (String key) {
		  
		postCollection.createIndex(Indexes.text("postText"));
		FindIterable<Document> doc = postCollection.find(Filters.text(key));
		for (Document d1 : doc) {
			System.out.println(d1.toJson());
		}
//		postCollection.dropIndex(Indexes.text("postText"));
		return doc;
	}
	
	public void addComment (String id, Comment comment ) {
		BasicDBObject query = new BasicDBObject();
	    query.put("_id", new ObjectId(id));
	    System.out.println(comment + "&" + id);
	    BasicDBObject modifyComment = new BasicDBObject();
	    Document modifiedComment = new Document("commentText",comment.getCommentText())
			    .append("createdDate", comment.getCreatedDate())
			    .append("updatedDate", comment.getUpdatedDate())
			    .append("user",new Document("username", comment.getUser().getUsername()));
	    modifyComment.put("$push", new BasicDBObject().append("comments", modifiedComment));
	    UpdateResult result = postCollection.updateOne(query,modifyComment);
	    System.out.println(result);
	}
	
	public List<String> getComments (String id) {
		Document doc = getPostId(id);
		List <String>comments = new ArrayList<String>();
		comments = (List<String> )doc.get("comments");
		System.out.println(comments);
		return comments;
	}
	
	public void markFavourite(String post_id, String username) {
		BasicDBObject query = new BasicDBObject();
	    query.put("post_id", post_id);
	    System.out.println(username + "&" + post_id);
	    BasicDBObject modifyFavUsers = new BasicDBObject();
	    modifyFavUsers.put("$push", new BasicDBObject().append("favouritedUsers", username));
	    UpdateResult result = postCollection.updateOne(query,modifyFavUsers);
	    System.out.println(result);
	}
	
	public void unmarkFavourite(String post_id, String username) {
		BasicDBObject query = new BasicDBObject();
	    query.put("_id", new ObjectId(post_id));
	    System.out.println(username + "&" + post_id);
	    BasicDBObject modifyFavUsers = new BasicDBObject();
	    modifyFavUsers.put("$pull", new BasicDBObject().append("favouritedUsers", username));
	    UpdateResult result = postCollection.updateOne(query,modifyFavUsers);
	    System.out.println(result);
	}
	
	public FindIterable<Document>getFavouritePosts(String username) {
		BasicDBObject query = new BasicDBObject();
	    query.put("favouritedUsers", username);
		FindIterable<Document> favPost = postCollection.find(query);
		return favPost;
		
	}
	public List<String> getTags() {
		DistinctIterable<String> documents = postCollection.distinct("tags", String.class);
		List<String>listTags = new ArrayList<>();
		for (String document : documents) {
		    listTags.add(document);
		}
		//System.out.println(listTags);
		return (listTags);
		
	}
	
	
	public Document getUser(String username) {
		BasicDBObject query = new BasicDBObject();
		query.removeField("password");
		FindIterable<Document> user = userCollection.find(eq("username",username)).projection(query);
		if (user == null) {
			return null;
		}
		return (user.first());
	}
	
	public void createUser (User user) {
		Document userDoc = new Document("username",user.getUsername())
					    .append("password", user.getPassword())
					    .append("createdDate", user.getCreatedDate())
					    .append("userInfo",new Document("name", user.getUserInfo().getName())
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
	
	public Document validateUser(String username, String password, String ip) {
		BasicDBObject query = new BasicDBObject();
		query.put("username", username);
		query.put("password", password);
		FindIterable<Document> user = userCollection.find(query);
		if (user == null) {
			return null;
		}
//		userCollection.updateOne(eq("username", username),
//				user.first().append("lastLoginIP", ip)
//				.append("lastLoginDate", new Date()));
		return(user.first());
	}
	
	public void createMessage(Message message) {
		Document msg = new Document("messageText",message.getMessageText())
			    .append("createdDate", message.getCreatedDate())
			    .append("user",new Document("username", message.getUser().getUsername()));
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
			    .append("userInfo",new Document("name", user.getUserInfo().getName())
			    		.append("phoneNumber", user.getUserInfo().getPhoneNumber())
			    		.append("name", user.getUserInfo().getName())
			    		.append("interest", user.getUserInfo().getInterest().name())
			    		.append("email", user.getUserInfo().getEmail()))
			    
			    .append("updatedDate", user.getUpdatedDate())
			    .append("lastLoginIP", user.getLastLoginIP())
			    .append("lastLoginDate", user.getLastLoginDate());
		
		userCollection.updateOne(eq("username",user.getUsername()),new Document("$set",userDoc));
		
		
		return userCollection.find(eq("username",user.getUsername())).first();
	}
}

