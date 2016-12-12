function imageViewerCompCtrl(){
	
	this.$postLink=function(){
		console.log("$postLink:" + $(".imgSlide").length);
	}
	
	this.$doCheck=function(){
		console.log("$doCheck:" + $(".imgSlide").length);
		if ($(".imgSlide").length>0){
			$(".imgSlide").zoom();
		}	
	}
	
	this.$postLink=function(){
		console.log("$postLink:" + $(".imgSlide").length);
	}
	current=0;
	  
	this.so_xfade_prev=function(){
		if (current==0)current = this.immagini.length-1;
		else current--;
		attivaImmagine();
	}

	this.so_xfade_succ=function(){
		if (current==this.immagini.length-1)current = 0;
		else current++;
	  attivaImmagine();
	}

	this.so_xfade_i = function(i){
		if (current==i)return;
		current = i;
		attivaImmagine();
	}

	this.visibilitaCorrente = function (index){
		if (index==current) return "display:block"; 
		else return "display:none"; 
	}  
	  

	this.calcolaImmagine = function(i){
		if (current==i)return "PallinoPieno.png";
		return "PallinoVuoto.png";
	}

	this.displayImmagine = function(){
		attivaImmagine();
	}
	  
	function attivaImmagine(){
		//valutare se mettere direttamente il display a none invece del fadeout
		$("#slideshow span").fadeOut("600");
		var a = $("#slideshow span");
		$(a[current]).fadeIn(600);
		
		$("#slideshowPopup img").css("display","none");
		var b = $("#slideshowPopup img");
		$(b[current]).fadeIn(600);
			
	}

}

angular.module('blogApp').component('imageViewer', {
    templateUrl: 'html/imageViewer.html',
    controller: imageViewerCompCtrl,
    bindings:{
    	immagini:'='
    }
});

