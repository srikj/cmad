angular.module('Rendezvous').controller('FavPostsController', function($scope,$http,$sessionStorage){
	token = $sessionStorage.authToken;
	$http.get("rest/user/favouritePosts/"+$sessionStorage.user.username, 
		{headers: {'Authorization': 'Bearer '+token}})
	.then(function (response) {
		if(response.data.length > 0)
        	$scope.favPosts = response.data;
    });
}); 