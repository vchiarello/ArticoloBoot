

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
	    }).state('viewItem', {
            url:'/viewItem/:itemId',
            templateUrl: URLS.partialsViewItem,
            controller: 'ViewItemCtrl'
        }).state('createItem', {
            url:'/createItem',
            templateUrl: URLS.partialsCreateItem,
            controller: 'ItemCreateCtrl'
	    }).state('editItem', {
            url:'/editItem/:itemId',
            templateUrl: URLS.partialsEditItem,
            controller: 'ItemEditCtrl'
	    }).state('viewSlideShowItem', {
            url:'/viewSlideShowItem/:itemId',
            templateUrl: URLS.partialsViewSlideShowItem,
            controller: 'ViewSlideShowItemCtrl'
        }).state('createSlideShowItem', {
            url:'/createSlideShowItem',
            templateUrl: URLS.partialsCreateSlideShowItem,
            controller: 'ItemCreateSlideShowCtrl'
        }).state('editSlideShowItem', {
            url:'/editSlideShowItem/:itemId',
            templateUrl: URLS.partialsEditSlideShowItem,
            controller: 'ItemEditSlideShowCtrl'
        }).state('endpoints', {
            url: '/endpoints',
            templateUrl: URLS.partialsMappings,
            controller: 'MappingsCtrl'
        });
});
