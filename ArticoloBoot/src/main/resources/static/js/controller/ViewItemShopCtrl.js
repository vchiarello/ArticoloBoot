angular.module('blogApp').controller('ViewItemShopCtrl',['$scope','ItemShop', '$stateParams','$http','Cart','CartOperations', '$q',
	function ($scope, ItemShop, $stateParams,$http,Cart,CartOperations,$q){
	
//	$http.get("/rest/itemShop/infoAllegati/"+$stateParams.id+"/"+$stateParams.name).
//	then(function(data){
//		//$scope.info = data;
//		$scope.immagini = new Array();
//		for (i = 0; i < data.data.length;i++){
//			$scope.immagini[i] = new Object();
//			$scope.immagini[i].indirizzo = "/rest/download?idAllegato="+data.data[i].id+"&nomeAllegato="+data.data[i].nomeAllegato;
//			$scope.immagini[i].titolo = data.data[i].nomeAllegato;
//		}
//	},
//	function(data){
//		$scope.info = data
//	})

	$scope.buttonAddCart=messaggi['viewItemShop.button.addCart'];

	function init() {
        $scope.promessa = new Object();
    }
	
	
	$scope.item = ItemShop.get({id:$stateParams.id,name:$stateParams.name},function(item){
		$scope.immagini = new Array();
		for (i = 0; i < item.listaFileSalvati.length;i++){
			$scope.immagini[i] = new Object();
			$scope.immagini[i].indirizzo = "/rest/download?idAllegato="+item.listaFileSalvati[i].idAllegato+"&nomeAllegato="+item.listaFileSalvati[i].nomeAllegato;
			$scope.immagini[i].titolo = item.listaFileSalvati[i].nomeAllegato;
		}
	});

	$scope.cart = CartOperations.getCart();
	
	$scope.aggiungiCarrello = function(idItem, nameItem){
		$scope.promessa = $q.defer();
		CartOperations.addToCart(idItem, nameItem,$scope.promessa);
		$scope.promessa.resolve('finito');
	}
	
	
	

}]);

