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
	    		message: messaggiErrore['editList.hideItem.function.Confirm'](data.titolo), 
	    		callback: function(result){
					if (result){
		    			d = new Date()
						var ds = ('0'+d.getDate()).substring(('0'+d.getDate()).length-2,('0'+d.getDate()).length)+'/'+
						         ('0'+(d.getMonth()+1)).substring(('0'+(d.getMonth()+1)).length-2,('0'+(d.getMonth()+1)).length)+'/'+
						         d.getFullYear();
						data.dataHidden=ds;
		    			data.$update().then(function() {
						    	        	bootbox.alert({message: messaggiErrore['editList.hideItem.result']})
		    			})
					}	
	    		} 
	    	})

    }
    

	function showItem(data){
	    	bootbox.confirm({
	    		message: messaggiErrore['editList.showItem.function.Confirm'](data.titolo), 
	    		callback: function(result){
					if (result){
		    			
						data.dataHidden=null;
		    			data.$update().then(function() {
						    	        	bootbox.alert({message: messaggiErrore['editList.showItem.result']})
		    			})
					}	
	    		} 
	    	})

    }
	
	
	function deleteItem (item) {
    	var it = new Item(item);

    	bootbox.confirm({
    		message: messaggiErrore['editList.deleteItem.function.Confirm'](item.titolo), 
    		callback: function(result){
    			if (result){
    	        		it.$delete({id:item.id, name:item.name}, 
    	        				function(){
				    	        	bootbox.alert({message: messaggiErrore['editList.deleteItem.result']})
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

