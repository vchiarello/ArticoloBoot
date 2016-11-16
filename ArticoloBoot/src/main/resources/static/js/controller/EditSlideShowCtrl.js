//controller per la creazione degli slide show
angular.module("blogApp").controller("EditSlideShowCtrl", function ($scope, Tag, Item, $state, $stateParams, FileUploader, $q, $cookies) {

	
    //calcolo del toker csrf_token
	var csrf_token = "";
	if ($cookies.get('XSRF-TOKEN')!=null){
		csrf_token = $cookies.get('XSRF-TOKEN');
	}
	
	//definizione della variabile che esegue gli upload per evitare problemi con spring security si aggiunge nell'header della richista il csrf token
    var uploader = $scope.uploader = new FileUploader({
        url: '/rest/upload',
        headers : {
        	'X-XSRF-TOKEN': csrf_token
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
		$scope.note=[];
		getTags();
        $scope.item = Item.get({id:$stateParams.id, name:$stateParams.name})
        $scope.promessa = new Object();
        $scope.promessa.promise = $scope.item.$promise;
    }
    $scope.messaggio = messaggiErrore['list.spinner.message'];

    //tutti i tag della combo Dei Tag
    function getTags() {
        $scope.listaTags = Tag.query();
    };

	//Appena si accede alla pagina si preleva l'item passato come parametro e si eseguono le inizializzazioni
    init();

	
    //transizione in caso di premuta del pulsante di cancel
    $scope.cancel = function () {
        $state.transitionTo("homeEditListItem");
    }


    //salvataggio dell'item
    $scope.updateItem = function() {
    	if (!_validaAll()){
    		bootbox.alert({message: messaggiErrore['editItem.validateAll.alert']});
    		return;
    	}
    	var item = new Item($scope.item);
		//se non ci sono upload ancora in sospeso
		//si aspetta che finisca poi si salva e si va verso la home di edit
        if (uploader.queue.length > 0 && uploader.progress < 100){
        	$scope.promessa = $q.defer()
        	uploader.onCompleteAll = function() {
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
			var fa = new FileAllegato(uploader.queue[i]._file.name, $scope.note[i])
			$scope.item.listaFile[i]=fa;
		}
		//TODO tipo Item dello slide show da verificare se codice va bene
        $scope.item.tipoItem = 2;
		//var item = new Item($scope.item);
        
        $scope.item.$update(function(itemWeb) {
            if (itemWeb.erroreWeb == null){
            	$state.transitionTo("homeEditListItem");
            	$scope.promessa.resolve('finito');
            }else{
            	$scope.promessa.resolve('finito');
            	$scope.erroreNome=item.erroreWeb.erroreNome;
            	$scope.erroreTitolo=item.erroreWeb.erroreTitolo;
            	$scope.erroreTesto=item.erroreWeb.erroreTesto;
            }},function() {
            	$scope.promessa.resolve('finito');
            	bootbox.alert({message: messaggiErrore['editItem.error.save']});
        });
    }
    
    


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

    $scope.rimuoviFile = function (item){
        uploader.removeFromQueue(item);
    }
    
	uploader.onAfterAddingFile = function(fileItem) {
//		alert($scope.item.nome + ", " +$scope.note + "" + angular.isArray($scope.note));
	};
    
    function _validaAll(){
    	
    	return $scope.validaNome() && $scope.validaTitolo() && $scope.validaTesto() && $scope.validaAutore();
    }
    
    $scope.validaNome = function () {
    	if($scope.item === undefined || $scope.item.nome == null || $scope.item.nome.trim().length==0){
    		$scope.erroreNome = messaggiErrore['item.edit.name.required'];
    		angular.element(document).find("#nome").addClass('inputErrore');
    		return false;
    	}else{
    		angular.element(document).find("#nome").removeClass('inputErrore');
    		$scope.erroreNome = "";    		
    	}
    	return true;
    };

    $scope.validaTitolo = function () {
    	if($scope.item === undefined || $scope.item.titolo == null || $scope.item.titolo.trim().length==0 ){
    		$scope.erroreTitolo = messaggiErrore['item.edit.title.required'];
    		angular.element(document).find("#titolo").addClass('inputErrore');
			return false;
		}else{
    		angular.element(document).find("#titolo").removeClass('inputErrore');
    		$scope.erroreTitolo = "";
		}
    	return true;
    };

    $scope.validaTesto = function () {
    	if($scope.item === undefined || $scope.item.testo == null || $scope.item.testo.trim().length==0){
    		$scope.erroreTesto = messaggiErrore['item.edit.text.required'];
    		angular.element(document).find("#testo").addClass('inputErrore');
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

    
	
});
