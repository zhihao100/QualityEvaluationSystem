//后台权限管理
qesModule.controller('personManageCtrl', [
    '$scope',
    '$http',
    '$state',
    function ($scope, $http, $state) {
        //管理员列表
        $scope.personManage = {};
        var search = function () {
            var postData = {
                currentPage: $scope.paginationConf.currentPage,
                pageSize: $scope.paginationConf.itemsPerPage,
                manager: $scope.personManage,
            };
            $http.post('findAllManagerByMultiConditionAndPage', postData).success(function (response) {
                $scope.paginationConf.totalItems = response.pagersInfo.totalElements;
                $scope.personManages = response.dataList;
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
            $scope.personManage = {};
        }
        //启用停用切换
        $scope.changeState = function (state, id) {
            $scope.manager = {};
            $scope.manager.managerState = state;
            $scope.manager.managerId = id;
            $http.post("updateManagerState", $scope.manager).success(function () {
                $state.go('personManage', {}, {reload: true});
            })
        }
    }])
    .controller('personManageInfoCtrl', [
        '$scope',
        '$http',
        '$stateParams',
        function ($scope, $http, $stateParams) {
            //管理员详情
            $scope.managerId = $stateParams.managerId;
            $http.post('showManagerDetails', $scope.managerId).success(function (response) {
                $scope.managerInfo = response;
            });
        }])
    .controller('personManageEditCtrl', [
        '$scope',
        '$http',
        '$stateParams',
        '$modal',
        '$location',
        '$log',
        function ($scope, $http, $stateParams, $modal, $location, $log, $modalInstance) {
            //管理员编辑
            //学院查询
            $http.post("findAllInstitute").success(function (rs) {
                $scope.instituteManages = rs;
            });
            $scope.managerId = $stateParams.managerId;
            $http.post('showManagerDetails', $scope.managerId).success(function (response) {
                $scope.managerInfo = response;
            });
            $scope.manageSubmit = function () {
                $scope.manager = {};
                $scope.manager = $scope.managerInfo.manager;
                $scope.manager.managerId = $scope.managerId;
                $scope.result = {};
                $http.post("updateManager", $scope.manager).success(function (response) {
                    $scope.result.title = "提示消息";
                    $scope.result.msg = response;
                    $scope.open('sm');
                    $scope.$modalInstance = undefined;
                });
                // 弹窗
                $scope.open = function (size) {
                    $scope.modalInstance = $modal.open({
                        templateUrl: 'tpls/common/popupMessage.html',
                        controller: ModalInstanceCtrl,
                        size: size,
                        resolve: {
                            requestResults: function () {
                                return $scope.result;
                            }
                        }
                    });
                    // 成功的回调方法 （可带参数）
                    $scope.modalInstance.result.then(function () {
                        // 跳转到列表页面
                        if ($scope.result.msg == "该管理员已存在" || $scope.result.msg == "该管理员不存在") {

                        } else {
                            $location.path('/personManage');
                        }
                        // 失败的回调方法
                    }, function () {
                        $log.info('Modal dismissed at: ' + new Date());
                    });
                };
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
            var ModalInstanceCtrl = function ($scope, $modalInstance,
                                              requestResults) {
                $scope.results = requestResults;
                // 确认按钮（close()可以带参数）
                $scope.ok = function () {
                    $modalInstance.close();
                };
                // 取消按钮
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            };//弹窗结束
        }])
    .controller('personManageAddCtrl', [
        '$scope',
        '$http',
        '$modal',
        '$location',
        '$log',
        function ($scope, $http, $modal, $location, $log, $modalInstance) {
            //管理员添加
            //学院查询
            $http.post("findAllInstitute").success(function (rs) {
                $scope.instituteManages = rs;
            });
            //提交
            $scope.manageSubmit = function () {
                $scope.manager = {};
                $scope.managerInitState = "启用";
                if ($scope.managerInfo.managerState == null) {
                    $scope.managerInfo.managerState = '启用';
                }
                $scope.manager = $scope.managerInfo;
                $scope.result = {};
                $http.post('createManager', $scope.manager).success(function (response) {
                    $scope.result.title = "提示消息";
                    $scope.result.msg = response;
                    $scope.open('sm');
                    $scope.$modalInstance = undefined;
                });
                // 弹窗
                $scope.open = function (size) {
                    $scope.modalInstance = $modal.open({
                        templateUrl: 'tpls/common/popupMessage.html',
                        controller: ModalInstanceCtrl,
                        size: size,
                        resolve: {
                            requestResults: function () {
                                return $scope.result;
                            }
                        }
                    });
                    // 成功的回调方法 （可带参数）
                    $scope.modalInstance.result.then(function () {
                        // 跳转到列表页面
                        if ($scope.result.msg == "该管理员已存在") {

                        } else {
                            $location.path('/personManage');
                        }
                        // 失败的回调方法
                    }, function () {
                        $log.info('Modal dismissed at: ' + new Date());
                    });
                };
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
            var ModalInstanceCtrl = function ($scope, $modalInstance,
                                              requestResults) {
                $scope.results = requestResults;
                // 确认按钮（close()可以带参数）
                $scope.ok = function () {
                    $modalInstance.close();
                };
                // 取消按钮
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            };//弹窗结束
        }]);