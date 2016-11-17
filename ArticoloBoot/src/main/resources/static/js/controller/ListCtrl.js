//controller della home page con la lista degli item
angular.module("blogApp").controller("ListCtrl", function ($scope, Item, $state) {

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
    
    $scope.getItems = function () {
    	$scope.items = Item.query();
    };
    
    
    $scope.messaggio = messaggiErrore['list.spinner.message'];

    function init () {
    	$scope.items = Item.query({attivi:'si'});
    };

    init();
    
});
