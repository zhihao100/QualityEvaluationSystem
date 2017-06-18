/**
 * Created by 新乐 on 2017/5/15.
 */
sqesModule.controller("detailRuleCtrl", [
    '$http',
    '$scope',
    '$stateParams',
    function ($http, $scope, $stateParams) {
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

    }])
    .controller('itemRuleCtrl', [
        '$scope',
        '$http',
        '$modal',
        '$stateParams',
        'scoreRule',
        'userService',
        '$log',
        'sqesAlert',
        function ($scope, $http, $modal, $stateParams, scoreRule, userService, $log, sqesAlert) {
            //登录进入，获取用户信息
            userService.user().then(function (res) {
                $scope.user = res.data;
            });
            //请求课程成绩加分规则数据
            scoreRule.scoreRule().then(function (res) {
                $scope.scoreRules = res.data;
            })
            $scope.scoreRule = {};
            //班级人数切换
            $scope.newPeopleNum = 20;
            $scope.changePeopleNum = function (newPeopleNum) {
                $scope.newPeopleNum = newPeopleNum;
            }
            //项目管理列表
            $http.post('itemRule', $stateParams.ruleId).success(function (response) {
                $scope.itemRule = response;
                //查找已申请列表
                $http.post('findApplicationByStudentIdAndDetailRuleId' + '/' + $scope.user.studentId, $stateParams.ruleId).success(function (res) {
                    for (var i = 0; i < $scope.itemRule.itemRuleList.length; i++) {
                        for (var k = 0; k < res.length; k++) {
                            if (res[k].itemRuleId == $scope.itemRule.itemRuleList[i].itemId) {
                                $scope.itemRule.itemRuleList[i].IsApplication = true;
                                $scope.itemRule.itemRuleList[i].applicationState = res[k].applicationState;
                            }
                        }
                    }
                });
            });

            //项目申请
            $scope.save = function (itemRuleList) {
                $scope.applications = [];
                for (var j = 0; j < itemRuleList.length; j++) {
                    if (itemRuleList[j].itemChoice && itemRuleList[j].itemChoice == true) {
                        $scope.application = {};
                        $scope.application.itemRuleId = itemRuleList[j].itemId;
                        $scope.application.detailRuleId = itemRuleList[j].detailRuleId;
                        $scope.application.mainRuleId = $stateParams.mainRuleId;
                        $scope.application.studentId = $scope.user.studentId;
                        $scope.application.studentName = $scope.user.studentName;
                        $scope.application.instituteId = $scope.user.instituteId;
                        $scope.application.applicationState = "待审核";
                        $scope.applications.push($scope.application);
                    }
                }
                $http.post("applicationAdd", $scope.applications).success(function (response) {
                    sqesAlert.show(response, "detailRule?mainRuleId=" + $scope.itemRule.mainRule.ruleId + "&mainRuleName=" + $scope.itemRule.mainRule.ruleName);
                })
            }
            //项目撤回
            $scope.revoke = function (itemId) {
                // 弹窗
                $scope.result = {};
                $scope.result.title = "提示消息";
                $scope.result.msg = "确认撤回此项目申请吗？";
                $scope.result.studentId = $scope.user.studentId;
                $scope.result.itemId = itemId;
                var ModalInstanceCtrl = function ($scope, $modalInstance,
                                                  requestResults) {
                    $scope.results = requestResults;
                    // 确认按钮
                    $scope.ok = function () {
                        $modalInstance.close();
                        $http.post("applicationDelete" + "/" + $scope.results.studentId, $scope.results.itemId).success(function (rs) {
                            sqesAlert.show(rs, "itemRule");

                        })
                    };
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                };

                $modal.open({
                    templateUrl: 'tpls/common/popupMessagePlus.html',
                    controller: ModalInstanceCtrl,
                    size: 'sm',
                    resolve: {
                        requestResults: function () {
                            return $scope.result;
                        }
                    }
                }).result.then(function () {

                }, function () {
                    $log.info('Modal dismissed at: ' + new Date());
                });
            }

        }]);

