/**
 * 
 */
$(document).ready(function() {
	var username, password;
	$( "#createPostBtn" ).click(function() {
	  $("#posts, #updateProfile, #searchResults").hide();
	  $("#createPost .alert").hide();
	  $("#createPost").show();
	});
	$( "#updateProfileBtn" ).click(function() {
	  $("#posts, #createPost, #searchResults").hide();
	  $("#updateProfile").show();
	});
	$( ".navbar-brand" ).click(function() {
	  $("#updateProfile, #createPost, #searchResults").hide();
	  $("#posts").show();
	});
	$( "#createPost button" ).click(function( event ) {
		event.preventDefault();
		var tagArray = $("#postTags").tagsinput('items');
		var tags = new Array();
		for (var i=0;i<tagArray.length; i++) {
			var tag = {
					tagName: tagArray[i]
			};
			tags.push(tag);
		}
		var formData = {
			title: $("#postTitle").val(),
			postText: $("#postText").val(),
			topic: $("#postTopic").val(),
			user : {
				username: "srikanthj"
			},
			tags: tags
		};
		
		$.ajax({
			url : "rest/post/create",
			type : 'post',
		    beforeSend: function(request) {
		      request.setRequestHeader("Authorization", "Basic "+auth);
		    },
			data : JSON.stringify(formData),
			contentType: 'application/json',
			success : function(response) {
				$("#postTags").val("");
				$("#postTitle").val("");
				$("#postText").val("");
				 $("#createPost .alert").addClass("alert-success");
				 $("#createPost .alert-success").html("Post Successfully Created.");
				 $("#createPost .alert").show();
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert("Failure");
			}
			
		});
		  
	});
	$( "#updateProfile button" ).click(function( event ) {
		event.preventDefault();
		var formData = {
			username: userName,
			password: $("#regPassword").val(),
			userInfo : {
				name: $("#regName").val(),
				email: $("#regEmail").val(),
				phoneNumber: $("#regPhone").val(),
				interest: $("#regInterest").val()
			}
		};
		var logout = false;
		
		$.ajax({
			url : "rest/user/update",
			type : 'put',
		    beforeSend: function(request) {
		      request.setRequestHeader("Authorization", "Basic "+auth);
		    },
			data : JSON.stringify(formData),
			contentType: 'application/json',
			dataType: 'json',
			success : function(response) {
				$("#regName").val();
				$("#regEmail").val();
				$("#regPhone").val();
				$("#regInterest").val();
				auth = btoa(username+":"+password);
				$('#updateProfileBtn').html(response.userInfo.name);
				$("#updateProfile .alert").addClass("alert-success");
				$("#updateProfile .alert-success").html("User Details Successfully Updated");
				$("#updateProfile .alert").show();
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert("Failure");
			}
			
		});	
		  
	});

});
