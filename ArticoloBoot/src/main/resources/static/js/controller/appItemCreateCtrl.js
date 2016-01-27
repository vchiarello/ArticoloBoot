//controller per la creazione degli item
angular.module("blogApp").controller("ItemCreateCtrl", function ($scope, Tag, Item, $state, $stateParams, FileUploader) {

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
