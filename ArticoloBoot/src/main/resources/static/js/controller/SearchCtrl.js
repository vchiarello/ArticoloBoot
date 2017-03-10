//controller della home page con la lista degli item
angular.module("blogApp").controller("SearchCtrl", function ($scope, Item, $state, $http,$stateParams) {

    
   function getItems () {
    	$scope.items = $http({method: 'GET',
    						url:	'/rest/itemShop/search/'+$stateParams.querySearch
    						}).then(function successCallback(respsone){console.log("Ricerca avvenuta correttamente")},
    								function errorCallback(response){Alert("Errore nella ricerca")})
    }
    
    $scope.messaggio = messaggiErrore['list.spinner.message'];

    function init () {
    	$scope.items = getItems();
    };

    init();
    
});
