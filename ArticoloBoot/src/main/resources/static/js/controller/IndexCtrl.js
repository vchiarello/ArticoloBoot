//controller della home page con la lista degli item
angular.module("blogApp").controller("IndexCtrl", function ($scope, Item, $state) {
	
	//label per l'internazionalizzazione dell'applicativo
	$scope.labelHome=messaggi['toolbar.home'];
	$scope.labelAbout=messaggi['toolbar.about'];
	$scope.labelContact=messaggi['toolbar.contact'];
	$scope.labelAction=messaggi['toolbar.action'];
	$scope.labelEditList=messaggi['toolbar.action.editList'];
	$scope.labelNewItem=messaggi['toolbar.action.newItem'];
	$scope.labelNewItemShop=messaggi['toolbar.action.newItemShop'];
	$scope.labelNewSlideShow=messaggi['toolbar.action.newSlideShow'];
	$scope.labelAnotherAction=messaggi['toolbar.action.anotherAction'];
	$scope.labelSeparatedLink=messaggi['toolbar.action.separatedLink'];
	$scope.labelNavHeader=messaggi['toolbar.action.navHeader'];
	$scope.labelViewCart=messaggi['toolbar.action.viewCart'];
	$scope.placeHolderQuerySearch=messaggi['toolbar.placeHolder.querySearch'];
	
	
	$scope.carrello=function(){
		alert("visualizza carello");
	}
	
	$scope.querySearch="";
	
	$scope.search = function(){
		if ($scope.querySearch != null && $scope.querySearch.trim().length>0)
		alert($scope.querySearch);

		$state.transitionTo("searchItem",{query: $scope.querySearch});
	}

});
