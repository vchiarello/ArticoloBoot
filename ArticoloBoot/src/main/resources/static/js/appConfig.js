

angular.module("blogApp").config(function ($stateProvider, $urlRouterProvider) {
	
	
	
    $urlRouterProvider.otherwise("/");

    $stateProvider
	    .state('login', {
	        url:'/login',
	        templateUrl: URLS.login,
	        controller: 'LoginCtrl'
	    }).state('/', {
	        url:'/',
	        templateUrl: 'html/list.html',
	        controller: 'ListCtrl'
	    }).state('list', {
	        url:'/list',
	        templateUrl: 'html/list.html',
	        controller: 'ListCtrl'
	    }).state('editList', {
	        url:'/editList',
	        templateUrl: 'html/editList.html',
	        controller: 'EditListCtrl'
	    }).state('viewItem', {
            url:'/viewItem/:id/:name',
            templateUrl: 'html/viewItem.html',
            controller: 'ViewItemCtrl'
        }).state('createItem', {
            url:'/createItem',
            templateUrl: 'html/newItem.html',
            controller: 'NewItemCtrl'
	    }).state('editItem', {
            url:'/editItem/:id/:name',
            templateUrl: 'html/editItem.html',
            controller: 'EditItemCtrl'
	    }).state('viewItemShop', {
            url:'/viewItemShop/:id/:name',
            templateUrl: 'html/viewItemShop.html',
            controller: 'ViewItemShopCtrl'
	    }).state('createItemShop', {
            url:'/createItemShop',
            templateUrl: 'html/newItemShop.html',
            controller: 'NewItemShopCtrl'
	    }).state('editItemShop', {
            url:'/editItemShop/:id/:name',
            templateUrl: 'html/editItemShop.html',
            controller: 'EditItemShopCtrl'
	    }).state('viewSlideShow', {
            url:'/viewSlideShow/:id/:name',
            templateUrl: 'html/viewSlideShow.html',
            controller: 'ViewSlideShowCtrl'
        }).state('createSlideShow', {
            url:'/createSlideShow',
            templateUrl: 'html/newSlideShow.html',
            controller: 'NewSlideShowCtrl'
        }).state('editSlideShow', {
            url:'/editSlideShowItem/:id/:name',
            templateUrl: 'html/editSlideShow.html',
            controller: 'EditSlideShowCtrl'
        }).state('viewCart', {
            url:'/viewCart',
            templateUrl: 'html/viewCart.html',
            controller: 'ViewCartCtrl'
        }).state('endpoints', {
            url: '/endpoints',
            templateUrl: URLS.partialsMappings,
            controller: 'MappingsCtrl'
        });
});
