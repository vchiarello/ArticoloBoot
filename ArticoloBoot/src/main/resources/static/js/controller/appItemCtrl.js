//controller della home page con la lista degli item
angular.module("blogApp").controller("ItemCtrl", function ($scope, Item, $state, spinnerService) {

    $scope.viewItem = function (item) {
    	if (item.tipoItem==1)
    		$state.transitionTo("viewItem",{id: item.id, name: item.nome});
    	else if (item.tipoItem==2)
    		$state.transitionTo("viewSlideShowItem",{id: item.id, name: item.nome});
    	else
    		window.alert("Tipo item non supportato")
    };
    
    $scope.getItems = function () {
//    	spinnerService.show('booksSpinner');
//      $scope.items = Item.query(function(){spinnerService.hide('booksSpinner');}, function(){spinnerService.hide('booksSpinner');});
      $scope.items = Item.query();
    };

    function init () {
//    	spinnerService.show('booksSpinner');
//      $scope.items = Item.query(function(){spinnerService.hide('booksSpinner');}, function(){spinnerService.hide('booksSpinner');});
      $scope.items = Item.query();
    };

    init();
    
});
