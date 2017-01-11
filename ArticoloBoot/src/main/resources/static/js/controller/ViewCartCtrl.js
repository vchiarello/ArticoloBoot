//controller per la visualizzaizone del singolo item
angular.module("blogApp").controller("ViewCartCtrl", function ($scope, Item, $stateParams, $state,Cart,CartOperations) {
    function init() {
    	$scope.cart = CartOperations.getCart();
    }
   
    init();
    
    $scope.updateCart=function(){
    	//array con i nomi dei file
		if ($scope.cart===undefined || $scope.cart==null) return;
		cart = new Cart($scope.cart);
        cart.$update(function(cart) {
            alert("finito di salvare")
        },function() {
            alert("errore di salvataggio")
        })
    }

    
    $scope.validateQuantity=function(quantita){
    	
    }
});



