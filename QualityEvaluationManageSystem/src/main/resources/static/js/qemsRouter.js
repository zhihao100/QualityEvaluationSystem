/**
 * 基于ui-router路由插件的前端路由实现
 * Created by liuzhihao on 2017/4/7.
 */
var routerModule = angular.module('routerModule', ['ui.router', 'ui.bootstrap']);

routerModule.config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/home");
    $stateProvider
    //首页
        .state('home', {
            url: "/home",
            templateUrl: "tpls/home.html"
        });
});
