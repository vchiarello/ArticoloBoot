//controller per la visualizzaizone del singolo item
angular.module("blogApp").controller("ViewCartCtrl", function ($scope, Item, $stateParams, $state,Cart,CartOperations) {

	//label per l'internazionalizzazione dell'applicativo
	$scope.buttonDelete=messaggi['viewCart.button.deleteRow'];
	$scope.buttonSave=messaggi['viewCart.button.saveCart'];
	$scope.labelProduct=messaggi['viewCart.label.product'];
	$scope.labelQuantity=messaggi['viewCart.label.quantinty'];
	$scope.labelPrice=messaggi['viewCart.button.price'];
	$scope.labelTotale=messaggi['viewCart.button.totale'];
	
	function init() {
    	$scope.cart = CartOperations.getCart();
    }
   
    init();
    
    $scope.updateCart=function(){
    	//array con i nomi dei file
		if ($scope.cart===undefined || $scope.cart==null) return;
		cart = new Cart($scope.cart);
		CartOperations.updateCart(cart);
//		$scope.cart = cart.$update(function(cart) {
//        	cc = cart.$get()
//        	alert("finito di salvare")
//        	return cc
//        },function() {
//            alert("errore di salvataggio")
//            return $scope.cart;
//        })
    }

	$scope.removeRow = function(idItem, nameItem){
		$scope.cart = CartOperations.removeFromCart(idItem, nameItem);
	}
    
    
    $scope.validateQuantity=function(quantita){
    	
    }
});



