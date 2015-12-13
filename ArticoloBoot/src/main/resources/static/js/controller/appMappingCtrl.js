angular.module("blogApp").controller("MappingsCtrl", function($scope, $state, mappingsFactory) {
    function init() {
        mappingsFactory.getMappings().success(function(data) {
           $scope.mappings = data;
        });
    }

    init();
});
