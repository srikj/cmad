angular.module("Rendezvous").directive('rvPageNav', function($sessionStorage, $http, $location){
  return {
    replace: true,
    restrict: "E",
    templateUrl: "assets/templates/directives/rvPageNav.html",
    controller: function($scope, $sessionStorage, $http, $rootScope ) {
    	$rootScope.user = $sessionStorage.user;
        $scope.user = $rootScope.user;
    	$scope.logout = function(){
            delete $sessionStorage.authToken ;
            delete $sessionStorage.user;
            delete $rootScope.user;
            $location.path("/");
        }
    }
  };
});