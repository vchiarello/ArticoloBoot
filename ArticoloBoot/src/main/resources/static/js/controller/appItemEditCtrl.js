//controller usato nell'edit degli item
angular.module("blogApp").controller("ItemEditCtrl", function ( $http, $scope,  Tag, Item, $state, $stateParams, FileUploader) {
	
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


    $('.dataFormat').datepicker({
        format: "dd/mm/yyyy",
        weekStart: 1,
        todayBtn: true,
        language: "it",
        daysOfWeekHighlighted: "0,6",
        autoclose:true
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


    $scope.validaNome = function () {
    	if(_campoErrore($scope.item.nome, "nome")){
    		$scope.erroreNome = "Nome campo obbligatorio.";
    		return false;
    	}else
    		$scope.erroreNome = "";
    	return true;
    };

    $scope.validaTitolo = function () {
    	if(_campoErrore($scope.item.titolo, "titolo")){
    		$scope.erroreTitolo = "Titolo campo obbligatorio.";
			return false;
		}else
    		$scope.erroreTitolo = "";
    };

    $scope.validaTesto = function () {
    	if(_campoErrore($scope.item.testo, "testo")){
    		$scope.erroreTesto = "Testo campo obbligatorio.";
			return false;
		}else
    		$scope.erroreTesto = "";
    };

    function _campoErrore(elemento, nomeCampo){
    	if(elemento == null || elemento.trim().length==0){
    		angular.element(document).find("#"+nomeCampo).addClass('inputErrore');
    		return true;
    	}else{
    		angular.element(document).find("#"+nomeCampo).removeClass('inputErrore');
    		return false;
    	}
    }
    
    //transizione in caso di premuta del pulsante di cancel
    $scope.cancel = function () {
        $state.transitionTo("homeEditListItem");
    }
    
    function _validaAll(){
    	
    	return $scope.validaTitolo() && $scope.validaTitolo() && $scope.validaTesto();
    }

    //salvataggio dell'item
    $scope.updateItem = function() {
    	var item = new Item($scope.item);
    	
    	var req = {
        		method: 'POST',
        		url: '/rest/validaItemWeb',
        		headers : {'X-CSRF-TOKEN': csrf_token},
        		data:{'itemWeb' : item}
        	}
        	
//    	var req = {
//        		method: 'GET',
//        		url: '/rest/validaItemWeb/Ciao validate',
//        		headers : {'X-CSRF-TOKEN': csrf_token},
//        	}
        	
    	$http(req).then(function(){alert("Ok")},function(){alert("Ko")})
    	
    	if (!_validaAll()){
    		
    		alert("Impossibile salvare correggere gli errori!");
    		return;
    	}
    	
    	
       
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

