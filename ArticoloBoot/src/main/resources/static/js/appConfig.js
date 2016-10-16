

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
	    }).state('homeItem', {
	        url:'/partialsListItem.html',
	        templateUrl: 'partialsListItem.html',
	        controller: 'ItemCtrl'
	    }).state('homeEditListItem', {
	        url:'/homeEditListItem',
	        templateUrl: URLS.partialsEditList,
	        controller: 'EditListCtrl'
	    }).state('viewItem', {
            url:'/viewItem/:id/:name',
            templateUrl: URLS.partialsViewItem,
            controller: 'ViewItemCtrl'
	    }).state('editItem', {
            url:'/editItem/:id/:name',
            templateUrl: URLS.partialsEditItem,
            controller: 'ItemEditCtrl'
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
