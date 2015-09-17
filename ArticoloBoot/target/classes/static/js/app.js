var app = angular.module("blogApp", ["ui.router", "ngResource", "angularFileUpload"]);



app.config(function ($stateProvider, $urlRouterProvider) {
	
	
	
    $urlRouterProvider.otherwise("homeItem");

    $stateProvider
        .state('homeItem', {
            url:'/homeItem',
            templateUrl: URLS.partialsListItem,
            controller: 'ItemCtrl'
        }).state('edit', {
            url:'/edit/:itemId',
            templateUrl: URLS.partialsEditItem,
            controller: 'ItemEditCtrl'
        }).state('createItem', {
            url:'/createItem',
            templateUrl: URLS.partialsCreateItem,
            controller: 'ItemCreateCtrl'
        }).state('createItemw1', {
            url:'/createItemw1',
            templateUrl: URLS.partialsCreateItemw1,
            controller: 'ItemCreateCtrl1'
        }).state('createItemw2', {
            url:'/createItemw2',
            templateUrl: URLS.partialsCreateItemw2,
            controller: 'ItemCreateCtrl2'
        }).state('createItemw3', {
            url:'/createItemw3',
            templateUrl: URLS.partialsCreateItemw3,
            controller: 'ItemCreateCtrl'
        }).state('create', {
            url:'/create',
            templateUrl: URLS.partialsCreate,
            controller: 'HotelCtrl'
        }).state('endpoints', {
            url: '/endpoints',
            templateUrl: URLS.partialsMappings,
            controller: 'MappingsCtrl'
        });
});


app.factory('itemWebflow', function () {
    var step1;
    var step2;
    function setStep1(data){
    	step1=data;
    }
    function getStep1(){
    	return step1;
    }
    function setStep2(data){
    	step2=data;
    }
    function getStep2(){
    	return step2;
    }
    function reset(){
    	item=null;
    }
    return{
    	setStep1: setStep1,
    	getStep1: getStep1,
    	setStep2: setStep2,
    	getStep2: getStep2,
    	reset:reset
    }
});

app.factory("Hotel", function ($resource) {
    return $resource(URLS.hotels, {id: "@id"}, {
        update: {
            method: 'PUT'
        }
    });
});

app.factory("Item", function ($resource) {
	var csrf_token = "";
	if (document.querySelector('input[name="_csrf"]') !=null) 	
		csrf_token = document.querySelector('input[name="_csrf"]').getAttribute('value');
    return $resource(URLS.items, {id: "@id"}, {
        update: {method: 'PUT', headers: {'X-CSRF-TOKEN': csrf_token}},
        save: {method: 'POST', headers: {'X-CSRF-TOKEN': csrf_token}}
    });
});

app.factory("Tag", function ($resource) {
    return $resource(URLS.tags, {id: "@id"}, {
        update: {
            method: 'PUT'
        }
    });
});

app.factory("mappingsFactory", function($http) {
    var factory = {};
    factory.getMappings = function() {
        return $http.get(URLS.mappingsUrl);
    }
    return factory;
});

app.controller("HotelCtrl", function ($scope, Hotel, $state) {
    function init() {
        $scope.getHotels();
    }


    $scope.getHotels = function () {
        $scope.hotels = Hotel.query();
    };

    $scope.deleteHotel = function (hotel) {
        return hotel.$delete({}, function () {
            $scope.hotels.splice($scope.hotels.indexOf(hotel), 1);
        });
    };

    $scope.createHotel = function () {
        var hotel = new Hotel($scope.hotel);
        hotel.$save({}, function() {
            $state.transitionTo("home");
        });
    };

    init();
});

app.controller("ItemCtrl", function ($scope, Item, $state) {
    function init() {
        $scope.getItems();
    }


    $scope.getItems = function () {
        $scope.items = Item.query();
    };

   
    init();
});

app.controller("HotelEditCtrl", function ($scope, Hotel, $state, $stateParams) {
    function init() {
        $scope.hotel = Hotel.get({id:$stateParams.hotelId})
    }

    $scope.updateHotel = function() {
       var hotel = new Hotel($scope.hotel);
       hotel.$update().then(function() {
           $state.transitionTo("home");
       }) ;
    }
    init();
});

