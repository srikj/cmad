package com.cisco.cmad.biz;

import static com.mongodb.client.model.Filters.eq;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.bson.Document;

import com.cisco.cmad.api.Comment;
import com.cisco.cmad.api.Interest;
import com.cisco.cmad.api.InvalidDataException;
import com.cisco.cmad.api.InvalidInterestException;
import com.cisco.cmad.api.Message;
import com.cisco.cmad.api.Post;
import com.cisco.cmad.api.PostNotFoundException;
import com.cisco.cmad.api.Rendezvous;
import com.cisco.cmad.api.RendezvousException;
import com.cisco.cmad.api.Tag;
import com.cisco.cmad.api.TagNotFoundException;
import com.cisco.cmad.api.User;
import com.cisco.cmad.api.UserAlreadyExistsException;
import com.cisco.cmad.api.UserInfo;
import com.cisco.cmad.api.UserNotFoundException;
import com.cisco.cmad.data.DAO;
import com.cisco.cmad.data.JPADAO;
import com.cisco.cmad.mongoapi.Mongoapi;
import com.mongodb.client.FindIterable;

public class SimpleRendezvous implements Rendezvous {

	private DAO dao;
	private Mongoapi mapi;

	public SimpleRendezvous() {
		// dao = new JPADAO();
		mapi = new Mongoapi();
	}

	@Override
	public void register(User user) throws UserAlreadyExistsException, InvalidDataException, RendezvousException {
		if (user.getUsername() == null || user.getUsername().trim().isEmpty())
			throw new InvalidDataException();
		UserInfo userinfo = user.getUserInfo();
		if (userinfo.getEmail() == null || userinfo.getEmail().trim().isEmpty())
			throw new InvalidDataException();
		if (userinfo.getName() == null || userinfo.getName().trim().isEmpty())
			throw new InvalidDataException();
		if (userinfo.getPhoneNumber() == null || userinfo.getPhoneNumber().trim().isEmpty()) {
			throw new InvalidDataException();
		}
		Pattern pattern = Pattern.compile("^[0-9]{10}$");
		Matcher matcher = pattern.matcher(userinfo.getPhoneNumber());
		if (!matcher.matches()) {
			throw new InvalidDataException();
		}
		Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
		Matcher regMatcher = regexPattern.matcher(userinfo.getEmail());
		if (!regMatcher.matches()) {
			throw new InvalidDataException();
		}
		user.setLastLoginDate(new Date());
		user.setUpdatedDate(new Date());
		user.setCreatedDate(new Date());
		user.setPosts(null);
		user.setFavouritePosts(null);
		user.setMessages(null);

		if (mapi.getUser(user.getUsername()) != null)
			throw new UserAlreadyExistsException();

		// dao.createUser(user);
		mapi.createUser(user);
	}

