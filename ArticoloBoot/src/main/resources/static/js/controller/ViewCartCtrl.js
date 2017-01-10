//controller per la visualizzaizone del singolo item
angular.module("blogApp").controller("ViewCartCtrl", function ($scope, Item, $stateParams, $state,Cart,CartOperations) {
    function init() {
    	$scope.cart = CartOperations.getCart();
    }
   
    init();
    
    $scope.updateCart=function(){
    	
    }
    
    $scope.validateQuantity=function(quantita){
    	
    }
});



