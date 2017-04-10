/**
 * 登录验证
 */
$.idcode.setCode();   //加载生成验证码方法

//表单提交时进行验证
function mySubmit(flag) {
    return flag;
}

$("#qesLoginForm").submit(function () {
    var IsBy = $.idcode.validateCode()  //文本框输入的验证码进行验证，调用返回值，返回值结果为true或者false
    var userName = $("#username").val();
    var password = $("#password").val();

    if (!userName || !password) {
        $("#errUserTip").removeClass('display-hide');
        $("#errUserTip").html("请输入用户名密码。");
        return mySubmit(false);
    } else {
        $("#errUser").text("");
        if (!IsBy) {
            $("#errUserTip").removeClass('display-hide');
            $("#errUserTip").html("验证码错误。");
            return mySubmit(false);
        } else {
            $("#errUserTip").addClass('display-hide');
            return mySubmit(true);
        }
    }
});
