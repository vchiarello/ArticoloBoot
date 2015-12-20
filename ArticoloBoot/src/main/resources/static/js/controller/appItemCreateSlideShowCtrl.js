//controller per la creazione degli slide show
angular.module("blogApp").controller("ItemCreateSlideShowCtrl", function ($scope, Tag, Item, $state, $stateParams, FileUploader) {

	function FileAllegato(name, note){
		this.nomeAllegato=name;
		this.note=note;
	}
	
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
    
    
	function init() {
		$scope.note=[];
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
	           item.$save().then(function() {
               $state.transitionTo("homeEditListItem");
           });
	    }	
       	uploader.uploadAll();
       //altrimenti si salva e quindi si naviga verso la home di edit
       }else{
           var item = new Item($scope.item);
           item.$save().then(function() {
               $state.transitionTo("homeEditListItem");
           });
       }
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
