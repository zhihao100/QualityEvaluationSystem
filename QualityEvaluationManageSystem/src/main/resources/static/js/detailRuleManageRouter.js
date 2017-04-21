/**
 * Created by liuzhihao on 2017/4/19.
 */
qesModule.config(function ($stateProvider) {
    $stateProvider
    //细则列表页
        .state('detailRule', {
            url: "/detailRule?mainRuleId&mainRuleName",
            templateUrl: "tpls/detailRuleManage/detailRule.html",
            controller: "detailRuleCtrl"
        })
        //细则新增
        .state('detailRuleAdd', {
            url: "/detailRuleAdd?mainRuleId&mainRuleName",
            templateUrl: "tpls/detailRuleManage/detailRuleAdd.html",
            controller: "detailRuleAddCtrl"
        })
        //细则详情
        .state('detailRuleInfo', {
            url: "/detailRuleInfo/:ruleId",
            templateUrl: "tpls/detailRuleManage/detailRuleInfo.html",
            controller: "detailRuleInfoCtrl"
        })
        //细则编辑
        .state('detailRuleEdit', {
            url: "/detailRuleEdit/:ruleId",
            templateUrl: "tpls/detailRuleManage/detailRuleEdit.html",
            controller: "detailRuleEditCtrl"
        });
})
