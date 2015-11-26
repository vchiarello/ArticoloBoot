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
	
    //calcolo del toker csrf_token
	var csrf_token = "";
	if (	document.querySelector('input[name="_csrf"]')!=null)
		csrf_token = document.querySelector('input[name="_csrf"]').getAttribute('value');
	
	//definizione della variabile che esegue gli upload per evitare problemi con spring security si aggiunge nell'header della richista il csrf token
    var uploader = $scope.uploader = new FileUploader({
        url: '/rest/upload',
        headers : {
        	'X-CSRF-TOKEN': csrf_token
            }
    });


    //metodo che preleva l'item dal database
	function init() {
        $scope.item = Item.get({id:$stateParams.itemId})
    }

	//Appena si accede alla pagina si preleva l'item passato come parametro
    init();

    //tutti i tag della combo Dei Tag
    $scope.getTags = function () {
        $scope.listaTags = Tag.query();
    };

    //transizione in caso di premuta del pulsante di cancel
    $scope.cancel = function () {
        $state.transitionTo("homeEditListItem");
    }

    //salvataggio dell'item
    $scope.updateItem = function() {
       var item = new Item($scope.item);
       
       //se non ci sono upload ancora in sospeso
       		//si aspetta che finisca poi si salva e si va verso la home di edit
       if (uploader.getNotUploadedItems != null && uploader.getNotUploadedItems.length >0){
    	   uploader.onCompleteAll = function() {
	           item.$update().then(function() {
	               $state.transitionTo("homeEditListItem");
	           });
    	   }	
    	//altrimenti si salva e quindi si naviga verso la home di edit
       }else{
           item.$update().then(function() {
               $state.transitionTo("homeEditListItem");
           });
       }
    }

    //appena si aggiunge un file questo viene immediatamenta uploadato sul server
    uploader.onAfterAddingFile = function(fileItem) {
        fileItem.upload();
    };
    
    //all'aggiunta di un file questo viene immediatamente caricato sul server e viene aggiornata la lista dei file nuovi da inserire nel db
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
        console.info('onSuccessItem', fileItem, response, status, headers);
		if ($scope.item.listaFile===undefined || $scope.item.listaFile==null) $scope.item.listaFile=new Array();
		$scope.item.listaFile[uploader.queue.length]=fileItem._file.name;
    };

    //per i file già caricate c'è sia la funzionalità di cancellazione che quella di ripristina 
    $scope.cancellaRipristina = function (id){
    	if (_isCancellato(id)){
    		ripristina(id);
    		
    	}else{ 
    		cancella(id);
    	
    	}
    }

    //function per capire se un elemento è stato contrassegnato per la cancellazione oppure no
    $scope.isCancellato = function (id){
    	return _isCancellato(id);	
    }
    
    //funzione effettiva
    function _isCancellato (id){
    	if ($scope.item.listaFileDaCancellare==null) return false;
    	for (i = 0; i < $scope.item.listaFileDaCancellare.length; i++){
    		if ($scope.item.listaFileDaCancellare[i]==id)return true;
    	}
    	return false;
    }
    
    //aggiunta dell'id nella list dei file da cancellare
    function cancella(id){
    	if ($scope.item.listaFileSalvati==null) return ;
    	
    	if ($scope.item.listaFileDaCancellare==null) $scope.item.listaFileDaCancellare= new Array();
        	$scope.item.listaFileDaCancellare.push(id);
    }

    //ripristino dell'id tra i file che saranno ancora associati all'item
    function ripristina (id){
    	if ($scope.item.listaFileSalvati==null||$scope.item.listaFileDaCancellare==null) return ;

    	for (i = 0; i < $scope.item.listaFileDaCancellare.length; i++){
    		if ($scope.item.listaFileDaCancellare[i]==id){
    			$scope.item.listaFileDaCancellare.splice(i,1);
    			return;
    		}
    	}
    }


    
});

//controller per la creazione degli item
app.controller("ItemCreateCtrl", function ($scope, Tag, Item, $state, $stateParams, FileUploader) {

    //calcolo del toker csrf_token
	var csrf_token = "";
	if (	document.querySelector('input[name="_csrf"]')!=null)
		csrf_token = document.querySelector('input[name="_csrf"]').getAttribute('value');
	
	//definizione della variabile che esegue gli upload per evitare problemi con spring security si aggiunge nell'header della richista il csrf token
    var uploader = $scope.uploader = new FileUploader({
        url: '/rest/upload',
        headers : {
        	'X-CSRF-TOKEN': csrf_token
            }
    });

	function init() {
		getTags();
    }

    //tutti i tag della combo Dei Tag
    function getTags() {
        $scope.listaTags = Tag.query();
    };

	//Appena si accede alla pagina si preleva l'item passato come parametro
    init();

	
    //transizione in caso di premuta del pulsante di cancel
    $scope.cancel = function () {
        $state.transitionTo("homeEditListItem");
    }

    //salvataggio dell'item
    $scope.createItem = function () {
        var item = new Item($scope.item);

        //se non ci sono upload ancora in sospeso
   		//si aspetta che finisca poi si salva e si va verso la home di edit
        if (uploader.getNotUploadedItems != null && uploader.getNotUploadedItems.length >0){
        	uploader.onCompleteAll = function() {
 	           item.$save().then(function() {
	               $state.transitionTo("homeEditListItem");
	           });
    	   }	
    	//altrimenti si salva e quindi si naviga verso la home di edit
       }else{
           item.$save().then(function() {
               $state.transitionTo("homeEditListItem");
           });
       }
    }


    //appena si aggiunge un file questo viene immediatamenta uploadato sul server
    uploader.onAfterAddingFile = function(fileItem) {
        fileItem.upload();
    };
    
    //all'aggiunta di un file questo viene immediatamente caricato sul server e viene aggiornata la lista dei file nuovi da inserire nel db
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
        console.info('onSuccessItem', fileItem, response, status, headers);
		if ($scope.item.listaFile===undefined || $scope.item.listaFile==null) $scope.item.listaFile=new Array();
		$scope.item.listaFile[uploader.queue.length]=fileItem._file.name;
    };
    
	
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
