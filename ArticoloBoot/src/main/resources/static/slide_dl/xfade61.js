angular.module('slide', []).controller('slideController',	function slideController($scope){		$scope.immagini = [			{indirizzo:"http://localhost:8080/slide_dl/Blu.png", titolo:"Blu.png"},            {indirizzo:"http://localhost:8080/slide_dl/Giallo.png", titolo:"Giallo.png"},            {indirizzo:"http://localhost:8080/slide_dl/Rosso.png", titolo:"Rosso.png"},            {indirizzo:"http://localhost:8080/slide_dl/Verde.png", titolo:"Verde.png"}            ]	});function slideCompController($scope){	  current=0;	  	  this.so_xfade_prev=function(){		  if (current==0)current = this.immagini.length-1;		  else			  current--;		  attivaImmagine();	  }	  this.so_xfade_succ=function(){		  if (current==this.immagini.length-1)current = 0;		  else			  current++;		  attivaImmagine();	  }	  this.so_xfade_i = function(i){		  if (current==i)return;			current = i;			attivaImmagine();		}	  this.zoom = function(){		  if (!zoomOk)//			  $('span[id^=ex]').zoom();		  zoomOk = true;		}	  this.visibilitaCorrente = function (index){		  if (index==current)			  return "{'display':'none'}"; 		  else			  return "{'display':'block'}"; 		  		}  	  	  this.calcolaImmagine = function(i){			if (current==i)return "PallinoPieno.png";			return "PallinoVuoto.png";		}	  this.displayImmagine = function(){		  attivaImmagine();	  }	  	  function attivaImmagine(){		  //valutare se mettere direttamente il display a none invece del fadeout			$("#slideshow>span").fadeOut("600");			var a = $("#slideshow>span");			$(a[current]).fadeIn(600);						$("#slideshowPopup>img").css("display","none");			var b = $("#slideshowPopup>img");			$(b[current]).fadeIn(600);					}}angular.module('slide').component('slideDirect', {    templateUrl: 'slideDirect61.html',    controller: slideCompController,    bindings:{    	immagini:'='    }  });function slideZoomController($scope){	this.zooma=function(id){alert(id)}}angular.module('slide').component('slideZoom', {    templateUrl: 'slideZoom61.html',    controller: slideZoomController,    bindings:{    	indirizzo:'=',    	titolo:'=',    	ident:'=',       	primo:'='    }  });