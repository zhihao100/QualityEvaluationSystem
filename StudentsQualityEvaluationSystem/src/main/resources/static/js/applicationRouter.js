/**
 * Created by 新乐 on 2017/5/15.
 */
sqesModule.config(function ($stateProvider) {
    $stateProvider
    //细则列表页
        .state('detailRule', {
            url: "/detailRule?mainRuleId&mainRuleName",
            templateUrl: "tpls/application/detailRule.html",
            controller: "detailRuleCtrl"
        })
        //项目列表页
        .state('itemRule', {
            url: "/itemRule/:ruleId/:mainRuleId",
            templateUrl: "tpls/application/itemRule.html",
            controller: "itemRuleCtrl"
        });
})
