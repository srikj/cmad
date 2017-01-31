<%@page import="com.cisco.cmad.api.User"%>
<%@page import="com.cisco.cmad.api.Interest"%>
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
          <a class="navbar-brand" href="home.jsp">r.e.n.d.e.z.v.o.u.s</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
        <ul class="nav navbar-nav pull-right">
            <li><a href="#" id="updateProfileBtn"><%=user.getUserInfo().getName() %></a></li>
            <li><a href="rest/user/logout">Logout</a></li>
          </ul>
        <form class="navbar-form navbar-right" id="search-form" data-toggle="validator">
        	
        	<div class="row">
        	<div class="input-group col-md-7">
			      <input type="text" id="search" class="form-control" placeholder="Search Posts..." required>
			      <span class="input-group-btn">
			        <button class="btn btn-default" type="submit">Go!</button>
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
        		<jsp:include page="sidenav.jsp" >
        			<jsp:param name="username" value="${userName}"/>
        		</jsp:include>
        		
        	</div>
        	<div class="col-md-6">
        		<jsp:include page="posts.jsp" >
        			<jsp:param name="username" value="${userName}"/>
        		</jsp:include>
        			
        		<jsp:include page="createPost.jsp" >
        			<jsp:param name="username" value="${userName}"/>
        		</jsp:include>
        		
        		<jsp:include page="updateProfile.jsp" >
        			<jsp:param name="userName" value="${userName}" />
        		</jsp:include>
        		
        		
        		<%@ include file="searchResults.jsp" %>
        		
        	</div>
        	<div class="col-md-4" role="complementary">
        		<%@ include file="sidenav2.jsp" %>
        	</div>
        </div>
    	
    </div>
    
	
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.9/validator.min.js"></script>
    <script src="js/bootstrap-tagsinput.min.js"></script>
    <script src="js/home.js?v1.2"></script>

</body>
</html>