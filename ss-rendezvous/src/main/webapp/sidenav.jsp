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

<div class="sidenav affix">
	<div class="widget" id="favouritePosts">
		<%
		String userName = (String) session.getAttribute("username");
		Rendezvous rendezvous2 = new SimpleRendezvous();
			Set<Post> posts = rendezvous2.getFavouritePosts(userName);
			if(posts != null && posts.size()>0) {%>
				<h4>Favorite Posts</h4>
				<ul>
				<%for (Iterator<Post> it = posts.iterator(); it.hasNext(); ) {
			        Post p = it.next(); %>
			        
			        <li><a href='' class="favPost" id="<%=p.getPost_id()%>-post"><%=p.getTitle() %></a></li>
			    
				<%}%>
				</ul>
		<% } %>
	</div>
	<div class="widget" id="postsByTag">
		<%
			List<Tag> tags = rendezvous2.getTags();
			if(tags != null && tags.size()>0) {%>
				<h4>Tags</h4>
					<p class="tags">
				<%for (Iterator<Tag> it = tags.iterator(); it.hasNext(); ) {
			        Tag tag = it.next(); %>
			        
			        <a href="" class="<%=tag.getId() %>-tag"><%=tag.getTagName() %></a> 
			    
				<%}%>
				</p>
		<% } %>
	</div>
	<div class="widget" id="postsByTopic">
		<h4>Topics</h4>
		<ul>
		<%
		for (Interest interest : Interest.values()) {%>
			<li><span class="topic"><a class="<%=interest.name() %>-topic"><%=interest.name().toLowerCase() %></a></span></li>
		<%}%>
		</ul>
	</div>
</div>