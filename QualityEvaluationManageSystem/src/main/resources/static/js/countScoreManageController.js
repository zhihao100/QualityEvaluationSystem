/**
 * 计分管理
 * Created by 新乐 on 2017/5/21.
 */
qesModule.controller('waitcountScoreCtrl', [
    '$scope',
    '$http',
    'userService',
    '$modal',
    '$log',
    'qemsAlert',
    function ($scope, $http, userService, $modal, $log, qemsAlert) {
        //查询当前管理员所在学院
        userService.user().then(function (res) {
            $scope.user = res.data;
        });

        //待审核列表
        $scope.application = {};
        $scope.application.instituteId = $scope.user.instituteId;
        $scope.application.applicationState = "待审核";
        var search = function () {
            var postData = {
                currentPage: $scope.paginationConf.currentPage,
                pageSize: $scope.paginationConf.itemsPerPage,
                application: $scope.application,
            };
            $http.post('findWaitApplicationByMultiConditionAndPage', postData).success(function (response) {
                $scope.paginationConf.totalItems = response.pagersInfo.totalElements;
                $scope.applications = response.dataList;
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
        //审核通过
        $scope.pass = function (applicationId) {
            // 弹窗
            $scope.result = {};
            $scope.result.title = "提示消息";
            $scope.result.msg = "确认审核通过此项申请吗？";
            $scope.result.applicationId = applicationId;
            var ModalInstanceCtrl = function ($scope, $modalInstance,
                                              requestResults) {
                $scope.results = requestResults;
                $scope.applicationState = "审核通过";
                // 确认按钮
                $scope.ok = function () {
                    $modalInstance.close();
                    $http.post("updateApplicationState" + "/" + $scope.results.applicationId, $scope.applicationState).success(function (rs) {
                        qemsAlert.show(rs, "waitcountScore");
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
        //审核拒绝
        $scope.refuse = function (applicationId) {
            // 弹窗
            $scope.result = {};
            $scope.result.title = "提示消息";
            $scope.result.msg = "确认审核拒绝此项申请吗？";
            $scope.result.applicationId = applicationId;
            var ModalInstanceCtrl = function ($scope, $modalInstance,
                                              requestResults) {
                $scope.results = requestResults;
                $scope.applicationState = "审核未通过";
                // 确认按钮
                $scope.ok = function () {
                    $modalInstance.close();
                    $http.post("updateApplicationState" + "/" + $scope.results.applicationId, $scope.applicationState).success(function (rs) {
                        qemsAlert.show(rs, "waitcountScore");
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
        //清空查询条件
        $scope.reset = function () {
            $scope.application = {};
            $scope.application.instituteId = $scope.user.instituteId;
            $scope.application.applicationState = "待审核";
        }

    }])