angular.module('Rendezvous').controller('PostsShowController', function($scope, $http, $sessionStorage, $route, $routeParams){
	$scope.token = $sessionStorage.authToken;
	$scope.user = $sessionStorage.user;
	$http({
	    method: 'GET',
	    url: 'rest/post/'+$routeParams.id,
	    headers: {
	    	'Content-Type': 'application/json',
	    	'Accept': 'application/json',
	    }
	}).then(function (response) {
        $scope.post = response.data;
  	});
  	$scope.postComment = function(comment) {
  		comment.user = $sessionStorage.user;
  		$http({
		    method: 'POST',
		    url: 'rest/post/'+$routeParams.id+'/comment',
		    data: comment,
		    headers: {
		    	'Content-Type': 'application/json',
		    	'Accept': 'application/json',
		    	'Authorization': 'Bearer '+ token
		    }
		}).then(function (response) {
	        $scope.comment = {};
	        $http({
			    method: 'GET',
			    url: 'rest/post/'+$routeParams.id+'/comments',
			    headers: {
			    	'Content-Type': 'application/json',
			    	'Accept': 'application/json'
			    }
			}).then(function (response) {
		        $scope.post.comments = response.data;
		        $scope.comment = {};
		  	});
	  	});
  	}
  	$scope.isFavorites = function(post) {
  		if(post.favouritedUsers) {
       		return post.favouritedUsers.indexOf($scope.user.username) >=0;
       	} else {
       		return false;
       	}
	}

	$scope.toggleFav = function(post) {
		if($scope.isFavorites(post)) {
			$http({
			    method: 'POST',
			    url: "rest/post/unmarkFav/"+post.post_id+"/"+$scope.user.username,
			    headers: {
			    	'Authorization': 'Bearer '+ token
			    }
			});
		} else {
			$http({
			    method: 'POST',
			    url: "rest/post/markFav/"+post.post_id+"/"+$scope.user.username,
			    headers: {
			    	'Authorization': 'Bearer '+ token
			    }
			});
		}

	}
});