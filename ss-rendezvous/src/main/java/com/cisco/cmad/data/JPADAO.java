package com.cisco.cmad.data;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.cisco.cmad.api.Comment;
import com.cisco.cmad.api.Interest;
import com.cisco.cmad.api.Message;
import com.cisco.cmad.api.Post;
import com.cisco.cmad.api.Tag;
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
		EntityManager em = factory.createEntityManager();
		User user = em.find(User.class, username);
		em.close();
		return user;
	}

	@Override
	public User update(User user) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.merge(user);
		em.getTransaction().commit();
		em.close();
		return user;
	}

	@Override
	public Set<Post> getFavouritePosts(String username) {
		EntityManager em = factory.createEntityManager();
		User user = em.find(User.class, username);
		Set<Post> posts = user.getFavouritePosts();
		em.close();
		return posts;
	}

	@Override
	public int createPost(Post post) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(post);
		em.getTransaction().commit();
		int id = post.getPost_id();
		em.close();
		return id;
	}

	@Override
	public List<Comment> getComments(int post_id) {
		EntityManager em = factory.createEntityManager();
		Post post = em.find(Post.class, post_id);
		List<Comment> comments = post.getComments();
		em.close();
		return comments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getPosts() {
		EntityManager em = factory.createEntityManager();
		List<Post> posts = em.createQuery("from Post").getResultList();
		em.close();
		return posts;
	}

	@Override
	public List<Post> getPosts(int offset, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Post> getPostsByTag(int tag_id) {
		EntityManager em = factory.createEntityManager();
		Tag t = em.find(Tag.class, tag_id);
		Set<Post> posts = t.getTaggedPosts();
		em.close();
		return posts;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getPostsByInterest(Interest interest) {
		EntityManager em = factory.createEntityManager();
		List<Post> posts = em.createQuery("from Post p where p.interest=:interest").setParameter("interest", interest)
				.getResultList();
		em.close();
		return posts;
	}

	@Override
	public Post getPost(int post_id) {
		EntityManager em = factory.createEntityManager();
		Post p = em.find(Post.class, post_id);
		em.close();
		return p;
	}

	@Override
	public int getFavouritePostCount(int post_id) {
		EntityManager em = factory.createEntityManager();
		Post p = em.find(Post.class, post_id);
		int size = p.getFavouritedUsers().size();
		em.close();
		return size;
	}

	@Override
	public void markFavourite(int post_id, String username) {
		EntityManager em = factory.createEntityManager();
		User user = em.find(User.class, username);
		Post p = em.find(Post.class, post_id);
		em.getTransaction().begin();
		user.getFavouritePosts().add(p);
		p.getFavouritedUsers().add(user);
		em.persist(user);
		em.persist(p);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void unMarkFavourite(int post_id, String username) {
		EntityManager em = factory.createEntityManager();
		User user = em.find(User.class, username);
		Post p = em.find(Post.class, post_id);
		em.getTransaction().begin();
		user.getFavouritePosts().remove(p);
		p.getFavouritedUsers().remove(user);
		em.persist(user);
		em.persist(p);
		em.getTransaction().commit();
		em.close();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> search(String key) {

		EntityManager em = factory.createEntityManager();

		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
		em.getTransaction().begin();

		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Post.class).get();
		org.apache.lucene.search.Query luceneQuery = qb.keyword().onFields("title", "postText", "tag.tagName")
				.matching(key).createQuery();

		Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Post.class);

		// execute search
		List<Post> result = jpaQuery.getResultList();
		em.close();
		return result;
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
