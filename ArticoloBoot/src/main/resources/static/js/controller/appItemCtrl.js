//controller della home page con la lista degli item
angular.module("blogApp").controller("ItemCtrl", function ($scope, Item, $state) {
    function init() {
        getItems();
    }

    $scope.viewItem = function (item) {
    	if (item.tipoItem==1)
    		$state.transitionTo("viewItem",{itemId: item.id});
    	else if (item.tipoItem==2)
    		$state.transitionTo("viewSlideShowItem",{itemId: item.id});
    	else
    		window.alert("Tipo item non supportato")
    };
    
    function getItems() {
        $scope.items = Item.query();
    };

    init();
});
