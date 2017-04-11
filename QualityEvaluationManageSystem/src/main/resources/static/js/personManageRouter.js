/**
 * 用户管理路由
 * Created by liuzhihao on 2017/4/11.
 */
qesModule.config(function ($stateProvider) {
    $stateProvider
    //用户管理
        .state('personManage', {
            url: "/personManage",
            templateUrl: 'tpls/personManage/personManage.html',
            controller: "personManageCtrl"
        })
        //personManageInfo用户管理详情
        .state('personManageInfo', {
            url: "/personManageInfo/:managerId",
            templateUrl: 'tpls/personManage/personManageInfo.html',
            controller: "personManageInfoCtrl"
        })
        //用户编辑personManageEdit
        .state('personManageEdit', {
            url: "/personManageEdit/:managerId",
            templateUrl: 'tpls/personManage/personManageEdit.html',
            controller: 'personManageEditCtrl'
        })
        //用户新增personManageAdd
        .state('personManageAdd', {
            url: "/personManageAdd",
            templateUrl: 'tpls/personManage/personManageAdd.html',
            controller: 'personManageAddCtrl'
        })
});