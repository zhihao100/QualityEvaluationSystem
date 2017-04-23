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
            itemsPerPage: 10,
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
        'qemsAlert',
        function ($scope, $http, $stateParams, qemsAlert) {
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
                $http.post("updateManager", $scope.manager).success(function (response) {
                    qemsAlert.show(response, "personManage");
                });
            };
        }])
    .controller('personManageAddCtrl', [
        '$scope',
        '$http',
        'qemsAlert',
        function ($scope, $http, qemsAlert) {
            //管理员添加
            //学院查询
            $http.post("findAllInstitute").success(function (rs) {
                $scope.instituteManages = rs;
            });
            $scope.managerInfo = {};
            $scope.managerInfo.managerState = "启用";
            //提交
            $scope.manageSubmit = function () {
                $scope.manager = {};
                $scope.manager = $scope.managerInfo;
                $http.post('createManager', $scope.manager).success(function (response) {
                    qemsAlert.show(response, "personManage");
                });
            };
        }]);