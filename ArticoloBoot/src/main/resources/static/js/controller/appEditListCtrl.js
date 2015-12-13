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

