angular.module("Rendezvous").directive('rvSideNav2', function(){
  return {
    replace: true,
    restrict: "E",
    templateUrl: "assets/templates/directives/rvSideNav2.html",
    controller: function($scope, $sessionStorage, $http, $rootScope) {
    	$scope.$storage = $sessionStorage;
    	$rootScope.user = $sessionStorage.user;
    	$scope.user = $rootScope.user;
    	token = $sessionStorage.authToken;
    	$scope.messages = {};
    	$scope.message = {};
    	updateMessage = function() {
    		$http({
			    method: 'GET',
			    url: 'rest/message/all?offset=0&size=10',
			    headers: {
			    	'Content-Type': 'application/json',
			    	'Accept': 'application/json',
			    	'Authorization': 'Bearer '+ token
			    }
			}).then(function (response) {
		        $scope.messages = response.data;
		  	});
    	}
    	if(token) updateMessage();
    	$scope.saveMessage = function(message){
			message.user=$sessionStorage.user;
			token = $sessionStorage.authToken;
			$http({
			    method: 'POST',
			    url: 'rest/message/create',
			    data: message,
			    headers: {
			    	'Content-Type': 'application/json',
			    	'Authorization': 'Bearer '+ token
			    }
			}).then(function (response) {
				updateMessage();
		        $scope.message = {};
		  	});
		}
    }
  };
});