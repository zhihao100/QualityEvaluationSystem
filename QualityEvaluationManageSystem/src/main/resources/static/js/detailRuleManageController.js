/**
 * Created by liuzhihao on 2017/4/19.
 */
qesModule.controller("detailRuleCtrl", [
    '$http',
    '$scope',
    '$stateParams',
    'qemsAlert',
    function ($http, $scope, $stateParams, qemsAlert) {
        $scope.mainRuleName = $stateParams.mainRuleName;
    //细则列表页
        $scope.detailRule = {};
        $scope.detailRule.mainRuleId = $stateParams.mainRuleId;
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
            itemsPerPage: 10,
            perPageOptions: [5, 10, 20]
        };
        $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', search);
        //清空查询条件
        $scope.reset = function () {
            $scope.detailRule = {};
            $scope.detailRule.mainRuleId = $stateParams.mainRuleId;
        }
        //启用停用切换
        $scope.changeState = function (state, id) {
            $scope.detailRule.ruleState = state;
            $scope.detailRule.ruleId = id;
            $http.post("updateDetailRuleState", $scope.detailRule).success(function (response) {
                qemsAlert.show(response, "detailRule");
            })
        }
    }])
    .controller('detailRuleAddCtrl', [
        '$scope',
        '$http',
        '$stateParams',
        'qemsAlert',
        function ($scope, $http, $stateParams, qemsAlert) {
            //细则新增
            $scope.detailRule = {};
            $scope.detailRule.ruleState = "启用";
            $scope.detailRule.mainRuleId = $stateParams.mainRuleId;
            $scope.mainRuleName = $stateParams.mainRuleName;
            //提交
            $scope.detailRuleSubmit = function () {
                $http.post('createDetailRule', $scope.detailRule).success(function (response) {
                    qemsAlert.show(response, "detailRule?mainRuleId=" + $scope.detailRule.mainRuleId + "&mainRuleName=" + $scope.mainRuleName);
                });
            };
        }])
    .controller('detailRuleInfoCtrl', [
        '$scope',
        '$http',
        '$stateParams',
        function ($scope, $http, $stateParams) {
            //细则详情
            $http.post('showDetailRuleInfo', $stateParams.ruleId).success(function (response) {
                $scope.detailRuleInfo = response;
            });
        }])
    .controller('detailRuleEditCtrl', [
        '$scope',
        '$http',
        '$stateParams',
        'qemsAlert',
        function ($scope, $http, $stateParams, qemsAlert) {
            //细则编辑
            $http.post('showDetailRuleInfo', $stateParams.ruleId).success(function (response) {
                $scope.detailRule = response.detailRule;
                $scope.mainRule = response.mainRule;
            });
            //提交
            $scope.detailRuleSubmit = function () {
                $http.post('updateDetailRule', $scope.detailRule).success(function (response) {
                    qemsAlert.show(response, "detailRule?mainRuleId=" + $scope.mainRule.ruleId + "&mainRuleName=" + $scope.mainRule.ruleName);
                });
            }
        }])
    .controller('itemRuleCtrl', [
        '$scope',
        '$http',
        '$stateParams',
        'qemsAlert',
        '$modal',
        function ($scope, $http, $stateParams, qemsAlert, $modal) {
            //项目管理列表
            $http.post('itemRule', $stateParams.ruleId).success(function (response) {
                $scope.itemRule = response;
            });
            //启用停用切换
            $scope.changeState = function (state, id) {
                $scope.itemRule.itemState = state;
                $scope.itemRule.itemId = id;
                $http.post("updateItemRuleState", $scope.itemRule).success(function (response) {
                    qemsAlert.show(response, "itemRule");
                })
            }
            //新增项目
            $scope.itemAdd = function () {
                $scope.itemRule.itemName = "";
                $scope.itemRule.itemScore = "";
                $scope.itemRule.itemState = "启用";
                $scope.itemRule.detailRuleId = $stateParams.ruleId;
                $modal.open({
                    templateUrl: "tpls/detailRuleManage/itemRuleAdd.html",
                    controller: "itemRuleAddCtrl",
                    resolve: {
                        itemRule: function () {
                            return angular.copy($scope.itemRule);
                        }
                    }
                })
            }
            //编辑项目
            $scope.itemEdit = function (item) {
                $modal
                    .open({
                        templateUrl: 'tpls/detailRuleManage/itemRuleEdit.html',
                        controller: 'itemRuleEditCtrl',
                        // 传参
                        resolve: {
                            item: function () {
                                return angular.copy(item);
                            }
                        }
                    });
            }
        }])
    .controller("itemRuleAddCtrl", [
        '$scope',
        '$http',
        'itemRule',
        'qemsAlert',
        '$modalInstance',
        function ($scope, $http, itemRule, qemsAlert, $modalInstance) {
            //新增项目
            $scope.itemRule = itemRule;
            // 确认按钮
            $scope.ok = function () {
                $modalInstance.close();
                $http.post("itemRuleAdd", $scope.itemRule).success(function (rs) {
                    qemsAlert.show(rs, "itemRule");
                })
            }
            // 取消按钮
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        }
    ])
    .controller("itemRuleEditCtrl", [
        '$scope',
        '$http',
        'item',
        'qemsAlert',
        '$modalInstance',
        function ($scope, $http, item, qemsAlert, $modalInstance) {
            //编辑项目
            $scope.itemRule = item;
            // 确认按钮
            $scope.ok = function () {
                $modalInstance.close();
                $http.post("itemRuleEdit", $scope.itemRule).success(function (rs) {
                    qemsAlert.show(rs, "itemRule");
                })
            }
            // 取消按钮
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        }
    ]);