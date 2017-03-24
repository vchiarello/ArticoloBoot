angular.module('blogApp').component('categoryTree', {
    templateUrl: 'html/component/categoryTree.html',
    controller: treeCategeoryCompCtrl,
    bindings:{
    	lista:'='
    }
});

function treeCategeoryCompCtrl(){
	
	this.$postLink=function(){
	}
	
	
	this.$onInit=function(){
	}
	
	this.$doCheck=function(){
	}
	
	this.$postLink=function(){
	}
	
	this.mostraNascondi = function(id){
		if ($("#list_"+id).css("display")=="none"){
			$("#img_categ_"+id).attr("src","images/FrecciaElencoAperta.png")
			$("#list_"+id).fadeIn(1000);
		}else{	
			$("#img_categ_"+id).attr("src","images/FrecciaElencoChiusa.png")
			$("#list_"+id).fadeOut(1000);
		}
	}
}

