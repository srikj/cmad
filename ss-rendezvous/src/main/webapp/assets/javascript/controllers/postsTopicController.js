angular.module('Rendezvous').controller('PostsTopicController',
	function($scope, $http, $route, $routeParams){
		$http.get("rest/post/postsByTopic?topic="+$routeParams.topic).then(function (response) {
		    $scope.posts = response.data;
		    $scope.heading = "Topic: "+ $routeParams.topic;
		});
	}
);