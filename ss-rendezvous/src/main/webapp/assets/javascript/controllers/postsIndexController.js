angular.module('Rendezvous').controller('PostsIndexController',
	function($scope, $http, $route, $rootScope, $sessionStorage){
		// $route.reload();
		$scope.token = $sessionStorage.authToken;
		$rootScope.user = $sessionStorage.user;
		$scope.user = $rootScope.user
	  	$http.get("rest/post/all").then(function (response) {
	        $scope.posts = response.data;
	        $scope.heading = "Latest Posts";
	  	});
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
	}
);