/**
 * Created by liuzhihao on 2017/4/25.
 */
var sqesModule = angular.module("sqesModule", ['ui.router', 'ui.bootstrap', 'tm.pagination']);
sqesModule.config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/home");
    $stateProvider
    //首页
        .state('home', {
            url: '/home',
                    templateUrl: 'tpls/home.html',
                    controller: 'indexCtrl'

        })
});

