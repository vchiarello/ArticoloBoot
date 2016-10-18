

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
        }).state('createItem', {
            url:'/createItem',
            templateUrl: 'html/newItem.html',
            controller: 'NewItemCtrl'
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
	    }).state('editItem', {
            url:'/editItem/:id/:name',
            templateUrl: 'html/editItem.html',
            controller: 'EditItemCtrl'
	    }).state('viewSlideShowItem', {
            url:'/viewSlideShowItem/:id/:name',
            templateUrl: URLS.partialsViewSlideShowItem,
            controller: 'ViewSlideShowItemCtrl'
        }).state('createSlideShowItem', {
            url:'/createSlideShowItem',
            templateUrl: URLS.partialsCreateSlideShowItem,
            controller: 'ItemCreateSlideShowCtrl'
        }).state('editSlideShowItem', {
            url:'/editSlideShowItem/:id/:name',
            templateUrl: URLS.partialsEditSlideShowItem,
            controller: 'ItemEditSlideShowCtrl'
        }).state('endpoints', {
            url: '/endpoints',
            templateUrl: URLS.partialsMappings,
            controller: 'MappingsCtrl'
        });
});
