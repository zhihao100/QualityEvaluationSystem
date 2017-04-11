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
});
