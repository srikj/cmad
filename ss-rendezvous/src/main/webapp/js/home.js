/**
 * 
 */
$(document).ready(function() {
	var username, password;
	$( "#createPostBtn" ).click(function() {
	  $("#posts, #updateProfile, #searchResults").hide();
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

});
