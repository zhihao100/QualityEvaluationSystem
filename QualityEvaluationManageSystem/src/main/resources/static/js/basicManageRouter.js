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
        //专业信息管理
        .state("majorManage", {
            url: "/majorManage/:instituteId",
            templateUrl: "tpls/basicManage/majorManage.html",
            controller: "majorManageCtrl",
            resolve: {
                institute: function ($http, $stateParams) {
                    return $http.post("showInstituteDetails", $stateParams.instituteId);
                }
            }
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
        //学生信息管理
        .state("studentManage", {
            url: "/studentManage",
            templateUrl: "tpls/basicManage/studentManage.html",
            controller: "studentManageCtrl"
        })
        //学生管理详情
        .state('studentManageInfo', {
            url: "/studentManageInfo/:studentId",
            templateUrl: 'tpls/basicManage/studentManageInfo.html',
            controller: "studentManageInfoCtrl"
        })
        //学生信息编辑
        .state('studentManageEdit', {
            url: "/studentManageEdit/:studentId",
            templateUrl: 'tpls/basicManage/studentManageEdit.html',
            controller: 'studentManageEditCtrl'
        })
        //学生新增
        .state('studentManageAdd', {
            url: "/studentManageAdd",
            templateUrl: 'tpls/basicManage/studentManageAdd.html',
            controller: 'studentManageAddCtrl'
        })
        //细则大类管理
        .state('mainRuleManage', {
            url: "/mainRuleManage",
            templateUrl: 'tpls/basicManage/mainRuleManage.html',
            controller: "mainRuleManageCtrl"
        })
        //细则大类管理详情
        .state('mainRuleManageInfo', {
            url: "/mainRuleManageInfo/:ruleId",
            templateUrl: 'tpls/basicManage/mainRuleManageInfo.html',
            controller: "mainRuleManageInfoCtrl"
        })
        //细则大类编辑
        .state('mainRuleManageEdit', {
            url: "/mainRuleManageEdit/:ruleId",
            templateUrl: 'tpls/basicManage/mainRuleManageEdit.html',
            controller: 'mainRuleManageEditCtrl'
        })
        //细则大类新增
        .state('mainRuleManageAdd', {
            url: "/mainRuleManageAdd",
            templateUrl: 'tpls/basicManage/mainRuleManageAdd.html',
            controller: 'mainRuleManageAddCtrl'
        })
});
