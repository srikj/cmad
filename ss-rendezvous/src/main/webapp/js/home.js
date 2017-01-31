/**
 * 
 */
$(document).ready(function() {
	var username, password;
	var favPostsArray = new Array();
	$( "#createPostBtn" ).click(function() {
	  $("#posts, #updateProfile, #searchResults").hide();
	  $("#createPost .alert").hide();
	  $("#createPost").show();
	});
	$( "#updateProfileBtn" ).click(function() {
	  $("#posts, #createPost, #searchResults").hide();
	  $("#updateProfile .alert").removeClass("alert-success");
	  $("#updateProfile .alert").html('');
	  $("#updateProfile .alert").hide();
	  $("#updateProfile").show();
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
	$("#messages button").click(function(event) {
		event.preventDefault();
		var formData = {
				messageText : $("#message").val(),
				user : {
					username: userName
				}
		};
		$.ajax({
			url : "rest/message/create",
			type : 'post',
		    beforeSend: function(request) {
		      request.setRequestHeader("Authorization", "Basic "+auth);
		    },
			data : JSON.stringify(formData),
			contentType: 'application/json',
			success : function(response) {
				$("#message").val("");
				updateMessages();
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert("Failure");
			}
		});
	});
	
	setInterval(updateMessages(), 1000);
	function updateMessages() {
		$.ajax({
			url : "rest/message/all?offset=0&size=10",
			type : 'get',
		    beforeSend: function(request) {
		      request.setRequestHeader("Authorization", "Basic "+auth);
		    },
			success : function(response) {
				var messages = "";
				for(var i = 0; i<response.length; i++) {
					var date = new Date(response[i].createdDate);
					messages+="<p class='message'><b>"+response[i].user.username+"</b> | "+formatDate(date)+"<br>"+
					response[i].messageText+"</p>";
				}
				$("#message-body").html(messages);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert("Failure");
			}
		});
	};
	
	function formatDate(date) {
	  var hours = date.getHours();
	  var minutes = date.getMinutes();
	  var ampm = hours >= 12 ? 'pm' : 'am';
	  hours = hours % 12;
	  hours = hours ? hours : 12; // the hour '0' should be '12'
	  minutes = minutes < 10 ? '0'+minutes : minutes;
	  var strTime = hours + ':' + minutes + ' ' + ampm;
	  var month = new Array();
	  month[0] = "JAN";
	  month[1] = "FEB";
	  month[2] = "MAR";
	  month[3] = "APR";
	  month[4] = "MAY";
	  month[5] = "JUN";
	  month[6] = "JUL";
	  month[7] = "AUG";
	  month[8] = "SEP";
	  month[9] = "OCT";
	  month[10] = "NOV";
	  month[11] = "DEC";
	  return date.getDate() + " " + month[date.getMonth()] + " " + date.getFullYear() + "  " + strTime;
	};
	
	$("#posts").on("click",".comments button", function() {
//	$(".comments button").click(function() {
		var id = this.id;
		var post_id = id.substr(0,id.indexOf('-'));
		var formData = {
				commentText : $("#"+post_id+"-comment").val(),
				user : {
					username: userName
				}
		};
		
		$.ajax({
			url : "rest/post/"+post_id+"/comment",
			type : 'post',
			beforeSend: function(request) {
		      request.setRequestHeader("Authorization", "Basic "+auth);
		    },
			data : JSON.stringify(formData),
			contentType: 'application/json',
			success : function(response) {
				$("#"+post_id+"-comment").val("");
				updateComments(post_id);
				
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert("Failure");
			}
		});
		return false;
	});
	
	function updateComments(post_id) {
		$.ajax({
			url : "rest/post/"+post_id+"/comments",
			type : 'get',
		    beforeSend: function(request) {
		      request.setRequestHeader("Authorization", "Basic "+auth);
		    },
			success : function(response) {
				var comments = "";
				for(var i = 0; i<response.length; i++) {
					var date = new Date(response[i].createdDate);
					comments+="<div class='comment'>"+
								"<p class='meta'>"+
									"<span class='author'>"+response[i].user.username+"</span> | <span class='date'>"+formatDate(date)+"</span></p>"+
								"<p class='commentText'>"+response[i].commentText+"</p></div>";
				}
				$("#"+post_id+"-comment-body").html(comments);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert("Failure");
			}
		});
	};
	
	function fetchFavouritePosts() {
		$.ajax({
			url : "rest/user/favouritePosts/"+userName,
			type : 'get',
		    beforeSend: function(request) {
		      request.setRequestHeader("Authorization", "Basic "+auth);
		    },
			success : function(response) {
				var favPosts = "";
				if(response.length >0) {
					favPosts+="<h4>Favorite Posts</h4><ul>";
				}
				for(var i = 0; i<response.length; i++) {
					var post = response[i];
					favPosts+="<li><a href='' id='"+post.post_id+"-post' class='favPost'>"+post.title+"</a></li>";
				}
				if(response.length >0) {
					favPosts+="</ul>";
				}
				$("#favouritePosts").html(favPosts);
				favPostsArray = response;
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert("Failure");
			}
		});
		
		
	};
	
	$("#posts").on("click",".unMarkFav", function() {
//	$(".unMarkFav").click(function() {
		var id = this.id;
		var postid = id.substr(0,id.indexOf('-'));
		
		$.ajax({
			url : "rest/post/markFav/"+postid+"/"+userName,
			type : 'post',
			beforeSend: function(request) {
		      request.setRequestHeader("Authorization", "Basic "+auth);
		    },
			success : function(response) {
				$("#"+id).removeClass("unMarkFav");
				$("#"+id).addClass("markFav");
				$("#"+id+" span").removeClass("glyphicon-star-empty");
				$("#"+id+" span").addClass("glyphicon-star");
				fetchFavouritePosts();
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert("Failure");
			}
		});
		return false;
	});
	
	$("#posts").on("click",".markFav", function() {
//	$(".markFav").click(function() {
		var id = this.id;
		var postid = id.substr(0,id.indexOf('-'));
		$.ajax({
			url : "rest/post/unmarkFav/"+postid+"/"+userName,
			type : 'post',
			beforeSend: function(request) {
		      request.setRequestHeader("Authorization", "Basic "+auth);
		    },
			success : function(response) {
				$("#"+id).addClass("unMarkFav");
				$("#"+id).removeClass("markFav");
				$("#"+id+" span").addClass("glyphicon-star-empty");
				$("#"+id+" span").removeClass("glyphicon-star");
				fetchFavouritePosts();
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert("Failure");
			}
		});
		return false;
	});
	
	$("body").on("click",".topic a", function(e) {
//	$(".topic a").click(function(e) {
		e.preventDefault();
		var className = $(this).attr('class');
		var topic = className.substr(0,className.indexOf('-'));
		$.ajax({
			url : "rest/post/postsByTopic?topic="+topic,
			type : 'get',
			beforeSend: function(request) {
		      request.setRequestHeader("Authorization", "Basic "+auth);
		    },
			success : function(response) {
				display("Topic: "+topic,response);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert("Failure");
			}
		});
		return false;
	});
	
	$("body").on("click",".tags a", function(e) {
//		$(".topic a").click(function(e) {
		e.preventDefault();
		var className = $(this).attr('class');
		var tagName = $(this).text();
		var tag = className.substr(0,className.indexOf('-'));
		$.ajax({
			url : "rest/post/postsByTag?tag="+tag,
			type : 'get',
			beforeSend: function(request) {
		      request.setRequestHeader("Authorization", "Basic "+auth);
		    },
			success : function(response) {
				display("Tag: "+tagName,response);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert("Failure");
			}
		});
		return false;
	});
	
	$("body").on("click",".favPost", function(e) {
//		$(".topic a").click(function(e) {
		e.preventDefault();
		var id = this.id;
		var post_id = id.substr(0,id.indexOf('-'));
		$.ajax({
			url : "rest/post/"+post_id,
			type : 'get',
			beforeSend: function(request) {
		      request.setRequestHeader("Authorization", "Basic "+auth);
		    },
			success : function(response) {
				$("#posts").html(buildPost(response));
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert("Failure");
			}
		});
		return false;
	});
	
	$( "#search-form" ).submit(function( event ) {
		event.preventDefault();
		var key = $("#search").val();
		$.ajax({
			url : "rest/post/search?search="+key,
			type : 'get',
			beforeSend: function(request) {
		      request.setRequestHeader("Authorization", "Basic "+auth);
		    },
			success : function(response) {
				var results = "<h2>Search Results</h2>";
				if(response.length ==0) {
					results = "<p>No Results Found</p>";
				} else {
					results+="<p>Key: <span id='key'>"+key+"</span></p>";
					results+="<ul class='results'>";
					for(var i = 0; i<response.length; i++) {
						var post = response[i];
						results+="<li><h5>"+
								"<a href='' class='singlePost' id='"+post.post_id+"-single-post'>"+post.title+"</a></h5> "+
								"<span class='meta'>by <span class='author'>"+post.user.username+"</span> on"+
								"<span class='date'>"+formatDate(new Date(post.createdDate))+"</span></span></li>";
					}
					results+="</ul>"
				}
				$("#searchResults").html(results);
				$("#posts, #createPost, #updateProfile").hide();
				$("#searchResults").show();
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert("Failure");
			}
		});
	});
	
	
	
	function display(heading, response) {
		var posts = "<h2>"+heading+"</h2>";
		fetchFavouritePosts();
		if(response.length == 0) {
			posts+="<p>There are no posts to display. Please create one!</p>";
		} else {
			for(var i = 0; i<response.length; i++) {
				post = response[i];
				posts+=buildPost(post);
			}
		}
		$("#posts").html(posts);
	}
	
	function buildPost(post) {
		var posts="<div class='post'>"+
			"<h3 class='post-title'>"+post.title+"</h3>"+
			"<p class='meta'>"+
				"Posted on <span class='date'>"+formatDate(new Date(post.createdDate))+"</span> by <span "+
					"class='author'>"+post.user.username+"</span> ";
		var fav = false;
		for(var j = 0; j<favPostsArray.length; j++ ) {
			var p = favPostsArray[j];
			if (p.post_id == post.post_id) 
	        	fav = true;
		}
		markFav = fav?"markFav":"unMarkFav";
		glyph = fav?"glyphicon-star":"glyphicon-star-empty";
		posts+="<a href='' id='"+post.post_id+"-fav' class='"+markFav+"'><span "+
					"class='glyphicon "+glyph+"' aria-hidden='true'></span></a>"+
			"<br>"+
				"Tags: <span class='tags'>";
		for(var j = 0; j<post.tags.length; j++) {
			var t = post.tags[j];
			posts+="<a href='' class='"+t.id+"-tag'>"+t.tagName+"</a> "
		}
		posts+="</span>, Topic: <span class='topic'><a class='"+post.topic+"-topic'>"+post.topic+"</a></span></a></p>";
			
		posts+="<div class='post-text'>"+
				post.postText
			+"</div>";

		posts+="<div class='comments'>"+
				"<a class='btn btn-primary' role='button' data-toggle='collapse'"+
					"href='#"+post.post_id+"-comments' aria-expanded='false'"+
					"aria-controls='"+post.post_id+"-comments'> Comments </a>"+
				"<div class='collapse' id='"+post.post_id+"-comments'>"+
					"<div class='well'>"+
						"<form role='form'>"+
							"<div class='form-group'>"+
								"<textarea id='"+post.post_id+"-comment' class='form-control' rows='3'></textarea>"+
								"<button id='"+post.post_id+"-button' class='btn btn-success btn-block' type='submit'>Post Comment</button>"+
							"</div>"+
						"</form>"+
						"<h3>Comments</h3>"+
						"<div id='"+post.post_id+"-comment-body'>";
		if(post.comments == null || post.comments.length ==0) {
			posts+="<p>There are no comments!</p>";
		}
		else {
			for(var j = 0; j<post.comments.length; j++ ) {
				var comment = post.comments[j];
				posts+="<div class='comment'>"+
					"<p class='meta'>"+
					"<span class='author'>"+comment.user.username+"</span> | <span class='date'>"+formatDate(new Date(comment.createdDate))+"</span></p>"+
					"<p class='commentText'>"+comment.commentText+"</p></div>";
			}
		}
		posts+="</div></div></div></div></div>";

		return posts;
	
	}

});
