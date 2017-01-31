<%@page import="com.cisco.cmad.api.User"%>
<%@page import="com.cisco.cmad.api.Post"%>
<%@page import="com.cisco.cmad.api.Tag"%>
<%@page import="com.cisco.cmad.api.Comment"%>
<%@page import="com.cisco.cmad.api.Interest"%>
<%@page import="com.cisco.cmad.api.Rendezvous"%>
<%@page import="com.cisco.cmad.biz.SimpleRendezvous"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%> 
<%@page import="java.util.Iterator"%>  

<div id="posts">
<%
Rendezvous rendezvous1 = new SimpleRendezvous();
List<Post> posts = rendezvous1.getPosts();
String userName = (String) session.getAttribute("username");
if(posts == null || posts.size()==0) {
%>
	<p>There are no posts to display. Please create one!</p>
<% } else { %>
	<h2>Latest Posts</h2>
<%		for(Post post : posts) {
%>

	<div class="post">
		<h3 class="post-title"><%=post.getTitle() %></h3>
		<p class="meta">
			Posted on <span class="date"><%=post.getCreatedDate() %></span> by <span
				class="author"><%=post.getUser().getUsername() %></span> 
				<%
					Set<Post> favouritePosts = rendezvous1.getFavouritePosts(userName); 
					boolean fav = false;
					for (Iterator<Post> it = favouritePosts.iterator(); it.hasNext(); ) {
				        Post p = it.next();
				        if (p.getPost_id() == post.getPost_id()) 
				        	fav = true;
				    }
				%>
				<a href="" id="<%=post.getPost_id() %>-fav" class="<%=fav?"markFav":"unMarkFav"%>"><span
				class="glyphicon <%=fav?"glyphicon-star":"glyphicon-star-empty"%>" aria-hidden="true"></span></a>
		<br>
			Tags: <span class="tags"><%
			Set<Tag> tags = post.getTags();
			for(Tag tag : tags) {%>
				<a href="" class="<%=tag.getId() %>-tag"><%=tag.getTagName() %></a>
			<% } %></span>, Topic: <span class="topic"><a class="<%=post.getTopic().name() %>-topic"><%=post.getTopic().name() %></a></span></a>
		</p>
		
		<div class="post-text">
			<%=post.getPostText() %>
		</div>

		<div class="comments">
			<a class="btn btn-primary" role="button" data-toggle="collapse"
				href="#<%=post.getPost_id() %>-comments" aria-expanded="false"
				aria-controls="<%=post.getPost_id() %>-comments"> Comments </a>
			<div class="collapse" id="<%=post.getPost_id() %>-comments">
				<div class="well">
					<form role="form">
						<div class="form-group">
							<textarea id="<%=post.getPost_id() %>-comment" class="form-control" rows="3"></textarea>
							<button id="<%=post.getPost_id() %>-button" class="btn btn-success btn-block" type="submit">Post
								Comment</button>
						</div>
					</form>
					<h3>Comments</h3>
					<div id="<%=post.getPost_id() %>-comment-body">
						<% List<Comment> comments = rendezvous1.getComments(post.getPost_id());
							if(comments == null || comments.size() ==0) { %>
							<p>There are no comments!</p>
							<%} else {
								for(Comment comment : comments) {
						%>
						<div class="comment">
							<p class="meta">
								<span class="author"><%=comment.getUser().getUsername() %></span> | <span class="date"><%=comment.getCreatedDate() %></span></p>
							<p class="commentText"><%=comment.getCommentText() %></p>
						</div>
						<% 		}
							} %>
					</div>
				</div>
			</div>
		</div>
	</div>
	<% 		}
		} %>
</div>
