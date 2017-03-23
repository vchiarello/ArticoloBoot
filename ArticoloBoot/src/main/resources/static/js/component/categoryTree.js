angular.module('blogApp').component('categoryTree', {
    templateUrl: 'html/component/categoryTree.html',
    controller: treeCategeoryCompCtrl,
    bindings:{
    	lista:'='
    }
});

function treeCategeoryCompCtrl(){
	
	this.$postLink=function(){
		console.log("treeCategeoryCompCtrl -> $postLink:");
	}
	
	
	this.$onInit=function(){
		console.log("treeCategeoryCompCtrl -> $onInit:");
	}
	
	this.$doCheck=function(){
		console.log("treeCategeoryCompCtrl -> $doCheck:" );
	}
	
	this.$postLink=function(){
		console.log("treeCategeoryCompCtrl -> $postLink:");
		console.log("lunghezza lista: " + this.lista.length)
	}
	
	this.mostraNascondi = function(id){
		alert(id)
		$("#"+id).css("display","none");
	}
}

