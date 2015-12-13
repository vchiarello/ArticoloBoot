//controller della home page con la lista degli item
angular.module("blogApp").controller("ItemCtrl", function ($scope, Item, $state) {
    function init() {
        getItems();
    }
    
    function getItems() {
        $scope.items = Item.query();
    };

    init();
});
