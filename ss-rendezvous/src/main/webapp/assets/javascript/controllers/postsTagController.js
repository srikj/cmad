angular.module('Rendezvous').controller('PostsTagController',
	function($scope, $http, $route, $routeParams){
			$http.get("rest/post/postsByTag?tag="+$routeParams.id).then(function (response) {
			    $scope.posts = response.data;
			    $scope.heading = "Tag: "+ $routeParams.tag;
			});

	}
);