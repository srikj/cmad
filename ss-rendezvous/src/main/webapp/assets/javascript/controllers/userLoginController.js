angular.module('Rendezvous').controller('UserLoginController', function($scope, $sessionStorage, $http, $location, $rootScope){
	$scope.showAlert=false;
	$scope.alertSuccess=false;
	$scope.login = function(username,password){
		$http({
		    method: 'GET',
		    url: "rest/user/login?username="+username+"&password="+password,
		}).then(function (response) {
	        $sessionStorage.authToken = response.data;
	        if($sessionStorage.authToken) {
		    	$http.get("rest/user/",
	                {headers: {'Authorization': 'Bearer '+$sessionStorage.authToken}}).then(function (response) {
			        $scope.user = response.data;
			        $rootScope.user = $scope.user;
			        $sessionStorage.user = $rootScope.user;
			        $location.path("/posts");
			  	});
	    	}
	  	});

	  }
});