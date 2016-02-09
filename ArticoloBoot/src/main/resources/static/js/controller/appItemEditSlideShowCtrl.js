//controller per la creazione degli slide show
angular.module("blogApp").controller("ItemEditSlideShowCtrl", function ($scope, Tag, Item, $state, $stateParams, FileUploader) {

	
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
		$scope.note=[];
		getTags();
        $scope.item = Item.get({id:$stateParams.id, name:$stateParams.name})

    }

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
    $scope.createItem = function () {

        
        //se ci sono upload ancora in sospeso
        //si aspetta che finisca poi si salva e si va verso la home di edit
       if (uploader.queue.length > 0){
       	uploader.onCompleteAll = function() {
    		
    		if ($scope.item.listaFile===undefined || $scope.item.listaFile==null) $scope.item.listaFile=new Array();
    		for (i = 0; i < uploader.queue.length; i++){
    			var fa = new FileAllegato(uploader.queue[i]._file.name, $scope.note[i])
    			$scope.item.listaFile[i]=fa
    		}
           var item = new Item($scope.item);
	           item.$update().then(function() {
               $state.transitionTo("homeEditListItem");
           });
	    }	
       	uploader.uploadAll();
       //altrimenti si salva e quindi si naviga verso la home di edit
       }else{
           var item = new Item($scope.item);
           item.$update().then(function() {
               $state.transitionTo("homeEditListItem");
           });
       }
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
    
    //all'aggiunta di un file questo viene immediatamente caricato sul server e viene aggiornata la lista dei file nuovi da inserire nel db
//    uploader.onSuccessItem = function(fileItem, response, status, headers) {
//        console.info('onSuccessItem', fileItem, response, status, headers);
//		if ($scope.item.listaFile===undefined || $scope.item.listaFile==null) $scope.item.listaFile=new Array();
//		$scope.item.listaFile[uploader.queue.length]=fileItem._file.name;
//    };
    
	
});
