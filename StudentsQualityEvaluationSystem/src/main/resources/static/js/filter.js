/**
 * Created by liuzhihao on 2017/4/17.
 */
sqesModule.filter("toPercent", function () {
    return function (value) {
        return value * 100 + "%";
    }
})
