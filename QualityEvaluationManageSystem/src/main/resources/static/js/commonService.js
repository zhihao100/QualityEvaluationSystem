/**
 * Created by liuzhihao on 2017/4/11.
 */
qesModule.factory("userService", ['$http', function ($http) {
    //登录进入，获取用户名显示
    return {
        user: function () {
            return $http.post("getCurrentManager");
        }
    };
}]);
qesModule.service("qemsAlert", ['$modal', '$http', '$location', '$state', function ($modal, $http, $location, $state) {
    /*消息提示模式窗口*/
    this.show = function (config, addressTo) {
        var DEFAULT = {
            templateUrl: 'tpls/common/popupMessage.html',
            controller: function ($scope, $modalInstance, items) {
                $scope.results = items;
                // 确认按钮
                $scope.ok = function () {
                    $modalInstance.close();
                    if ($scope.results.msg == "新增成功" || $scope.results.msg == "修改成功") {
                        $location.path("/" + $scope.results.doSomething);
                    }
                    if ($scope.results.msg == "新增完成" || $scope.results.msg == "修改完成") {
                        $state.go($scope.results.doSomething, {}, {reload: true});
                    }
                };
                // 取消按钮
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            },
            size: 'sm',
            title: '提示消息',
            msg: ''
        };
        var cfg = (Object.prototype.toString.call(config) === "[object String]") ? $.extend(DEFAULT, {msg: config}, {doSomething: addressTo}) : $.extend(DEFAULT, config, addressTo);

        //创建弹框对象
        var modalInstance = $modal.open({
            templateUrl: cfg.templateUrl,
            controller: cfg.controller,
            size: cfg.size,
            resolve: {
                items: function () {
                    return {title: cfg.title, msg: cfg.msg, doSomething: cfg.doSomething};
                }
            }
        });
        return modalInstance;
    }
}]);