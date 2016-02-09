//controller per la creazione degli slide show
angular.module("blogApp").controller("ItemCreateSlideShowCtrl", function ($scope, Tag, Item, $state, $stateParams, FileUploader, $q) {

    $scope.messaggio = messaggiErrore['list.spinner.message'];
	
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
    }

    //tutti i tag della combo Dei Tag
    function getTags() {
        $scope.listaTags = Tag.query();
    };

	//Appena si accede alla pagina si eseguono le inizializzazioni
    init();

	
    //transizione in caso di premuta del pulsante di cancel
    $scope.cancel = function () {
        $state.transitionTo("homeEditListItem");
    }

    //salvataggio dell'item
    $scope.createItem = function () {
        
    	if (uploader.queue.length > 0){
    		uploader.onCompleteAll = function() {
    		
	    		if ($scope.item.listaFile===undefined || $scope.item.listaFile==null) $scope.item.listaFile=new Array();
	    		
	    		for (i = 0; i < uploader.queue.length; i++){
	    			var fa = new FileAllegato(uploader.queue[i]._file.name, $scope.note[i])
	    			$scope.item.listaFile[i]=fa;
	    		}
	    		//TODO tipo Item dello slide show da verificare se codice va bene
	            $scope.item.tipoItem = 2;
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
				});
		    }	

	       	$scope.promessa = $q.defer()
	       	uploader.uploadAll();
       	
       //altrimenti si salva e quindi si naviga verso la home di edit
       }else{
			$scope.promessa = $q.defer()
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
			});
       }
    }


	uploader.onAfterAddingFile = function(fileItem) {
//		alert($scope.item.nome + ", " +$scope.note + "" + angular.isArray($scope.note));
	};


    function _validaAll(){
    	
    	return $scope.validaNome() && $scope.validaTitolo() && $scope.validaTesto();
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
    	if($scope.item === undefined || $scope.item.testo == null || $scope.item.testo.trim().length==0 ){
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
