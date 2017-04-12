/**
 *基础设置
 * Created by liuzhihao on 2017/4/11.
 */
qesModule.config(function ($stateProvider) {
    $stateProvider
    //学院信息管理
        .state("instituteManage", {
            url: "/instituteManage",
            templateUrl: "tpls/basicManage/instituteManage.html",
            controller: "instituteManageCtrl"
        })
        //学院管理详情
        .state('instituteManageInfo', {
            url: "/instituteManageInfo/:instituteId",
            templateUrl: 'tpls/basicManage/instituteManageInfo.html',
            controller: "instituteManageInfoCtrl"
        })
        //学院信息编辑
        .state('instituteManageEdit', {
            url: "/instituteManageEdit/:instituteId",
            templateUrl: 'tpls/basicManage/instituteManageEdit.html',
            controller: 'instituteManageEditCtrl'
        })
        //学院新增
        .state('instituteManageAdd', {
            url: "/instituteManageAdd",
            templateUrl: 'tpls/basicManage/instituteManageAdd.html',
            controller: 'instituteManageAddCtrl'
        })
        //班级信息管理
        .state("classManage", {
            url: "/classManage",
            templateUrl: "tpls/basicManage/classManage.html",
            controller: "classManageCtrl"
        })
        //班级管理详情
        .state('classManageInfo', {
            url: "/classManageInfo/:classId",
            templateUrl: 'tpls/basicManage/classManageInfo.html',
            controller: "classManageInfoCtrl"
        })
        //班级信息编辑
        .state('classManageEdit', {
            url: "/classManageEdit/:classId",
            templateUrl: 'tpls/basicManage/classManageEdit.html',
            controller: 'classManageEditCtrl'
        })
        //班级新增
        .state('classManageAdd', {
            url: "/classManageAdd",
            templateUrl: 'tpls/basicManage/classManageAdd.html',
            controller: 'classManageAddCtrl'
        })
});
