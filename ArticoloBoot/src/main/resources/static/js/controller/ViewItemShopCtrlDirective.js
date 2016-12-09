angular.module("blogApp").controller('ViewItemShopCtrl', ['$scope','ItemShop', '$stateParams','$http', function($scope, ItemShop, $stateParams,$http) {
	
	zoomOk=false;
	
	$http.get("/rest/itemShop/infoAllegati/"+$stateParams.id+"/"+$stateParams.name).
		then(function(data){
			//$scope.info = data;
			$scope.immagini = new Array();
			for (i = 0; i < data.data.length;i++){
				$scope.immagini[i] = new Object();
				$scope.immagini[i].indirizzo = "/rest/download?idAllegato="+data.data[i].id+"&nomeAllegato="+data.data[i].nomeAllegato;
				$scope.immagini[i].titolo = data.data[i].nomeAllegato;
			}
		},
		function(data){
			$scope.info = data
		})
	
	$scope.item = ItemShop.get({id:$stateParams.id,name:$stateParams.name});



  
  current=0;
  
  $scope.so_xfade_prev=function(){
	  if (current==0)current = $scope.immagini.length-1;
	  else
		  current--;
	  attivaImmagine();
  }
  $scope.so_xfade_succ=function(){
	  if (current==$scope.immagini.length-1)current = 0;
	  else
		  current++;
	  attivaImmagine();
  }

  $scope.so_xfade_i = function(i){
	  if (current==i)return;
		current = i; //ciao Vito
		attivaImmagine();
	}

  $scope.zoom = function(){
	  if (!zoomOk)
//		  $('span[id^=ex]').zoom();
	  zoomOk = true;
	}

  $scope.visibilitaCorrente = function (index){
	  if (index==current)
		  return "{'display':'none'}"; 
	  else
		  return "{'display':'block'}"; 
	  
	}  
  

  $scope.calcolaImmagine = function(i){
		if (current==i)return "PallinoPieno.png";
		return "PallinoVuoto.png";
	}

  $scope.displayImmagine = function(){
	  attivaImmagine();
  }
  
  function attivaImmagine(){
	  //valutare se mettere direttamente il display a none invece del fadeout
		$("#slideshow>span").fadeOut("600");
		var a = $("#slideshow>span");
		$(a[current]).fadeIn(600);
		
		$("#slideshowPopup>img").css("display","none");
		var b = $("#slideshowPopup>img");
		$(b[current]).fadeIn(600);
		
	}

}])
.directive('slideDirect', function() {
  return {
    templateUrl: '../html/slideDirect.html',
  };
})
.directive('zoom',  [function () {
	  //questa direttiva si è resa necessaria per fare in modo che funzioni lo zoom sull'immagine
	  //succedeva che mettendo un mouseover sull'immagine si perdeva il primo atterraggio del mouse, dal secondo era ok
	  return {
		  link: function(scope, element, attrs, ngModelCtrl) {
			  //siccome in fase di rendering inizialmente il nome dell'attributo ha la {{index}}
			  //si fa in modo che il metodo .zoom() si chiami alla prima variazione dell'oggetto id
			  //quando cioè ha finito angular di valorizzare il tutto.
			  //Ricordo che watch chiama la senconda funzione quando cambia il valore della prima
			  var watch = scope.$watch(function() {return attrs.id;},function(){element.zoom()});
		  }
	  };
	}])
.directive('visualizza',  [function () {
	  return {
		  link: function(scope, element, attrs, ngModelCtrl) {
			  var watch = scope.$watch(function() {return attrs.id;},function(){
				  console.log('prima ' + attrs.style);
				  if (attrs.id=='sliPop0') attrs.style='display:block;';
				  else attrs.style='display:none;'
				  console.log('dopo ' + attrs.style);
				  
			  });
		  }
	  };
	}])
;
