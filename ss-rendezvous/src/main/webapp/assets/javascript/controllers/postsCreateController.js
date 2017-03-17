angular.module('Rendezvous').controller('PostsCreateController', function($sessionStorage,$scope, $http){
	$scope.showAlert=false;
	$scope.alertSuccess=false;
	$scope.savePost = function(post){
		post.user=$sessionStorage.user;
		token = $sessionStorage.authToken;
		$http({
		    method: 'POST',
		    url: 'rest/post/create',
		    data: post,
		    headers: {
		    	'Content-Type': 'application/json',
		    	'Authorization': 'Bearer '+ token
		    }
		}).then(function (response) {
	        $scope.post = {};
	        $scope.alertSuccess = true;
	        $scope.alertMessage = "Post Created Successfully";
	        $scope.showAlert=true;
	  	});

	  }
});