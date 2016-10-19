

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
        }).state('endpoints', {
            url: '/endpoints',
            templateUrl: URLS.partialsMappings,
            controller: 'MappingsCtrl'
        });
});
