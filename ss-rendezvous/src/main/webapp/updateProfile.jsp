<%@page import="com.cisco.cmad.api.User"%>
<%@page import="com.cisco.cmad.api.Interest"%>
<%@page import="com.cisco.cmad.api.Rendezvous"%>
<%@page import="com.cisco.cmad.biz.SimpleRendezvous"%>  
<%
Rendezvous rendezvous = new SimpleRendezvous();
User user = rendezvous.getUserByUsername(session.getAttribute("username").toString()); 
%>
<div id="updateProfile">
	<h2>Your Profile</h2>
	<div class="alert " role="alert"></div>
	<form class="form-horizontal" data-toggle="validator">
		<div class="form-group">
			<label for="regName" class="col-sm-2 control-label">Name</label>
			<div class="col-sm-10">
				<input type="text" id="regName" class="form-control"
					placeholder="Name" value="<%=user.getUserInfo().getName()%>"
					required>
				<div class="help-block with-errors"></div>
			</div>
		</div>

		<div class="form-group">
			<label for="regUsername" class="col-sm-2 control-label">Username</label>
			<div class="col-sm-10">
				<input type="text" id="regName" class="form-control" disabled
					placeholder="Username" value="<%=user.getUsername()%>" required>
				<div class="help-block with-errors"></div>
			</div>
		</div>

		<div class="form-group">
			<label for="regEmail" class="col-sm-2 control-label">E-mail</label>
			<div class="col-sm-10">
				<input type="email" id="regEmail" class="form-control"
					placeholder="Email address"
					value="<%=user.getUserInfo().getEmail()%>" required>
				<div class="help-block with-errors"></div>
			</div>
		</div>

		<div class="form-group ">
			<label for="regPhone" class="col-sm-2 control-label">Phone
				Number</label>
			<div class="col-sm-10">
				<input type="number" id="regPhone" class="form-control"
					placeholder="Phone Number"
					value="<%=user.getUserInfo().getPhoneNumber()%>">
				<div class="help-block with-errors"></div>
			</div>
		</div>

		<div class="form-group">
			<label for="regInterest" class="col-sm-2 control-label">Interest</label>
			<div class="col-sm-10">
				<select id="regInterest" class="form-control">
					<%
						for (Interest interest : Interest.values()) {
						if (user.getUserInfo().getInterest().equals(interest)) {
					%>
					<option value="<%=interest.ordinal()%>" selected><%=interest.name()%></option>
					<%
						} else {
					%>
					<option value="<%=interest.ordinal()%>"><%=interest.name()%></option>
					<%
						}
					}
					%>
				</select>
				<div class="help-block with-errors"></div>
			</div>
		</div>

		<div class="form-group">
			<label for="regPassword" class="col-sm-2 control-label">Password</label>
			<div class="col-sm-10">
				<input type="password" id="regPassword" class="form-control"
					placeholder="Password" data-minlength="6" required>
				<div class="help-block">Minimum of 6 characters</div>
			</div>
		</div>

		<div class="form-group">
			<label for="regConfirmPassword" class="col-sm-2 control-label">Confirm
				Password</label>
			<div class="col-sm-10">
				<input type="password" id="regConfirmPassword" class="form-control"
					data-match="#regPassword"
					data-match-error="Whoops, these don't match"
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