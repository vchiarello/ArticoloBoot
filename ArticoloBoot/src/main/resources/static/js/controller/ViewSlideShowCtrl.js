//controller per la visualizzaizone del singolo item
angular.module("blogApp").controller("ViewItemShopCtrl", function ($scope, Item, $stateParams, $state) {
    function init() {
        $scope.item = Item.get({id:$stateParams.id,name:$stateParams.name})
    }
   
    init();
});



