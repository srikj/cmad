/**
 * 
 */
$(document).ready(function() {
	var username, password;
	$( "#toggleRegister" ).click(function() {
	  $(".form-signin").hide();
	  $(".form-signup").show();
	});

	$( "#toggleSignIn" ).click(function() {
	  $(".form-signup").hide();
	  $(".form-signin").show();
	});
//	$( ".form-signin" ).submit(function( event ) {
//		username = $("#inputUsername").val();
//		password = $("#inputPassword").val();
//		$.ajax({
//			url : "rest/user/login?username="+username+"&password="+password,
//			type : 'get',
//			success : function(response) {
//				 $.ajax({
//					 type :'get',
//					 url: "rest/user/home",
//					 beforeSend: function (xhr) {
//						    xhr.setRequestHeader ("Authorization", "Basic " + btoa(username + ":" + password));
//					 },
//					 success: function (response){
//						     $('body').html(response);
//						    }
//				 })
//			},
//			error: function(jqXHR, textStatus, errorThrown) {
//				alert("Failure");
//			}
//			
//		});
//		  event.preventDefault();
//	});
	$( ".form-signup" ).submit(function( event ) {
		event.preventDefault();
		var formData = {
			username: $("#regUsername").val(),
			password: $("#regPassword").val(),
			userInfo : {
				name: $("#regName").val(),
				email: $("#regEmail").val(),
				phoneNumber: $("#regPhone").val(),
				interest: $("#regInterest").val()
			}
		};
		
		$.ajax({
			url : "rest/user/register",
			type : 'post',
			data : JSON.stringify(formData),
			contentType: 'application/json',
			success : function(response) {
				 $(".form-signup").hide();
				 $(".form-signin").show();
				 $(".alert").addClass("alert-success");
				 $(".alert-success").html("User Successfully Created. Please login");
				 $(".alert").show();
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert("Failure");
			}
			
		});
		  
	});
});
