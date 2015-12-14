angular.module("blogApp").factory("ItemOperation", function (Item) {

	function isNascosto(item){
		if (item.dataHidden ==null)return false;
		anno = item.dataHidden.substring(6,10);
		mese = new Number(item.dataHidden.substring(3,5))-1;
		giorno = item.dataHidden.substring(0,2)
		d = new Date(anno,mese,giorno,0,0,0)
		if (d <= new Date())return true;
		return false;
	}
	
	function nascondiItem(data){
	    	bootbox.confirm({
	    		message: "Nascondere l'item \"" + data.titolo +"\"?", 
	    		callback: function(result){
					if (result){
		    			d = new Date()
						var ds = ('0'+d.getDate()).substring(('0'+d.getDate()).length-2,('0'+d.getDate()).length)+'/'+
						         ('0'+(d.getMonth()+1)).substring(('0'+(d.getMonth()+1)).length-2,('0'+(d.getMonth()+1)).length)+'/'+
						         d.getFullYear();
						data.dataHidden=ds;
		    			data.$update().then(function() {
						    	        	bootbox.alert({message: "Item nascoto!"})
		    			})
					}	
	    		} 
	    	})

    }
    

	function showItem(data){
	    	bootbox.confirm({
	    		message: "Mostrare l'item \"" + data.titolo +"\"?", 
	    		callback: function(result){
					if (result){
		    			
						data.dataHidden=null;
		    			data.$update().then(function() {
						    	        	bootbox.alert({message: "Item è ora visibile!"})
		    			})
					}	
	    		} 
	    	})

    }
	
	
	function deleteItem (item) {
    	var it = new Item(item);

    	bootbox.confirm({
    		message: "Cancellare l'oggetto \"" + it.titolo +"\"?", 
    		callback: function(result){
    			if (result){
    	        		it.$delete({id:item.id}, 
    	        				function(){
				    	        	bootbox.alert({message: "Item cancellato!"})
			    	        	})
    			}
    		} 
    	})

    	
    };
    
	return {
		nascondiItem:nascondiItem,
		showItem:showItem,
		isNascosto:isNascosto,
		deleteItem:deleteItem
	};
});

