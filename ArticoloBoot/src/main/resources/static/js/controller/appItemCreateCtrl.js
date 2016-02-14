//controller per la creazione degli item
angular.module("blogApp").controller("ItemCreateCtrl", function ($scope, Tag, Item, $state, $stateParams, FileUploader, $q) {

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
    }

    //tutti i tag della combo Dei Tag
    function getTags() {
        $scope.listaTags = Tag.query();
    };

	//Appena si accede alla pagina si preleva l'item passato come parametro
    init();

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

	
    //transizione in caso di premuta del pulsante di cancel
    $scope.cancel = function () {
        $state.transitionTo("homeEditListItem");
    }

    //salvataggio dell'item
    $scope.createItem = function () {
    	if (!_validaAll()){
    		bootbox.alert({message: messaggiErrore['createItem.validateAll.alert']});
    		return;
    	}

    	var item = new Item($scope.item);

		//se non ci sono upload ancora in sospeso
		//si aspetta che finisca poi si salva e si va verso la home di edit
        if (uploader.queue.length > 0){
           	uploader.onCompleteAll = function() {
            	$scope.promessa = $q.defer()
           		_validaESalva();
    	    }	
           	uploader.uploadAll();
        }else{
        	$scope.promessa = $q.defer()
        	_validaESalva();
        }
    }

    
    function _validaESalva(){
		if ($scope.item.listaFile===undefined || $scope.item.listaFile==null) $scope.item.listaFile=new Array();
		for (i = 0; i < uploader.queue.length; i++){
			var fa = new FileAllegato(uploader.queue[i]._file.name, "")
			$scope.item.listaFile[i]=fa;
		}
		//TODO tipo Item dello slide show da verificare se codice va bene
        $scope.item.tipoItem = 1;
		var item = new Item($scope.item);
        
        item.$save(function(itemWeb) {
            if (itemWeb.erroreWeb == null){
            	$state.transitionTo("homeEditListItem");
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
    
	
});
