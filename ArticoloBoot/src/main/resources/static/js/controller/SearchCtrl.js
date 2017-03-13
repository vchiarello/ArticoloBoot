//controller della home page con la lista degli item
angular.module("blogApp").controller("SearchCtrl", function ($scope, Item, $state, $http,$stateParams) {

    
   function getItems () {
    	$scope.o= $http({method: 'GET',
				url:	'/rest/itemShop/search/'+$stateParams.querySearch
						}).then(function successCallback(response){
								console.log("Ricerca avvenuta correttamente");
								$scope.items = response.data;
								},
								function errorCallback(response){Alert("Errore nella ricerca")});
	   console.log($scope.o);
   }
    
    $scope.messaggio = messaggiErrore['list.spinner.message'];

    
    $scope.viewItem = function (item) {
    	if (item.tipoItem==1)
    		$state.transitionTo("viewItem",{id: item.id, name: item.nome});
    	else if (item.tipoItem==2)
    		$state.transitionTo("viewSlideShow",{id: item.id, name: item.nome});
    	else if (item.tipoItem==3)
    		$state.transitionTo("viewItemShop",{id: item.id, name: item.nome});
    	else
    		window.alert("Tipo item non supportato")
    };
    
    
    function init () {
    	getItems();
    };

    init();
    
});
