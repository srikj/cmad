angular.module("Rendezvous").directive('rvSideNav', function(){
  return {
    replace: true,
    restrict: "E",
    templateUrl: "assets/templates/directives/rvSideNav.html",
    controller: function($scope, $sessionStorage, $http, $rootScope) {
    	$scope.$storage = $sessionStorage;
    	$scope.user = $rootScope.user;
    }
  };
});