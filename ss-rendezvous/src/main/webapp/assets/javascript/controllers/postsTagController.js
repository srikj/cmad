angular.module('Rendezvous').controller('PostsTagController',
	function($scope, $http, $route, $routeParams){
		$http.get("rest/post/postsByTag?tag="+$routeParams.tag).then(function (response) {
		    $scope.posts = response.data;
		    $scope.heading = "Tag: "+ $routeParams.tag;
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