app.controller("ItemEditCtrl", function ($scope,  Tag, Item, $state, $stateParams, FileUploader) {
    function init() {
        $scope.item = Item.get({id:$stateParams.itemId})
    }

    $scope.updateHotel = function() {
       var item = new Item($scope.item);
       var attendi = false;
       for (i = 0; i < uploader.queue.length;i++){
			if (item.listaFile===undefined || item.listaFile==null) item.listaFile=new Array();
			item.listaFile[i]=uploader.queue[i].file.name;
			if (!uploader.queue[i].isSuccess){
				attendi=true;
	       		uploader.queue[i].upload();
			}
       }
       
       if (attendi){
	        uploader.onCompleteAll = function() {
	            item.$update().then(function() {
	                $state.transitionTo("homeItem");
	            });
	        };
       }else{
           item.$update().then(function() {
               $state.transitionTo("homeItem");
           });
       }    
       
    }
    
    $scope.getTags = function () {
        $scope.listaTags = Tag.query();
    };

	var csrf_token = "";
	if (	document.querySelector('input[name="_csrf"]')!=null)
		csrf_token = document.querySelector('input[name="_csrf"]').getAttribute('value');

    var uploader = $scope.uploader = new FileUploader({
        url: '/rest/upload',
        headers : {
        	'X-CSRF-TOKEN': csrf_token
            }
    });
    
    $scope.labelCancellaRipristina = function ( id){
    	return _labelCancellaRipristina(id);
    }
    
    function _labelCancellaRipristina (id){
    	if ($scope.item.listaFileDaCancellare==null) return "cancella";
    	for (i = 0; i < $scope.item.listaFileDaCancellare.length; i++){
    		if ($scope.item.listaFileDaCancellare[i]==id)return "ripristina";
    	}
    	return "cancella";
    }

    $scope.cancellaRipristina = function (id){    	
    	if (_labelCancellaRipristina(id)=="cancella") cancella(id);
    	else ripristina(id);
    }

     function cancella(id){
    	if ($scope.item.listaFileSalvati==null) return ;
    	
    	if ($scope.item.listaFileDaCancellare==null) $scope.item.listaFileDaCancellare= new Array();
        	$scope.item.listaFileDaCancellare.push(id);
    		
    }

    function ripristina (id){
    	if ($scope.item.listaFileSalvati==null||$scope.item.listaFileDaCancellare==null) return ;
    	for (i = 0; i < $scope.item.listaFileDaCancellare.length; i++){
    		if ($scope.item.listaFileDaCancellare[i]==id){
    			$scope.item.listaFileDaCancellare.splice(i,1);
    			return;
    		}
    	}
    }


    
    init();
});

app.controller("ItemCreateCtrl", function ($scope, Tag, Item, $state, $stateParams, FileUploader) {

	
    $scope.cancel = function () {
        $state.transitionTo("homeItem");
    }

    $scope.createItem = function () {
        var item = new Item($scope.item);
        var attendi = false;
        for (i = 0; i < uploader.queue.length;i++){
    		if (item.listaFile===undefined) item.listaFile=new Array();
        	item.listaFile[i]=uploader.queue[i].file.name;
        	if (uploader.queue[i].isSuccess){
        	}else{
        		attendi=true;
        		uploader.queue[i].upload();
        	}
        }
        
        if (attendi){
	        uploader.onCompleteAll = function() {
	            item.$save({}, function() {
	                $state.transitionTo("homeItem");
	            })
	        };
        }else{
        	item.$save({}, function() {
                $state.transitionTo("homeItem");
            })
        }    
//        item.$save({}, function() {
//            $state.transitionTo("homeItem");
//        })
        

    };

	var csrf_token = ""
	if (	document.querySelector('input[name="_csrf"]')!=null)
		csrf_token = document.querySelector('input[name="_csrf"]').getAttribute('value');

    var uploader = $scope.uploader = new FileUploader({
        url: '/rest/upload',
        headers : {
        	'X-CSRF-TOKEN': csrf_token
            }
    });

	
	function init() {
        $scope.getTags();
    }

    $scope.getTags = function () {
        $scope.listaTags = Tag.query();
    };


    init();
});

app.controller("ItemCreateCtrl1", function ($scope, Tag,  $state, itemWebflow, FileUploader) {

	$scope.createItem1 = function () {
		window.alert($scope.item.nome+" "+$scope.item.titolo)
        itemWebflow.setStep1($scope.item);
        $state.transitionTo("createItemw2");
    };

	function init() {
        $scope.getTags();
    }

    $scope.getTags = function () {
        $scope.listaTags = Tag.query();
    	$scope.item=itemWebflow.getStep1();
    };

    init();
});

app.controller("ItemCreateCtrl2", function ($scope, Tag, $state, itemWebflow, FileUploader) {
	
    $scope.createItem2 = function () {
        itemWebflow.setStep2($scope.item);
        $state.transitionTo("createItemw1");
    };

	function init() {
        $scope.getTags();
    	$scope.item=itemWebflow.getStep2();
    }

    $scope.getTags = function () {
        $scope.listaTags = Tag.query();
    };

    init();
});

app.controller("MappingsCtrl", function($scope, $state, mappingsFactory) {
    function init() {
        mappingsFactory.getMappings().success(function(data) {
           $scope.mappings = data;
        });
    }

    init();
});
