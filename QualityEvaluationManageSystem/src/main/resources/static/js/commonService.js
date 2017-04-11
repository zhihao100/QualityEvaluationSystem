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