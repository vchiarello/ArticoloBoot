angular.module('blogApp').component('categoryTree', {
    templateUrl: 'html/component/categoryTree.html',
    controller: treeCategeoryCompCtrl,
    bindings:{
    	lista:'=',
    	onSelect:'&'
    }
});

function treeCategeoryCompCtrl(){
	
	this.$postLink=function(){
	}
    
	this.select=function(nodo){
		this.onSelect({nodo: nodo});
	}

	this.selectTreeItem = function(nodo){
		this.onSelect({nodo: nodo});
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

