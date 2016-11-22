//controller per la creazione degli item
angular.module("blogApp").controller("EditItemShopCtrl", ['$scope', 'Tag', 'ItemShop', 'ItemProperty', '$state', '$stateParams', 'FileUploader', '$q', '$cookies', function ($scope, Tag, ItemShop, ItemProperty, $state, $stateParams, FileUploader, $q, $cookies
		) {
	
	//Label usate nella pagina
	$scope.labelNome=messaggi['editItemShop.label.name'];
	$scope.placeHolderNome=messaggi['editItemShop.placeholder.name'];
	$scope.labelTitle=messaggi['editItemShop.label.title'];
	$scope.placeHolderTitle=messaggi['editItemShop.placeholder.title'];
	$scope.labelText = messaggi['editItemShop.label.text'];
	$scope.placeHolderText = messaggi['editItemShop.placeholder.text'];
	$scope.labelQuantita = messaggi['editItemShop.label.quantity'];
	$scope.placeHolderQuantita = messaggi['editItemShop.placeholder.quantity'];
	$scope.labelPrezzo = messaggi['editItemShop.label.prezzo'];
	$scope.placeHolderPrezzo = messaggi['editItemShop.placeholder.prezzo'];
	$scope.labelPublishDate = messaggi['editItemShop.label.publishDate'];
	$scope.placeHolderPublishDate = messaggi['editItemShop.placeholder.publishDate'];
	$scope.labelExpirationDate = messaggi['editItemShop.label.expirationDate'];
	$scope.placeHolderExpirationDate = messaggi['editItemShop.placeholder.expirationDate'];
	$scope.labelHiddenDate = messaggi['editItemShop.label.hiddenDate'];
	$scope.placeHolderHiddenDate = messaggi['editItemShop.placeholder.hiddenDate'];
	$scope.labelAuthor=messaggi['editItemShop.label.author'];
	$scope.placeholderAuthor=messaggi['editItemShop.placeholder.author'];
	$scope.labelTag = messaggi['editItemShop.label.tag'];
	$scope.labelNewTag = messaggi['editItemShop.label.newTag'];
	$scope.placeholderNewTag = messaggi['editItemShop.placeholder.newTags'];
	$scope.labelResource = messaggi['editItemShop.label.resources'];
	$scope.labelResourceName = messaggi['editItemShop.label.resources.name'];
	$scope.labelResourceSize = messaggi['editItemShop.label.resources.size'];
	$scope.labelResourceType = messaggi['editItemShop.label.resources.type'];
	$scope.labelResourceProgress = messaggi['editItemShop.label.resources.progress'];
	$scope.labelResourceState = messaggi['editItemShop.label.resources.state'];
	$scope.labelResourceActions = messaggi['editItemShop.label.resources.actions'];
	$scope.labelResourceRemove = messaggi['editItemShop.label.resources.button.remove'];
	$scope.labelResourceRestore = messaggi['editItemShop.label.resources.button.restore'];
	$scope.buttonCancel = messaggi['editItemShop.button.cancel'];
	$scope.buttonSave = messaggi['editItemShop.button.save'];
	$scope.labelColori = messaggi['editItemShop.label.colori']
	$scope.labelTaglie = messaggi['editItemShop.label.taglie']
	
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
        $scope.item = ItemShop.get({id:$stateParams.id, name:$stateParams.name})
        $scope.promessa = new Object();
        $scope.promessa.promise = $scope.item.$promise;
		getTags();
		getColori();
		getTaglie();
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

	
    //transizione in caso di premuta del pulsante di cancel
    $scope.cancel = function () {
      $state.transitionTo("editList");
    }

    //salvataggio dell'itemShop
    $scope.createItem = function () {
    	if (!_validaAll()){
    		bootbox.alert({message: messaggiErrore['createItem.validateAll.alert']});
    		return;
    	}

    	var item = new ItemShop($scope.item);

		//se non ci sono upload ancora in sospeso
		//si aspetta che finisca poi si salva e si va verso la home di edit
        if (uploader.queue.length > 0 && uploader.progress < 100){
        	uploader.onCompleteAll = function() {
        		//quando finisce l'upload eseguirà il salvataggio
            	$scope.promessa = $q.defer()
           		_validaESalva();
    	    }	
        	//esegue l'upload
           	uploader.uploadAll();
        }else{
        	$scope.promessa = $q.defer()
        	_validaESalva();
        }
    }

    
    function _validaESalva(){
    	//array con i nomi dei file
		if ($scope.item.listaFile===undefined || $scope.item.listaFile==null) $scope.item.listaFile=new Array();
		for (i = 0; i < uploader.queue.length; i++){
			var fa = new FileAllegato(uploader.queue[i]._file.name, "")
			$scope.item.listaFile[i]=fa;
		}
		//TODO tipo Item dello slide show da verificare se codice va bene
        $scope.item.tipoItem = 3;
		var item = new ItemShop($scope.item);
        
        item.$update(function(itemWeb) {
            if (itemWeb.erroreWeb == null){
            	$state.transitionTo("editList");
            	$scope.promessa.resolve('finito');
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
    //per i file già caricati c'è sia la funzionalità di cancellazione che quella di ripristina 
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


	
}]);
