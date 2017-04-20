/**
 * Created by liuzhihao on 2017/4/19.
 */
qesModule.config(function ($stateProvider) {
    $stateProvider
    //细则列表页
        .state('detailRule', {
            url: "/detailRule?ruleId&mainRuleName",
            templateUrl: "tpls/detailRuleManage/detailRule.html",
            controller: "detailRuleManageCtrl",
        });
})
