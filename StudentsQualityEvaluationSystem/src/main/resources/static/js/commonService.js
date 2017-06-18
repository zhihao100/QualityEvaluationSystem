/**
 * Created by liuzhihao on 2017/4/11.
 */
sqesModule.factory("userService", ['$http', function ($http) {
    //登录进入，获取用户名显示
    return {
        user: function () {
            return $http.post("getCurrentUser");
        }
    };
}]);
sqesModule.service("sqesAlert", ['$modal', '$http', '$location', '$state', function ($modal, $http, $location, $state) {
    /*消息提示模式窗口*/
    this.show = function (config, addressTo) {
        var DEFAULT = {
            templateUrl: 'tpls/common/popupMessage.html',
            controller: function ($scope, $modalInstance, items) {
                $scope.results = items;
                // 确认按钮
                $scope.ok = function () {
                    $modalInstance.close();
                    if ($scope.results.msg == "申请成功" || $scope.results.msg == "修改成功") {
                        $location.url("/" + $scope.results.doNext);
                    }
                    if ($scope.results.msg == "撤回成功" || $scope.results.msg == "修改完成") {
                        $state.go($scope.results.doNext, {}, {reload: true});
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
        var cfg = (Object.prototype.toString.call(config) === "[object String]") ? $.extend(DEFAULT, {msg: config}, {doNext: addressTo}) : $.extend(DEFAULT, config, addressTo);

        //创建弹框对象
        var modalInstance = $modal.open({
            templateUrl: cfg.templateUrl,
            controller: cfg.controller,
            size: cfg.size,
            resolve: {
                items: function () {
                    return {title: cfg.title, msg: cfg.msg, doNext: cfg.doNext};
                }
            }
        });
        return modalInstance;
    }
}]);
sqesModule.service("scoreRule", function ($http) {
    //请求课程成绩加分规则数据
    return {
        scoreRule: function () {
            return $http.get("data/scoreRule.json");
        }
    }
});