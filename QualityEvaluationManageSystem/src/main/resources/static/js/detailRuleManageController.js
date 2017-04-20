/**
 * Created by liuzhihao on 2017/4/19.
 */
qesModule.controller("detailRuleManageCtrl", [
    '$http',
    '$scope',
    '$stateParams',
    function ($http, $scope, $stateParams) {
        $scope.mainRuleName = $stateParams.mainRuleName;
    //细则列表页
        $scope.detailRule = {};
        $scope.detailRule.mainRuleId = $stateParams.ruleId;
        var search = function () {
            var postData = {
                currentPage: $scope.paginationConf.currentPage,
                pageSize: $scope.paginationConf.itemsPerPage,
                detailRule: $scope.detailRule
            };
            $http.post('findDetailRuleByMultiConditionAndPage', postData).success(function (response) {
                $scope.paginationConf.totalItems = response.totalElements;
                $scope.detailRules = response.content;
            });
        };
        $scope.search = search;

        //配置分页基本参数
        $scope.paginationConf = {
            currentPage: 1,
            itemsPerPage: 5,
            perPageOptions: [5, 10, 20]
        };
        $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', search);
        //清空查询条件
        $scope.reset = function () {
            $scope.detailRule = {};
            $scope.detailRule.mainRuleId = $stateParams.ruleId;
        }
        //启用停用切换
        $scope.changeState = function (state, id) {
            $scope.detailRule = {};
            $scope.detailRule.ruleState = state;
            $scope.detailRule.ruleId = id;
            $http.post("updateDetailRuleState", $scope.detailRule).success(function () {
                $state.go('detailRule', {}, {reload: true});
            })
        }
    }]);