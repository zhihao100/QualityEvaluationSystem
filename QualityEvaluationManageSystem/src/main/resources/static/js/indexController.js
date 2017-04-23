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
        //细则大类查询
        var postData = {
            currentPage: 1,
            pageSize: 20,
            mainRule: {}
        };
        $http.post("findAllMainRuleByMultiConditionAndPage", postData).success(function (rs) {
            $scope.mainRules = rs.content;
            //细则大类的URL定义
            for (var i = 0; i < $scope.mainRules.length; i++) {
                $scope.mainRules[i].url = "detailRule";
            }
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
