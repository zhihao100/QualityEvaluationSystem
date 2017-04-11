/**
 * Created by liuzhihao on 2017/3/10.
 */
qesModule.controller("indexCtrl", [
    '$http',
    '$scope',
    'userService',
    function ($http, $scope, userService) {
        //登录进入，获取用户名显示
        userService.user().then(function (res) {
            $scope.user = res.data;
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
