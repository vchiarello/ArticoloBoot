var app = angular.module("busyApp", ["ui.router", "ngCookies", "ngResource", "angularFileUpload","ngSanitize","cgBusy"]);

angular.module("busyApp").controller("busyContr", ['$scope',  '$state', '$stateParams',  '$q', '$http',
	function ($scope, $state, $stateParams, $q,$http ) {
		
		$scope.promise = null;
	

		function asyncGreet(name) {
			  // perform some asynchronous operation, resolve or reject the promise when appropriate.
			  return $q(function(resolve, reject) {
			    setTimeout(function() {
			      if (false) {
			        resolve('Hello, ' + name + '!');
			      } else {
			        reject('Greeting ' + name + ' is not allowed.');
			      }
			    }, 10000);
			  });
			}

			var promise1 = asyncGreet('Robin Hood');
			promise1.then(function(greeting) { alert('Success: ' + greeting);}
					      ,function(reason) {alert('Failed: ' + reason);})
					      ;
		
		function demo1(){

			//$scope.promise = $http.get('http://localhost:8080/rest/cart');
			$scope.promise = promise1;
			console.log($scope.promise);

		};
		
		demo1();
	}]);