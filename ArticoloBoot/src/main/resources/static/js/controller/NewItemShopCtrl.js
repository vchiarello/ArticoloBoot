//controller per la creazione degli item
angular.module("blogApp").controller("NewItemShopCtrl", ['$scope', '$http', 'Tag', 'ItemShop', 'ItemProperty', '$state', '$stateParams', 'FileUploader', '$q', '$cookies', function ($scope, $http, Tag, ItemShop, ItemProperty, $state, $stateParams, FileUploader, $q, $cookies
		) {
	
	//Label usate nella pagina
	$scope.labelNome=messaggi['createItemShop.label.name'];
	$scope.placeHolderNome=messaggi['createItemShop.placeholder.name'];
	$scope.labelTitle=messaggi['createItemShop.label.title'];
	$scope.placeHolderTitle=messaggi['createItemShop.placeholder.title'];
	$scope.labelText = messaggi['createItemShop.label.text'];
	$scope.placeHolderText = messaggi['createItemShop.placeholder.text'];
	$scope.labelQuantita = messaggi['createItemShop.label.quantity'];
	$scope.placeHolderQuantita = messaggi['createItemShop.placeholder.quantity'];
	$scope.labelPrezzo = messaggi['createItemShop.label.prezzo'];
	$scope.placeHolderPrezzo = messaggi['createItemShop.placeholder.prezzo'];
	$scope.labelPublishDate = messaggi['createItemShop.label.publishDate'];
	$scope.placeHolderPublishDate = messaggi['createItemShop.placeholder.publishDate'];
	$scope.labelExpirationDate = messaggi['createItemShop.label.expirationDate'];
	$scope.placeHolderExpirationDate = messaggi['createItemShop.placeholder.expirationDate'];
	$scope.labelHiddenDate = messaggi['createItemShop.label.hiddenDate'];
	$scope.placeHolderHiddenDate = messaggi['createItemShop.placeholder.hiddenDate'];
	$scope.labelAuthor=messaggi['createItemShop.label.author'];
	$scope.placeholderAuthor=messaggi['createItemShop.placeholder.author'];
	$scope.labelTag = messaggi['createItemShop.label.tag'];
	$scope.labelNewTag = messaggi['createItemShop.label.newTag'];
	$scope.placeholderNewTag = messaggi['createItemShop.placeholder.newTags'];
	$scope.labelResource = messaggi['createItemShop.label.resources'];
	$scope.labelResourceName = messaggi['createItemShop.label.resources.name'];
	$scope.labelResourceSize = messaggi['createItemShop.label.resources.size'];
	$scope.labelResourceType = messaggi['createItemShop.label.resources.type'];
	$scope.labelResourceProgress = messaggi['createItemShop.label.resources.progress'];
	$scope.labelResourceState = messaggi['createItemShop.label.resources.state'];
	$scope.labelResourceActions = messaggi['createItemShop.label.resources.actions'];
	$scope.labelResourceRemove = messaggi['createItemShop.label.resources.button.remove'];
	$scope.buttonCancel = messaggi['createItemShop.button.cancel'];
	$scope.buttonOk = messaggi['createItemShop.button.Ok'];
	$scope.apply = messaggi['createItemShop.button.apply'];
	$scope.preview = messaggi['createItemShop.button.preview'];
	$scope.uploadError = messaggi['createItemShop.upload.error'];
	$scope.uploadSuccess = messaggi['createItemShop.upload.success'];

	$scope.labelColori = messaggi['createItemShop.label.colori']
	$scope.labelTaglie = messaggi['createItemShop.label.taglie']
	
	//chiamata ad init per avere l'elenco dei tag
    init();
		
    //calcolo del token csrf_token che viene passato da spring sul cookie XSRF-TOKEN usato nel Filtro custom CsrfHeaderFilter 
	var csrf_token = "";
	if ($cookies.get('XSRF-TOKEN')!=null){
		csrf_token = $cookies.get('XSRF-TOKEN');
	}
	
	//definizione della variabile che esegue gli upload per evitare problemi con spring security 
	//il token del fitlro CRSF viene passato nell'header e controllato dalla classe CrsfFilter di spring
    var uploader = $scope.uploader = new FileUploader({
        url: '/rest/upload',
        headers : {
        	'X-XSRF-TOKEN': csrf_token
        }
    });

    //formato dei campi data
    $('.dateFormat').datepicker({
        format: "dd/mm/yyyy",
        weekStart: 1,
        todayBtn: true,
        language: "it",
        daysOfWeekHighlighted: "0,6",
        autoclose:true
    });
    
    
	function init() {
		getTags();
		getColori();
		getTaglie();
		getPrezzo();
		getCategory();
    }

	function getCategory () {
		$scope.category= $http({method: 'GET',
		url:	'/rest/category/'
				}).then(function successCallback(response){
							//console.log("Category scaricata correttamenteRicerca avvenuta correttamente");
							$scope.category = response.data;
							
							//console.log($scope.categoryTree)
						},
						function errorCallback(response){Alert("Errore nella ricerca")});
	}
	    
	
	
	
	
	//tutti i tag della combo Dei Tag
    function getTags() {
        $scope.listaTags = Tag.query();
    };

    //tutti i colori
    function getColori() {
        $scope.listaColori = ItemProperty.query({nome:'Colore'});
    };

    //tutte le taglie
    function getTaglie() {
        $scope.listaTaglie = ItemProperty.query({nome:'Taglia'});
    };

    //tutte le taglie
    function getPrezzo() {
    	$scope.prezzi = ItemProperty.query({nome:'Prezzo'});
    };

	
    //transizione in caso di premuta del pulsante di cancel
    $scope.cancel = function () {
      $state.transitionTo("editList");
    }

    //salvataggio dell'itemShop
    $scope.createItem = function (remain,preview) {
    	if (!_validaAll()){
    		bootbox.alert({message: messaggiErrore['createItem.validateAll.alert']});
    		return;
    	}
    	
    	var errore = false;
		//se non ci sono upload ancora in sospeso
		//si aspetta che finisca poi si salva e si va verso la home di edit
        if (uploader.queue.length > 0 && uploader.progress < 100){
        	uploader.onCompleteAll = function() {
        		//quando finisce l'upload eseguirà il salvataggio
        		if(!errore){
	            	$scope.promessa.resolve(messaggi['createItemShop.upload.success']);
	           		_validaESalva(remain,preview);
        		}
    	    }	
        	uploader.onErrorItem = function(item, response, status, headers) {
        		errore = true;
        		$scope.promessa.reject(messaggi['createItemShop.upload.error']);
        	}
        	//esegue l'upload
        	$scope.promessa = $q.defer();
        	$scope.promessa.promise.then(
        			function(messaggio) {console.log(messaggio);},
        			function(messaggio) {bootbox.alert({message: messaggio});}
        	)
           	uploader.uploadAll();
        }else{
        	_validaESalva(remain,preview);
        }
    }

    
    function _validaESalva(remain,preview){
    	//array con i nomi dei file
		if ($scope.item.listaFile===undefined || $scope.item.listaFile==null) $scope.item.listaFile=new Array();
		for (i = 0; i < uploader.queue.length; i++){
			var fa = new FileAllegato(uploader.queue[i]._file.name, "")
			$scope.item.listaFile[i]=fa;
		}
		//TODO tipo Item dello slide show da verificare se codice va bene
        $scope.item.tipoItem = 3;
		var item = new ItemShop($scope.item);
    	item.prezzo=$scope.prezzi[0];
        
    	$scope.promessa.promise = item.$save(function(itemWeb) {
            if (itemWeb.erroreWeb == null){
            	if (!remain){
            		$state.transitionTo("editList");
            	
	            }else if (preview){
	            	$state.transitionTo('editItemShop',{id: itemWeb.id, name: itemWeb.nome},{reload:true});
            		var url ="/index.html#"+ $state.href("viewItemShop", {id: itemWeb.id, name: itemWeb.nome});
            		window.open(url,'_blank');
	            }else{
	            	$state.transitionTo('editItemShop',{id: itemWeb.id, name: itemWeb.nome},{reload:true});
	            }
            }else{
            	$scope.promessa.resolve('finito');
            	$scope.erroreNome=item.erroreWeb.erroreNome;
            	$scope.erroreTitolo=item.erroreWeb.erroreTitolo;
            	$scope.erroreTesto=item.erroreWeb.erroreTesto;
            }
        },function() {
        	$scope.promessa.resolve('finito');
        	bootbox.alert({message: messaggiErrore['createItem.error.save']});
    })
    }

    //appena si aggiunge un file questo viene immediatamenta uploadato sul server
    uploader.onAfterAddingFile = function(fileItem) {
        //fileItem.upload();
    };
    
    //all'aggiunta di un file questo viene immediatamente caricato sul server e viene aggiornata la lista dei file nuovi da inserire nel db
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
//        console.info('onSuccessItem', fileItem, response, status, headers);
//		if ($scope.item.listaFile===undefined || $scope.item.listaFile==null) $scope.item.listaFile=new Array();
//		$scope.item.listaFile[uploader.queue.length]=fileItem._file.name;
    };
    
    //metodo chiamato al momento del salvataggio per validare i campi obbligatori
    function _validaAll(){
    	return $scope.validaNome() && $scope.validaTitolo() && $scope.validaTesto() && $scope.validaAutore();
    }
    
    $scope.validaNome = function () {
    	if($scope.item === undefined || $scope.item.nome == null || $scope.item.nome.trim().length==0){
    		angular.element(document).find("#nome").addClass('inputErrore');
    		$scope.erroreNome = messaggiErrore['item.edit.name.required'];
    		return false;
    	}else{
    		angular.element(document).find("#nome").removeClass('inputErrore');
    		$scope.erroreNome = "";
    	}	
    	return true;
    };

    $scope.validaTitolo = function () {
    	if($scope.item === undefined || $scope.item.titolo == null || $scope.item.titolo.trim().length==0){
    		angular.element(document).find("#titolo").addClass('inputErrore');
    		$scope.erroreTitolo = messaggiErrore['item.edit.title.required'];
			return false;
		}else{
    		angular.element(document).find("#titolo").removeClass('inputErrore');
    		$scope.erroreTitolo = "";
		}
    	return true;
    };

    $scope.validaTesto = function () {
    	if($scope.item === undefined || $scope.item.testo == null || $scope.item.testo.trim().length==0 ){
			angular.element(document).find("#testo").addClass('inputErrore');
    		$scope.erroreTesto = messaggiErrore['item.edit.text.required'];
			return false;
		}else{
    		angular.element(document).find("#testo").removeClass('inputErrore');
    		$scope.erroreTesto = "";
		}
    	return true;
    };

    $scope.validaAutore = function () {
    	if($scope.item === undefined || $scope.item.autore == null || $scope.item.autore.trim().length==0 ){
			angular.element(document).find("#autore").addClass('inputErrore');
    		$scope.erroreAutore = messaggiErrore['item.edit.author.required'];
			return false;
		}else{
    		angular.element(document).find("#autore").removeClass('inputErrore');
    		$scope.erroreAutore = "";
		}
    	return true;
    };

	
}]);
