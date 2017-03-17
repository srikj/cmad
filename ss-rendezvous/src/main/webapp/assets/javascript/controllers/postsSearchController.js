angular.module('Rendezvous').controller('PostsSearchController', function($scope, $http, $route, $routeParams){
  $http.get("rest/post/search?search="+$routeParams.key).then(function (response) {
        $scope.results = response.data;
        $scope.key = $routeParams.key;
  });
});