<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>r.e.n.d.e.z.v.o.u.s</title>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="css/signin.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<h1>r.e.n.d.e.z.v.o.u.s</h1>
		
		
		
		<form class="form-signin" method="get" action="rest/user/login" data-toggle="validator">
		<div class="alert " role="alert"></div>
			<h2 class="form-signin-heading">Login</h2>
		  <div class="form-group">
			  <label for="inputUsername" class="control-label">Username</label>
			  <input type="text" id="inputUsername" name="username" class="form-control" placeholder="Username" required autofocus>
			  <div class="help-block with-errors"></div>
		  </div>
		  <div class="form-group">
			  <label for="inputPassword" class="control-label">Password</label>
			  <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required>
			  <div class="help-block with-errors"></div>
		  </div>
		  <button class="btn btn-lg btn-success btn-block" type="submit">Sign in</button>
		  <button class="btn btn-lg btn-primary btn-block" type="button" id="toggleRegister">No Account? Sign Up!</button>
		</form>
		
		
		
		<form class="form-signup" role="form" data-toggle="validator">

	        <h2 class="form-signin-heading">No Account? Sign up!</h2>
	        
	        <div class="form-group">
				<label for="regName" class="control-label">Name</label>
		        <input type="text" id="regName" class="form-control" placeholder="Name" required>
		        <div class="help-block with-errors"></div>
	        </div>
	        
	        <div class="form-group">
		        <label for="regUsername" class="control-label">Username</label>
		        <input type="text" id="regUsername" class="form-control" placeholder="Username" name="username" data-remote="rest/user/validateUsername" required>
		        <span class="glyphicon form-control-feedback" aria-hidden="true"></span>
		        <div class="help-block with-errors"></div>
	        </div>
	        
	        <div class="form-group">
		        <label for="regEmail" class="control-label">E-mail</label>
		        <input type="email" id="regEmail" class="form-control" placeholder="Email address" name="email" data-remote="rest/user/validateEmail" required>
		        <span class="glyphicon form-control-feedback" aria-hidden="true"></span>
		        <div class="help-block with-errors"></div>
		    </div>
		    <div class="form-group">
		         <div class="form-inline row">
					<div class="form-group col-sm-6">
						 <label for="regPhone" class="control-label">Phone Number</label>
						 <input type="number" id="regPhone" class="form-control" placeholder="Phone Number">
						 <div class="help-block with-errors"></div>
					</div>
					
					<div class="form-group col-sm-6">
						<label for="regInterest" class="control-label">Interest</label>
					 	<select id="regInterest" class="form-control">
						  <option value="TECHNOLOGY">Technology</option>
						  <option value="MOVIES">Movies</option>
						  <option value="SPORTS">Sports</option>
						</select>
						<div class="help-block with-errors"></div>
					</div>
		        </div>
		    </div>
	        <div class="form-group">
	         	<label for="regPassword" class="control-label">Password</label>
		         <div class="form-inline row">
			        <div class="form-group col-sm-6">
				        <input type="password" id="regPassword" class="form-control" placeholder="Password" data-minlength="6" required>
				        <div class="help-block">Minimum of 6 characters</div>
			        </div>
			        
			        <div class="form-group col-sm-6">
				        <input type="password" id="regConfirmPassword" class="form-control" 
				        data-match="#regPassword" data-match-error="Whoops, these don't match"
				        placeholder="Confirm Password" required>
				        <div class="help-block with-errors"></div>
			        </div>
		        </div>
	        </div>
	        <button class="btn btn-lg btn-success btn-block" type="submit">Create Account</button>
	        <button class="btn btn-lg btn-primary btn-block" type="button" id="toggleSignIn">Sign In</button>
	    </form>
    </div>
	 
	 <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.9/validator.min.js"></script>
    <script src="js/signin.js?v1.2"></script>
</body>
</html>