	@Override
	public Document login(String username, String password, String ip)
			throws UserNotFoundException, InvalidDataException, RendezvousException {
		if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			throw new InvalidDataException();
		}
		// User user = dao.getUser(username);
		Document user = mapi.validateUser(username, password, ip);
		if (user == null) {
			throw new UserNotFoundException();
		}
		return user;
	}

	@Override
	public Document update(User user) throws UserNotFoundException, InvalidDataException, RendezvousException {
		if (user.getUsername() == null || user.getUsername().trim().isEmpty())
			throw new InvalidDataException();
		UserInfo userinfo = user.getUserInfo();
		if (userinfo.getPhoneNumber() != null) {
			Pattern pattern = Pattern.compile("^[0-9]{10}$");
			Matcher matcher = pattern.matcher(userinfo.getPhoneNumber());
			if (!matcher.matches()) {
				throw new InvalidDataException();
			}
		}
		if (userinfo.getEmail() != null) {
			Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
			Matcher regMatcher = regexPattern.matcher(userinfo.getEmail());
			if (!regMatcher.matches()) {
				throw new InvalidDataException();
			}
		}

		user.setUpdatedDate(new Date());
		Document updatedUser = mapi.update(user);
		return updatedUser;
	}

	@Override
	public void invite(String emailIds) throws InvalidDataException, RendezvousException {
		String from = "noreply@cisco.com";
		String host = "outbound.cisco.com";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);

		for (String emailid : emailIds.split(",")) {
			if (emailid == null || emailid.trim().isEmpty()) {
				throw new InvalidDataException();
			}
			Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
			Matcher regMatcher = regexPattern.matcher(emailid);
			if (regMatcher.matches()) {
				System.out.println("Email Invite sent to" + emailid);

				try {
//					getUserByEmail(emailid);
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress(from));
					message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(emailid));
					message.setSubject("Welcome to Rendezvous");
					message.setText(
							"You have been invited to Rendezvous! Register at http://35.185.110.136/:8080/rendezvous");
					Transport.send(message);
				} catch (MessagingException mex) {
					mex.printStackTrace();
				}
			} else {
				throw new InvalidDataException();
			}
		}
	}

	@Override
	public FindIterable<Document> getFavouritePosts(String username) throws UserNotFoundException, RendezvousException {
		if (username == null || username.trim().isEmpty())
			throw new InvalidDataException();

		FindIterable<Document> posts = mapi.getFavouritePosts(username);
		if (posts == null) {
			throw new RendezvousException();
		}
		return posts;
	}

	@Override
	public void createPost(Post post) throws InvalidDataException, RendezvousException {
		if (post.getTitle() == null || post.getTitle().trim().isEmpty() || post.getPostText() == null
				|| post.getPostText().trim().isEmpty()) {
			throw new InvalidDataException();
		}
		post.setAbstractText(post.getPostText().substring(0, 100));
		post.setCreatedDate(new Date());
		post.setUpdatedDate(new Date());
		// When post is created usually there will be no comments
		// post.setComments(null);
		// post.setFavouritedUsers(null);
		// post.setTags(null);
		// dao.createPost(post);
		mapi.createPost(post);
	}

	@Override
	public List<String> getComments(String post_id) throws PostNotFoundException, RendezvousException {
		List<String> comments = mapi.getComments(post_id);
		if (comments == null) {
			throw new PostNotFoundException();
		}
		return comments;
	}

	@Override
	public Object getPosts() {
		FindIterable<Document> allPosts = mapi.getPosts();
		return allPosts;
	}

	@Override
	public FindIterable<Document> getPosts(int offset, int size) {
		FindIterable<Document> numPosts = mapi.getPosts(size);
		if (numPosts == null) {
			return null;
		}
		return numPosts;
	}

	@Override
	public FindIterable<Document> getPostsByTag(String tag) throws TagNotFoundException, RendezvousException {
		FindIterable<Document> postByTag = mapi.getPostsByTag(tag);
		if (postByTag == null) {
			throw new TagNotFoundException();
		}
		return postByTag;
	}

	@Override
	public FindIterable<Document> getPostsByInterest(Interest interest)
			throws InvalidInterestException, RendezvousException {
		// SHould add here to validate the interest
		FindIterable<Document> postByInt = mapi.getPostsByInterest(interest);
		if (postByInt == null) {
			throw new RendezvousException();
		}
		return postByInt;
	}

	@Override
	public Document getPost(String post_id) throws PostNotFoundException, RendezvousException {
		Document post = mapi.getPostId(post_id);
		if (post == null) {
			throw new PostNotFoundException();
		}
		return post;
	}

	/***
	 * @Override public int getFavouritePostCount(int post_id) throws
	 *           PostNotFoundException, RendezvousException { int size =
	 *           dao.getFavouritePostCount(post_id); if (size == 0) { throw new
	 *           PostNotFoundException(); } return size; }
	 */

	@Override
	public void markFavourite(String post_id, String username)
			throws PostNotFoundException, UserNotFoundException, RendezvousException, InvalidDataException {
		if (post_id.trim().isEmpty() || username.trim().isEmpty() || username == null) {
			throw new InvalidDataException();
		}
		// User user = dao.getUser(username);
		Document user = mapi.getUser(username);
		if (user == null) {
			throw new UserNotFoundException();
		} else {
			// dao.markFavourite(post_id, username);
			mapi.markFavourite(post_id, username);
		}

	}

	@Override
	public void unMarkFavourite(String post_id, String username)
			throws PostNotFoundException, UserNotFoundException, RendezvousException, InvalidDataException {
		if (post_id.trim().isEmpty() || username.trim().isEmpty() || username == null) {
			throw new InvalidDataException();
		}
		// User user = dao.getUser(username);
		Document user = mapi.getUser(username);
		if (user == null) {
			throw new UserNotFoundException();
		} else {
			// dao.unMarkFavourite(post_id, username);
			mapi.unmarkFavourite(post_id, username);
		}
	}

	@Override
	public FindIterable<Document> search(String key) {
		return mapi.searcPost(key);
	}

	@Override
	public void createComment(String post_id, Comment comment)
			throws PostNotFoundException, InvalidDataException, RendezvousException {
		if (comment.getCommentText() == null || comment.getCommentText().trim().isEmpty()
				|| comment.getUser().getUsername() == null || comment.getUser().getUsername().trim().isEmpty()
				|| post_id.trim().isEmpty()) {
			throw new InvalidDataException();
		}
		// Post post = dao.getPost(post_id);
		Document post = mapi.getPostId(post_id);
		if (post == null) {
			throw new PostNotFoundException();
		}
		comment.setCreatedDate(new Date());
		comment.setUpdatedDate(new Date());
		mapi.addComment(post_id, comment);

	}

	@Override
	public void createMessage(Message message) throws InvalidDataException, RendezvousException {
		if (message.getMessageText() == null || message.getMessageText().trim().isEmpty()) {
			throw new InvalidDataException();
		}
		if (message.getUser() == null) {
			throw new RendezvousException();
		}
		message.setCreatedDate(new Date());
		mapi.createMessage(message);
	}

	@Override
	public FindIterable<Document> getMessages() {
		// List<Message> allMessage = dao.getMessages();
		FindIterable<Document> allMessage = mapi.getMessages();
		return allMessage;
	}

	@Override
	public FindIterable<Document> getMessages(int offset, int size) {
		// List<Message> numMessages = dao.getMessages(offset, size);
		FindIterable<Document> numMessages = mapi.getMessage(size);
		return numMessages;
	}

	@Override
	public Document getUserByUsername(String username)
			throws UserNotFoundException, InvalidDataException, RendezvousException {
		if (username == null || username.trim().isEmpty())
			throw new InvalidDataException();

		Document user = mapi.getUser(username);

		if (user == null)
			throw new UserNotFoundException();
		return user;
	}

	@Override
	public Document getUserByEmail(String email)
			throws UserNotFoundException, InvalidDataException, RendezvousException {
		if (email == null || email.trim().isEmpty())
			throw new InvalidDataException();

		// User user = dao.getUserByEmail(email);
		Document user = mapi.getUserByEmail(email);
		if (user == null)
			throw new UserNotFoundException();
		return user;
	}

	@Override
	public List<String> getTags() {
		// return dao.getTags();
		return mapi.getTags();
	}

}
