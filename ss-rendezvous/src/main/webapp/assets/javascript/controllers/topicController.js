angular.module('Rendezvous').controller('TopicController',
	function($scope, $http, $location, $rootScope){
	    $scope.getPostsByTopic = function(topic){
	    	$location.path("/posts/topic/"+topic);
	  	}
	}
);