package com.cisco.cmad.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.cisco.cmad.api.Comment;
import com.cisco.cmad.api.Interest;
import com.cisco.cmad.api.Message;
import com.cisco.cmad.api.Post;
import com.cisco.cmad.api.User;

public class JPADAO implements DAO {
	
	static EntityManagerFactory factory = Persistence.createEntityManagerFactory("rendezvous");

	@Override
	public void createUser(User user) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> getFavouritePosts(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createPost(Post post) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Comment> getComments(int post_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> getPosts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> getPosts(int offset, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> getPostsByTag(int tag_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> getPostsByInterest(Interest interest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post getPost(int post_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFavouritePostCount(int post_id) {
		
	}

	@Override
	public void markFavourite(int post_id, String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unMarkFavourite(int post_id, String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Post> search(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createComment(int post_id,Comment comment) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Post p = em.find(Post.class, post_id);
		p.getComments().add(comment);
		em.persist(p);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void createMessage(Message message) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(message);
		em.getTransaction().commit();
		em.close();
		
	}

	@Override
	public List<Message> getMessages() {
		EntityManager em = factory.createEntityManager();
		List<Message> AllMessages = em.createQuery("FROM Message", Message.class).getResultList();
		em.close();
		return AllMessages;
	}

	@Override
	public List<Message> getMessages(int offset, int size) {
		EntityManager em = factory.createEntityManager();
		TypedQuery<Message> query =
			      em.createQuery("FROM Message", Message.class);
	    List<Message> numMessages =query.setFirstResult(offset)
	    								.setMaxResults(size)
	    								.getResultList();
	    return numMessages;
	}

	

}
