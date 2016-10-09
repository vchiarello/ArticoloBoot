//oggetto che tiene le operazioni sull'oggetto Item. 
//L'oggetto ritornato da questa factory è un oggetto $resource che permette
//di chiamare dei servizi rest che lo riguardano.
//https://docs.angularjs.org/api/ngResource/service/$resource
angular.module("blogApp").factory("Item", function ($resource) {
	var csrf_token = "";
	if (document.querySelector('input[name="_csrf"]') !=null) 	
		csrf_token = document.querySelector('input[name="_csrf"]').getAttribute('value');


	
//	If the parameter value is prefixed with @ then the value for that parameter will be extracted from the corresponding property on 
//	the data object (provided when calling an action method). For example, if the defaultParam object is 
//	{someParam: '@someProp'} then the value of someParam will be data.someProp.
	//ATTENZIONE: come scritto nelle righe sopra se il paramentro ha la @ vuol dire che viene preso dall'oggetto
	//quindi deve avere lo stesso nome della proprietà dell'oggetto
	return $resource(URLS.items, {id: "@id", name: "@nome"}, {
		  update: {method: 'PUT', headers: {'X-CSRF-TOKEN': csrf_token}},
		  query:{method: 'GET', isArray:true},
		  save: {method: 'POST', headers: {'X-CSRF-TOKEN': csrf_token}},
		  delete: {method: 'DELETE', headers: {'X-CSRF-TOKEN': csrf_token}}
	});
});
