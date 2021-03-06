package com.cisco.cmad.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


//import org.hibernate.search.jpa.FullTextEntityManager;
//import org.hibernate.search.jpa.Search;
//import org.hibernate.search.query.dsl.QueryBuilder;

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
		posts.size();
		em.close();
		return posts;
	}

	@Override
	public int createPost(Post post) {
		
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		User u = getUser(post.getUser().getUsername());
		post.setUser(u);
		
		for(Tag tag : post.getTags() ) {
			Tag t = null;
			try {
				t = (Tag) em.createQuery("from Tag t where t.tagName=:tagName").setParameter("tagName", tag.getTagName()).getSingleResult();
			} catch(NoResultException e) {
				tag.getTaggedPosts().add(post);
				e.printStackTrace();
			}
			if(t != null) {
				t.getTaggedPosts().add(post);
				em.merge(t);
				post.getTags().remove(tag);
				post.getTags().add(t);
			}
		}
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
		List<Post> posts = em.createQuery("from Post p order by p.createdDate DESC").getResultList();
		for(Post post: posts) {
			post.getUser();
		}
		em.close();
		return posts;
	}

	@Override
	public List<Post> getPosts(int offset, int size) {
		EntityManager em = factory.createEntityManager();
		TypedQuery<Post> query =
			      em.createQuery("FROM Post p order by p.createdDate DESC", Post.class);
	    List<Post> numPosts =query.setFirstResult(offset)
	    								.setMaxResults(size)
	    								.getResultList();
	    return numPosts;
	}

	@Override
	public Set<Post> getPostsByTag(int tag_id) {
		EntityManager em = factory.createEntityManager();
		Tag t = em.find(Tag.class, tag_id);
		Set<Post> posts = t.getTaggedPosts();
		posts.size();
		em.close();
		return posts;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getPostsByInterest(Interest interest) {
		EntityManager em = factory.createEntityManager();
		List<Post> posts = em.createQuery("from Post p where p.topic=:interest").setParameter("interest", interest)
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
		em.persist(user);
		p.getFavouritedUsers().add(user);
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

//		EntityManager em = factory.createEntityManager();
//		em.getTransaction().begin();
//		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
//		
//		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Post.class).get();
//		org.apache.lucene.search.Query luceneQuery = qb.keyword().onFields("title", "postText", "tag.tagName")
//				.matching(key).createQuery();
//
//		Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Post.class);
//
//		// execute search
//		List<Post> result = jpaQuery.getResultList();
//		em.close();
		EntityManager em = factory.createEntityManager();
		List<Post> posts; 
		try {
			posts = em.createQuery("from Post p where p.title like :key OR p.postText like :key order by p.createdDate DESC").setParameter("key", "%"+key+"%")
				.getResultList();
		} catch(NoResultException e) {
			em.close();
			e.printStackTrace();
			return new ArrayList<Post>();
		}
		em.close();
		return posts;
	}

	@Override
	public void createComment(int post_id,Comment comment) {
		
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		User u = getUser(comment.getUser().getUsername());
		comment.setUser(u);
		Post p = em.find(Post.class, post_id);
		comment.setPost(p);
		em.persist(comment);
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
		List<Message> AllMessages = em.createQuery("FROM Message m order by m.createdDate DESC", Message.class).getResultList();
		em.close();
		return AllMessages;
	}

	@Override
	public List<Message> getMessages(int offset, int size) {
		EntityManager em = factory.createEntityManager();
		TypedQuery<Message> query =
			      em.createQuery("FROM Message m order by m.createdDate DESC", Message.class);
	    List<Message> numMessages =query.setFirstResult(offset)
	    								.setMaxResults(size)
	    								.getResultList();
	    return numMessages;
	}

	@Override
	public User getUserByEmail(String email) {
		EntityManager em = factory.createEntityManager();
		User user = null;
		try {
			user = (User) em.createQuery("from User a where a.userInfo.email = :email").setParameter("email", email).getSingleResult();
		} catch(NoResultException nre) {
			nre.printStackTrace();
			return null;
		}
		em.close();
		return user;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> getTags() {
		EntityManager em = factory.createEntityManager();
		List<Tag> tags = em.createQuery("from Tag").getResultList();
		em.close();
		return tags;
	}

}
