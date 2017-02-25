//oggetto che tiene le operazioni sull'oggetto Item. 
//L'oggetto ritornato da questa factory è un oggetto $resource che permette
//di chiamare dei servizi rest che lo riguardano.
//https://docs.angularjs.org/api/ngResource/service/$resource
angular.module("blogApp").factory("CartOperations", function (Cart) {

	function addToCart(idItem, nomeItem){
		cc = new Cart();
		cc.id=idItem;
		cc.nome=nomeItem;
		cc.$save();
		
	}

	function removeFromCart(idCartDetail){
		cc = new Cart();
		cc.id=idCartDetail;
		cc.$delete(null,function(){cc = cc.$get()});
		return cc;
	}

	function updateCart(cart){
		var C = new Cart(cart);
		var cc=null;
		C.$update(null,
				function(){cc = C.$get(); alert("finito di salvare")},
				function(){cc = C.$get(); alert("errore di salvataggio")}
				);
		return cc;
	}

	function getCart(){
		cc = new Cart();
		cc.$get(null,function(){console.log("cart:" + cc.idCart);});
		return cc;
	}

	return {
		addToCart:addToCart,
		removeFromCart:removeFromCart,
		getCart:getCart,
		updateCart:updateCart
	};


});
