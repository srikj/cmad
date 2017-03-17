angular.module('Rendezvous').controller('UserEditController', function($scope, $sessionStorage, $http){
	$scope.showAlert=false;
	$scope.alertSuccess=false;
	$scope.user = $sessionStorage.user;
	$scope.user.userInfo.interest = function(newName) {
		value = 0;
    	if(newName == 'TECHNOLOGY') value = 0;
    	else if(newName == 'MOVIES') value = 1;
    	else if(newName == 'SPORTS') value = 2;
		return value;
	};
	$scope.editUser = function(user){
		token = $sessionStorage.authToken;
		$http({
		    method: 'PUT',
		    url: 'rest/user/update',
		    data: user,
		    headers: {
		    	'Content-Type': 'application/json',
				'Authorization': 'Bearer '+ token
		    }
		}).then(function (response) {
	        $scope.showAlert=true;
	        $scope.user = response.data;
	        $scope.alertSuccess = true;
	        $scope.alertMessage = "User Details Successfully Updated";
	  	});

	  }
});