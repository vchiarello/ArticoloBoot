//controller della home che edit gli item solo per amministratori
angular.module("blogApp").controller("EditListCtrlOld", function ($scope, Item, ItemOperation, $state) {
    function init() {
        getItems();
    }

    $scope.messaggio = messaggiErrore['list.spinner.message'];


    function getItems() {
        $scope.items = Item.query();
    };

    $scope.deleteItem = function (item) {

    	var it = new Item(item);
    	bootbox.confirm({
    		message: messaggiErrore['editList.deleteItem.function.Confirm'](item.titolo), 
    		callback: function(result){
    			if (result){
	        		it.$delete({id:item.id, name:item.nome}, 
	    				function(){
		        	    	var indice = $scope.items.indexOf(item);
		        	    	$scope.items.splice(indice,1);
		    	        	bootbox.alert({message: messaggiErrore['editList.deleteItem.result']});
	    	        	})
    			}
    		} 
    	})
    };

    $scope.editItem = function (item) {
    	if (item.tipoItem==1)
    		$state.transitionTo("editItem",{id: item.id, name: item.nome});
    	else if (item.tipoItem==2)
    		$state.transitionTo("editSlideShowItem",{id: item.id, name: item.nome});
    	else
    		window.alert("Tipo item non supportato")
    };

    $scope.viewItem = function (item) {
    	if (item.tipoItem==1)
    		$state.transitionTo("viewItem",{id: item.id, name: item.nome});
    	else if (item.tipoItem==2)
    		$state.transitionTo("viewSlideShowItem",{id: item.id, name: item.nome});
    	else
    		window.alert("Tipo item non supportato")
    };

	$scope.hideItem = function (item) {
    	ItemOperation.nascondiItem(item);
	}
	
	
	$scope.isNascosto = function (item){
		return ItemOperation.isNascosto(item)
	}
	
    $scope.showItem = function (item) {
    	ItemOperation.showItem(item);
    };

   
    init();
});

