/**
 * Created by 新乐 on 2017/5/21.
 */
qesModule.config(function ($stateProvider) {
    $stateProvider
    //计分管理列表页
        .state('waitcountScore', {
            url: "/waitcountScore",
            templateUrl: "tpls/countScoreManage/waitcountScore.html",
            controller: "waitcountScoreCtrl"
        })

});