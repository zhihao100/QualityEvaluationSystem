/**
 * Created by liuzhihao on 2017/3/10.
 */
routerModule.controller("indexCtrl", [
    '$http',
    '$scope',
    function ($http, $scope) {
        //登录进入，获取用户名显示
        $http.post("getCurrentManager").success(function (data) {
            $scope.user = data;
        });
        $scope.logout = function () {
            $http.post('logout', {}).success(function () {
                window.location.reload();
            });
        }
    }])
    .controller("initializeHomeCtrl", [
        '$http',
        '$scope',
        '$state',
        function ($http, $scope, $state) {

        }]);
