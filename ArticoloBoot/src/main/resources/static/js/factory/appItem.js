//oggetto che tiene le operazioni sull'oggetto Item. 
//L'oggetto ritornato da questa factory è un oggetto $resource che permette
//di chiamare dei servizi rest che lo riguardano.
//https://docs.angularjs.org/api/ngResource/service/$resource
angular.module("blogApp").factory("Item", function ($resource) {
	var csrf_token = "";
	if (document.querySelector('input[name="_csrf"]') !=null) 	
		csrf_token = document.querySelector('input[name="_csrf"]').getAttribute('value');

    
	return $resource(URLS.items, {id: "@id", name: "@name"}, {
		  update: {method: 'PUT', headers: {'X-CSRF-TOKEN': csrf_token}},
		  save: {method: 'POST', headers: {'X-CSRF-TOKEN': csrf_token}},
		  delete: {method: 'DELETE', headers: {'X-CSRF-TOKEN': csrf_token}}
	});
});
