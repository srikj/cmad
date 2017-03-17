angular.module('Rendezvous').controller('UserRegisterController', function($scope, $sessionStorage, $http){
	$scope.showAlert=false;
	$scope.alertSuccess=false;
	$scope.register = function(user){
		$http({
		    method: 'POST',
		    url: "rest/user/register",
		    data: user,
		    headers: {
		    	'Content-Type': 'application/json',
		    }
		}).then(function (response) {
	        $scope.user = {};
	        $scope.alertSuccess = true;
	        $scope.alertMessage = "User Created Successfully";
	        $scope.showAlert=true;
	  	});

	  }
});