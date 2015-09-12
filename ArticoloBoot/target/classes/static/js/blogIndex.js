var blogIndex = angular.module('blogIndex', ['ngRoute']);

blogIndex.config(['$routeProvider',function ($routeProvider) {
	 
	$routeProvider
        .when('/home', {
            templateUrl: '/index',
            controller: 'listaController'
        }).when('/dettaglio/:id', {
            templateUrl: '/dettaglio',
            controller: 'dettaglioController'
        }).otherwise({
            redirectTo: '/home'
        });
}]);


blogIndex.controller('listaController', ['$scope', '$http',
function($scope, $http) {
		$scope.queryString = '';
		$scope.lista = null;
		$http.get('/lista').success(function(data) {
				$scope.lista = data.listItem;
		});
}]);

blogIndex.controller('dettaglioController', ['$scope', '$http','$routeParams',
 function($scope, $http, $routeParams, $location) {
		$scope.item = null;
 		$http.get('/getItem'+$routeParams.id).success(function(data) {
 				$scope.item = data.item;
 		});
 }]);
