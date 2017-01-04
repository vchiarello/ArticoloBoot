//oggetto che tiene le operazioni sull'oggetto Item. 
//L'oggetto ritornato da questa factory è un oggetto $resource che permette
//di chiamare dei servizi rest che lo riguardano.
//https://docs.angularjs.org/api/ngResource/service/$resource
angular.module("blogApp").factory("CartOperations", function (Cart) {

	function addToCart(idItem, nomeItem){
		cc = new Cart();
		cc.id=idItem;
		cc.nome=nomeItem;
		cc.$save()
	}

	function removeFromCart(idItem, nomeItem){
		cc = new Cart();
		cc.id=idItem;
		cc.nome=nomeItem;
		cc.$delete()
	}

	function getCart(idItem, nomeItem){
		cc = new Cart();
		cc.$get()
	}

	return {
		addToCart:addToCart,
		removeFromCart:removeFromCart,
		getCart:getCart
	};


});
