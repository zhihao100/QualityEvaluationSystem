/**
 * Created by liuzhihao on 2017/4/25.
 */
var sqesModule = angular.module("sqesModule", ['ui.router', 'tm.pagination']);
sqesModule.config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/index");
    $stateProvider
    //首页
        .state('index', {
            url: '/index',
            views: {
                '': {
                    templateUrl: 'index.html'
                },
                'home': {
                    templateUrl: 'tpls/home.html',
                    controller: 'indexCtrl'
                }
            }
        })
});

