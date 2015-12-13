angular.module("blogApp").app.factory("mappingsFactory", function($http) {
    var factory = {};
    factory.getMappings = function() {
        return $http.get(URLS.mappingsUrl);
    }
    return factory;
});

