var app = angular.module("blogApp", ["ui.router", "ngResource", "angularFileUpload"]);



app.config(function ($stateProvider, $urlRouterProvider) {
	
	
	
    $urlRouterProvider.otherwise("homeItem");

    $stateProvider
	    .state('login', {
	        url:'/login',
	        templateUrl: URLS.login,
	        controller: 'LoginCtrl'
	    }).state('homeItem', {
	        url:'/homeItem',
	        templateUrl: URLS.partialsListItem,
	        controller: 'ItemCtrl'
	    }).state('homeEditListItem', {
	        url:'/homeEditListItem',
	        templateUrl: URLS.partialsEditList,
	        controller: 'EditListCtrl'
	    }).state('edit', {
            url:'/edit/:itemId',
            templateUrl: URLS.partialsEditItem,
            controller: 'ItemEditCtrl'
	    }).state('view', {
            url:'/view/:itemId',
            templateUrl: URLS.partialsViewItem,
            controller: 'ViewItemCtrl'
        }).state('createItem', {
            url:'/createItem',
            templateUrl: URLS.partialsCreateItem,
            controller: 'ItemCreateCtrl'
        }).state('viewItem', {
            url:'/viewItem',
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

//se si decidesse di fare un flow con angular si potrebbe utilizzare un factory con i vari metodi per settare le variabili 
//con tutte le info delle maschere
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

//oggetto che tiene le operazioni sull'oggetto Item. 
//L'oggetto ritornato da questa factory è un oggetto $resource che permette
//di chiamare dei servizi rest che lo riguardano.
//https://docs.angularjs.org/api/ngResource/service/$resource
app.factory("Item", function ($resource) {
	var csrf_token = "";
	if (document.querySelector('input[name="_csrf"]') !=null) 	
		csrf_token = document.querySelector('input[name="_csrf"]').getAttribute('value');
    return $resource(URLS.items, {id: "@id"}, {
        update: {method: 'PUT', headers: {'X-CSRF-TOKEN': csrf_token}},
        save: {method: 'POST', headers: {'X-CSRF-TOKEN': csrf_token}}
    });
});

//oggetto che tiene le operazioni sull'oggetto Tag. 
//L'oggetto ritornato da questa factory è un oggetto $resource che permette
//di chiamare dei servizi rest che lo riguardano.
//https://docs.angularjs.org/api/ngResource/service/$resource
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

//controller della home page con la lista degli item
app.controller("ItemCtrl", function ($scope, Item, $state) {
    function init() {
        $scope.getItems();
    }

    $scope.getItems = function () {
        $scope.items = Item.query();
    };

   
    init();
});

//controller della home che edit gli item solo per amministratori
app.controller("EditListCtrl", function ($scope, Item, $state) {
    function init() {
        $scope.getItems();
    }


    $scope.getItems = function () {
        $scope.items = Item.query();
    };

   
    init();
});

//controller per la visualizzaizone del singolo articolo
app.controller("ViewItemCtrl", function ($scope, Item, $stateParams, $state) {
    function init() {
        $scope.item = Item.get({id:$stateParams.itemId})
    }
   
    init();
});




app.controller("LoginCtrl", function ($scope, Item, $state) {
});

//controller usato nell'edit degli item
app.controller("ItemEditCtrl", function ($scope,  Tag, Item, $state, $stateParams, FileUploader) {
	
	//metodo che preleva l'item dal database
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

    //appena si aggiunge un file questo viene immediatamenta uploadato sul server
    uploader.onAfterAddingFile = function(fileItem) {
        fileItem.upload();
    };
    
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
        console.info('onSuccessItem', fileItem, response, status, headers);
		
		if ($scope.item.listaFile===undefined || $scope.item.listaFile==null) $scope.item.listaFile=new Array();
		$scope.item.listaFile[uploader.queue.length]=fileItem._file.name;
		alert("aggiunto il file "+ fileItem._file.name + " alla lista");
    };

    
    
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

    $scope.cancel = function () {
        $state.transitionTo("homeItem");
    }

    
	//Appena si accede alla pagina si preleva l'item passato come parametro
    init();
});

//controller per la creazione degli item
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


app.controller("MappingsCtrl", function($scope, $state, mappingsFactory) {
    function init() {
        mappingsFactory.getMappings().success(function(data) {
           $scope.mappings = data;
        });
    }

    init();
});

//controller per fare un wizard invece della singola pagina in edit/create
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
