angular.module("Rendezvous").service('UserService', function($scope, $sessionStorage, $http) {
	this.username = $sessionStorage.username;
	this.user = $sessionStorage.user;
	
    this.loggedInUser = function() {
    	if(username) {
    		if(user) return user;
    		else {
		    	$http.get("rest/user/"+username).then(function (response) {
			        user = response.data;
			        $sessionStorage.user = response.data;
			  	});
	    	}	
    	} else {
    		return null;
    	}
    }
	this.isLoggedIn = function() {
		return $sessionStorage.username ? true: false;
	}
});