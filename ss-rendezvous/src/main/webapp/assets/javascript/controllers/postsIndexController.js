angular.module('Rendezvous').controller('PostsIndexController',
	function($scope, $http, $route, $rootScope, $sessionStorage){
		// $route.reload();
		$rootScope.user = $sessionStorage.user;
	  	$http.get("rest/post/all").then(function (response) {
	        $scope.posts = response.data;
	        $scope.heading = "Latest Posts";
	  	});
	}
);