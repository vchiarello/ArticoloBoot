//oggetto che tiene le operazioni sull'oggetto Tag. 
//L'oggetto ritornato da questa factory è un oggetto $resource che permette
//di chiamare dei servizi rest che lo riguardano.
//https://docs.angularjs.org/api/ngResource/service/$resource
angular.module("blogApp").factory("Tag", function ($resource) {
  return $resource(URLS.tags, {id: "@id"}, {
      update: {
          method: 'PUT'
      }
  });
});

