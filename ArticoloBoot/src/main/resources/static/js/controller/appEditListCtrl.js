//controller della home che edit gli item solo per amministratori
angular.module("blogApp").controller("EditListCtrl", function ($scope, Item, ItemOperation, $state) {
    function init() {
        getItems();
    }


    function getItems() {
        $scope.items = Item.query();
    };

    $scope.deleteItem = function (item) {
    	ItemOperation.deleteItem(item);
    	var indice = $scope.items.indexOf(item);
    	$scope.items.splice(indice,1);

    };

    $scope.editItem = function (item) {
    	if (item.tipoItem==1)
    		$state.transitionTo("editItem",{itemId: item.id, itemName:item.name});
    	else if (item.tipoItem==2)
    		$state.transitionTo("editSlideShowItem",{itemId: item.id, itemName:item.name});
    	else
    		window.alert("Tipo item non supportato")
    };

    $scope.viewItem = function (item) {
    	if (item.tipoItem==1)
    		$state.transitionTo("viewItem",{itemId: item.id, itemName:item.name});
    	else if (item.tipoItem==2)
    		$state.transitionTo("viewSlideShowItem",{itemId: item.id, itemName:item.name});
    	else
    		window.alert("Tipo item non supportato")
    };

	$scope.hideItem = function (item) {
    	ItemOperation.nascondiItem(item);
	}
	
	
	$scope.isNascosto = function (item){
		return ItemOperation.isNascosto(item)
	}
	
    $scope.showItem = function (item) {
    	ItemOperation.showItem(item);
    };

   
    init();
});

