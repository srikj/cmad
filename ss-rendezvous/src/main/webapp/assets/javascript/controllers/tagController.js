angular.module('Rendezvous').controller('TagController', 
	function($scope, $http, $location, $rootScope){
		$http.get("rest/tag/all").then(function (response) {
	        $scope.tags = response.data;
	    });
	}
);