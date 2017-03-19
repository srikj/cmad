angular.module('Rendezvous').controller('PostsTopicController',
	function($scope, $http, $route, $routeParams){
		$http.get("rest/post/postsByTopic?topic="+$routeParams.topic).then(function (response) {
		    $scope.posts = response.data;
		    $scope.heading = "Topic: "+ $routeParams.topic;
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