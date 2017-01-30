<%@page import="com.cisco.cmad.api.User"%>
<%@page import="com.cisco.cmad.api.Rendezvous"%>
<%@page import="com.cisco.cmad.biz.SimpleRendezvous"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>r.e.n.d.e.z.v.o.u.s</title>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="css/bootstrap-tagsinput.css" rel="stylesheet">
<link href="css/home.css?v1" rel="stylesheet">
</head>
<body>
<%
//allow access only if session exists
User user = null;
Rendezvous rendezvous = new SimpleRendezvous();
String userName = null;
String auth = null;
if(session.getAttribute("username") == null || session.getAttribute("auth") == null){
	response.sendRedirect("index.jsp");
}
else {
	userName = (String) session.getAttribute("username");
	auth = (String) session.getAttribute("auth");
	user = 	rendezvous.getUserByUsername(userName);
}
%>
<script >
var userName = "<%=userName %>";
var auth = "<%=auth %>";
</script>
	<nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">r.e.n.d.e.z.v.o.u.s</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
        <ul class="nav navbar-nav pull-right">
            <li><a href="#" id="updateProfileBtn"><%=user.getUserInfo().getName() %></a></li>
            <li><a href="rest/user/logout">Logout</a></li>
          </ul>
        <form class="navbar-form navbar-right" id="nav-form">
        	
        	<div class="row">
        	<div class="input-group col-md-7">
			      <input type="text" id="search" class="form-control" placeholder="Search Posts...">
			      <span class="input-group-btn">
			        <button class="btn btn-default" type="button">Go!</button>
			      </span>
			    </div>
        		<div class="col-md-5" >
					<button class="btn  btn-primary btn-block" type="button" id="createPostBtn">Create Post</button>
				</div>
			</div>
          </form>
          
          
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    
    <div class="container">
    	<div class="row">
        	<div class="col-md-2">
        		<div class="sidenav affix">
        			<div class="widget" id="favouritePosts">
		        		<h4>Favorite Posts</h4>
						<ul>
							<li><a href='home.html'>asdkjf saddflkj</a></li>
							<li><a href='home.html'>oioi oioi</a></li>
							<li><a href='home.html'>mkkjvnch sdf</a></li>
							<li><a href='home.html'>zxc zxv zcxcv</a></li>
						</ul>
					</div>
					<div class="widget" id="postsByTag">
		        		<h4>Tags</h4>
		        		<p><a href='home.html'>asdkjf saddflkj</a>, <a href='home.html'>oioi oioi</a>, <a href='home.html'>mkkjvnch sdf</a>, <a href='home.html'>zxc zxv zcxcv</a></p>
					</div>
					<div class="widget" id="postsByTopic">
		        		<h4>Topic</h4>
						<ul>
							<li><a href='home.html'>Movies</a></li>
							<li><a href='home.html'>Technology</a></li>
							<li><a href='home.html'>Sports</a></li>
						</ul>
					</div>
				</div>
        	</div>
        	<div class="col-md-6">
        		<div id="posts">
	        		<div class="post">
		        		<h2 class="post-title">OpenSource Event Announced</h2> 
		        		<p class="meta">Posted on <span class="date">1 JAN 2017</span> by <span class="author">glarimy</span> <a href="" class="unMarkFav"><span class="glyphicon glyphicon-star" aria-hidden="true"></span></a></p>
						<div class="post-text">
							<p>asdfjaf adflkajflaskdjf afflaskfjaf aslfkajsflkasjfas
								dflaskfjasd flaskfjalsfkjalsfdkjsa dfasdlfkjasdflasf
								asdlkfjsdalfjksdaflkajsdf asdflaksdjflasdkfjas dfllaskdfjlaskdfjas
								dflasdkfjasldkfja</p>
			
							<p>asdfjaf adflkajflaskdjf afflaskfjaf aslfkajsflkasjfas
								dflaskfjasd flaskfjalsfkjalsfdkjsa dfasdlfkjasdflasf
								asdlkfjsdalfjksdaflkajsdf asdflaksdjflasdkfjas dfllaskdfjlaskdfjas
								dflasdkfjasldkfjas dflaskdfjlaskdfjlasdkfj asdflaskdfjasfd
								asldkfjasdlfkjasdf salldfkjjsdlfkjsdf salfksajflksjdf
								dflaskfjalskfjas fds dflaskdfjlaskdfjlasdkfj asdflaskdfjasfd
								asldkfjasdlfkjasdf salldfkjjsdlfkjsdf salfksajflksjdf
								dflaskfjalskfjas fd</p>
			
							<p>jalskfjas fdasdfjaf adflkajflask salldfkjjsdlfkjsdf
								salfksajflksjdf dflaskf</p>
			
							<p>asdfjaf adflkajflaskdjf afflaskfjaf aslfkajsflkasjfas
								dflaskfjasd flaskfjalsfkjalsfdkjsa dfasdlfkjasdflasf
								asdlkfjsdalfjksdaflkj asdflaskdfjasfd asldkfjasdlfkjasdf
								salldfkj	jsdlfkjsdf salfksajflksjdf dflaskfjalskfjas fd</p>
						</div>
						
				        <div class="comments">
				        	<a class="btn btn-primary" role="button" data-toggle="collapse" href="#post1-comments" aria-expanded="false" aria-controls="post1-comments">
							  Comments
							</a>
							<div class="collapse" id="post1-comments">
							  <div class="well">
							    <form role="form">
				        			<div class="form-group">
								      <textarea id="comment" class="form-control" rows="3"></textarea>
								      <button class="btn btn-success btn-block" type="submit">Post Comment</button>
								    </div>
						        </form>
						        <h3>Comments</h3>
						        <div class="comment">
						        	<p class="meta"><span class="author">glarimy</span> | <span class="date">3 JAN 2017</span>
						        	<p class="commentText">alsdkfj asdfllkkasjflasdkf sdfkaskjflkasdjf
									asdflaskfjladskfjsda f</p>
						        </div>
						        <div class="comment">
						        	<p class="meta"><span class="author">glarimy</span> | <span class="date">3 JAN 2017</span>
						        	<p class="commentText">alsdkfj asdfllkkasjflasdkf sdfkaskjflkasdjf
									asdflaskfjladskfjsda f</p>
						        </div>
							  </div>
							</div>
						</div>
	        		</div>
	        		<div class="post">
		        		<h2 class="post-title">OpenSource Event Announced</h2> 
		        		<p class="date">Posted on 1 JAN 2017<a href="" class="markFav"><span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span></a></p>
						<div class="post-text">
							<p>asdfjaf adflkajflaskdjf afflaskfjaf aslfkajsflkasjfas
								dflaskfjasd flaskfjalsfkjalsfdkjsa dfasdlfkjasdflasf
								asdlkfjsdalfjksdaflkajsdf asdflaksdjflasdkfjas dfllaskdfjlaskdfjas
								dflasdkfjasldkfja</p>
			
							<p>asdfjaf adflkajflaskdjf afflaskfjaf aslfkajsflkasjfas
								dflaskfjasd flaskfjalsfkjalsfdkjsa dfasdlfkjasdflasf
								asdlkfjsdalfjksdaflkajsdf asdflaksdjflasdkfjas dfllaskdfjlaskdfjas
								dflasdkfjasldkfjas dflaskdfjlaskdfjlasdkfj asdflaskdfjasfd
								asldkfjasdlfkjasdf salldfkjjsdlfkjsdf salfksajflksjdf
								dflaskfjalskfjas fds dflaskdfjlaskdfjlasdkfj asdflaskdfjasfd
								asldkfjasdlfkjasdf salldfkjjsdlfkjsdf salfksajflksjdf
								dflaskfjalskfjas fd</p>
			
							<p>jalskfjas fdasdfjaf adflkajflask salldfkjjsdlfkjsdf
								salfksajflksjdf dflaskf</p>
			
							<p>asdfjaf adflkajflaskdjf afflaskfjaf aslfkajsflkasjfas
								dflaskfjasd flaskfjalsfkjalsfdkjsa dfasdlfkjasdflasf
								asdlkfjsdalfjksdaflkj asdflaskdfjasfd asldkfjasdlfkjasdf
								salldfkj	jsdlfkjsdf salfksajflksjdf dflaskfjalskfjas fd</p>
						</div>
						<div class="comments">
				        	<a class="btn btn-primary" role="button" data-toggle="collapse" href="#post2-comments" aria-expanded="false" aria-controls="post1-comments">
							  Comments
							</a>
							<div class="collapse" id="post2-comments">
							  <div class="well">
							    <form role="form">
				        			<div class="form-group">
								      <textarea id="comment" class="form-control" rows="3"></textarea>
								      <button class="btn btn-success btn-block" type="submit">Post Comment</button>
								    </div>
						        </form>
						        <h3>Comments</h3>
						        <div class="comment">
						        	<p class="meta"><span class="author">glarimy</span> | <span class="date">3 JAN 2017</span>
						        	<p class="commentText">alsdkfj asdfllkkasjflasdkf sdfkaskjflkasdjf
									asdflaskfjladskfjsda f</p>
						        </div>
						        <div class="comment">
						        	<p class="meta"><span class="author">glarimy</span> | <span class="date">3 JAN 2017</span>
						        	<p class="commentText">alsdkfj asdfllkkasjflasdkf sdfkaskjflkasdjf
									asdflaskfjladskfjsda f</p>
						        </div>
							  </div>
							</div>
						</div>
	        		</div>
        		</div>
        		
        		
        		<div id="createPost">
        			<h2>New Post</h2>
        			<div class="alert " role="alert"></div>
        			<form class="form-horizontal" data-toggle="validator">
					  <div class="form-group">
					    <label for="postTitle" class="col-sm-2 control-label">Title</label>
					    <div class="col-sm-10">
					      <input type="text" class="form-control" id="postTitle" placeholder="Title" required>
					      <div class="help-block with-errors"></div>
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="postTags" class="col-sm-2 control-label">Tags</label>
					    <div class="col-sm-10">
					      <input type="text" class="form-control full-width" id="postTags" placeholder="Tags" data-role="tagsinput">
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="postTopic" class="col-sm-2 control-label">Topic</label>
					    <div class="col-sm-10">
					      <select id="postTopic" class="form-control">
							  <option value="0">Technology</option>
							  <option value="1">Movies</option>
							  <option value="2">Sports</option>
							</select>
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="postText" class="col-sm-2 control-label">Post</label>
					    <div class="col-sm-10">
					      <textarea id="postText" class="form-control" rows="5" data-minlength="100"></textarea>
					      <div class="help-block with-errors"></div>
					    </div>
					  </div>
					  <div class="form-group">
					    <div class="col-sm-offset-2 col-sm-10">
					      <button type="submit" class="btn btn-default">Submit Post</button>
					    </div>
					  </div>
					</form>
        		</div>
        		
        		<div id="updateProfile">
        			<h2>Your Profile</h2>
        			<form class="form-horizontal">
					  <div class="form-group">
							<label for="regName" class="col-sm-2 control-label">Name</label>
							<div class="col-sm-10">
						        <input type="text" id="regName" class="form-control" placeholder="Name" required>
						        <div class="help-block with-errors"></div>
					        </div>
				        </div>
				        
				        <div class="form-group">
					        <label for="regUsername" class="col-sm-2 control-label">Username</label>
					        <div class="col-sm-10">
						        <label class="control-label">srikanthj</label>
						        <div class="help-block with-errors"></div>
					        </div>
				        </div>
				        
				        <div class="form-group">
					        <label for="regEmail" class="col-sm-2 control-label">E-mail</label>
					        <div class="col-sm-10">
						        <input type="email" id="regEmail" class="form-control" placeholder="Email address" required>
						    	<div class="help-block with-errors"></div>
						    </div>
					    </div>
					    
					    <div class="form-group ">
							 <label for="regPhone" class="col-sm-2 control-label">Phone Number</label>
							 <div class="col-sm-10">
								 <input type="number" id="regPhone" class="form-control" placeholder="Phone Number">
								 <div class="help-block with-errors"></div>
							 </div>
						</div>
						
						<div class="form-group">
							<label for="regInterest" class="col-sm-2 control-label">Interest</label>
								<div class="col-sm-10">
							 	<select id="regInterest" class="form-control">
								  <option value="0">Technology</option>
								  <option value="1">Movies</option>
								  <option value="2">Sports</option>
								</select>
								<div class="help-block with-errors"></div>
							</div>
						</div>
						
						<div class="form-group">
							<label for="regPassword" class="col-sm-2 control-label">Password</label>
							<div class="col-sm-10">
						        <input type="password" id="regPassword" class="form-control" placeholder="Password" data-minlength="6" required>
						        <div class="help-block">Minimum of 6 characters</div>
					        </div>
				        </div>
				        
				        <div class="form-group">
				        	<label for="regConfirmPassword" class="col-sm-2 control-label">Confirm Password</label>
				        	<div class="col-sm-10">
						        <input type="password" id="regConfirmPassword" class="form-control" 
						        data-match="#regPassword" data-match-error="Whoops, these don't match"
						        placeholder="Confirm Password" required>
						        <div class="help-block with-errors"></div>
					        </div>
				        </div>
				        <div class="form-group">
						    <div class="col-sm-offset-2 col-sm-10">
						      <button type="submit" class="btn btn-default">Update</button>
						    </div>
						  </div>
					</form>
        		</div>
        		
        		<div id="searchResults">
        			<h2>Search Results</h2>
        			<p>Key:  <span id="key">asdf  saddf sdfs fs df</span></p>
        			<ul class="results">
        				<li>
        					<h5><a href="home.html">alsdkfja adf</a></h5>
        					<span class="meta">by <span class="author">alsdkjf</span> on <span class="date">12 NOV 2017</span></span>
        				</li>
        				<li>
        					<h5><a href="home.html">alsdkfja adf</a></h5>
        					<span class="meta">by <span class="author">alsdkjf</span> on <span class="date">12 NOV 2017</span></span>
        				</li>
        				<li>
        					<h5><a href="home.html">alsdkfja adf</a></h5>
        					<span class="meta">by <span class="author">alsdkjf</span> on <span class="date">12 NOV 2017</span></span>
        				</li>
        			</ul>
        		</div>
        		
        	</div>
        	<div class="col-md-4" role="complementary">
        		<div class="sidenav2 affix">
	        		<div class="widget" id="messages">
		        		<h4>Messages</h4>
						<p>
							<u>krishna | 31 DEC 2016 3.45PM</u><br />asdf adlfkajsf
							asfllsdakfjsdad faslfdjasdfasf sdfllsdakfjs df
						</p>
						<p>
							<u>krishna | 31 DEC 2016 3.45PM</u><br />asdf adlfkajsf
							asfllsdakfjsdad faslfdjasdfasf sdfllsdakfjs df
						</p>
						<p>
							<u>mohan | 30 DEC 2016 3.45PM</u><br />asdf adlfkajsf
							asfllsdakfjsdad faslfdjasdfasf sdfllsdakfjs df
						</p>
						<hr /> 
						<form role="form">
		        			<div class="form-group">
						      <textarea id="message" class="form-control" rows="3"></textarea>
						      <button class="btn btn-success btn-block" type="submit">Post Message</button>
						    </div>
				        </form>
	        		</div>
	        		<div class="widget" id="invite">
	        			<h4>Invite Users</h4>
	        			<form role="form">
		        			<div class="input-group">
						      <input type="text" id="invite" class="form-control" placeholder="email1, email2, email3">
						      <span class="input-group-btn">
						        <button class="btn btn-default btn-block" type="submit">Invite</button>
						      </span>
						    </div>
				        </form>
	        		</div>
        		</div>
        	</div>
        </div>
    	
    </div>
    
	
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.9/validator.min.js"></script>
    <script src="js/bootstrap-tagsinput.min.js"></script>
    <script src="js/home.js"></script>

</body>
</html>