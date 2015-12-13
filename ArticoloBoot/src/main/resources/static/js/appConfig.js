angular.module("blogApp").config(function ($stateProvider, $urlRouterProvider) {
	
	
	
    $urlRouterProvider.otherwise("homeItem");

    $stateProvider
	    .state('login', {
	        url:'/login',
	        templateUrl: URLS.login,
	        controller: 'LoginCtrl'
	    }).state('homeItem', {
	        url:'/homeItem',
	        templateUrl: URLS.partialsListItem,
	        controller: 'ItemCtrl'
	    }).state('homeEditListItem', {
	        url:'/homeEditListItem',
	        templateUrl: URLS.partialsEditList,
	        controller: 'EditListCtrl'
	    }).state('edit', {
            url:'/edit/:itemId',
            templateUrl: URLS.partialsEditItem,
            controller: 'ItemEditCtrl'
	    }).state('view', {
            url:'/view/:itemId',
            templateUrl: URLS.partialsViewItem,
            controller: 'ViewItemCtrl'
        }).state('createItem', {
            url:'/createItem',
            templateUrl: URLS.partialsCreateItem,
            controller: 'ItemCreateCtrl'
        }).state('viewItem', {
            url:'/viewItem',
            templateUrl: URLS.partialsCreateItem,
            controller: 'ItemCreateCtrl'
        }).state('createItemw1', {
            url:'/createItemw1',
            templateUrl: URLS.partialsCreateItemw1,
            controller: 'ItemCreateCtrl1'
        }).state('createItemw2', {
            url:'/createItemw2',
            templateUrl: URLS.partialsCreateItemw2,
            controller: 'ItemCreateCtrl2'
        }).state('createItemw3', {
            url:'/createItemw3',
            templateUrl: URLS.partialsCreateItemw3,
            controller: 'ItemCreateCtrl'
        }).state('create', {
            url:'/create',
            templateUrl: URLS.partialsCreate,
            controller: 'HotelCtrl'
        }).state('endpoints', {
            url: '/endpoints',
            templateUrl: URLS.partialsMappings,
            controller: 'MappingsCtrl'
        });
});
