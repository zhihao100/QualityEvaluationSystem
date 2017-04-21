/**
 * 基础设置
 * Created by liuzhihao on 2017/4/11.
 */
qesModule.controller('instituteManageCtrl', [
    '$scope',
    '$http',
    '$state',
    function ($scope, $http, $state) {
        //学院列表
        $scope.instituteManage = {};
        var search = function () {
            var postData = {
                currentPage: $scope.paginationConf.currentPage,
                pageSize: $scope.paginationConf.itemsPerPage,
                institute: $scope.instituteManage,
            };
            $http.post('findAllInstituteByMultiConditionAndPage', postData).success(function (response) {
                $scope.paginationConf.totalItems = response.totalElements;
                $scope.instituteManages = response.content;
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
            $scope.instituteManage = {};
        }
        //启用停用切换
        $scope.changeState = function (state, id) {
            $scope.institute = {};
            $scope.institute.instituteState = state;
            $scope.institute.instituteId = id;
            $http.post("updateInstituteState", $scope.institute).success(function () {
                $state.go('instituteManage', {}, {reload: true});
            })
        }
    }])
    .controller('instituteManageInfoCtrl', [
        '$scope',
        '$http',
        '$stateParams',
        function ($scope, $http, $stateParams) {
            //学院详情
            $scope.instituteId = $stateParams.instituteId;
            $http.post('showInstituteDetails', $scope.instituteId).success(function (response) {
                $scope.instituteInfo = response;
            });
        }])
    .controller('instituteManageEditCtrl', [
        '$scope',
        '$http',
        '$stateParams',
        'qemsAlert',
        function ($scope, $http, $stateParams, qemsAlert) {
            //学院编辑
            $scope.instituteId = $stateParams.instituteId;
            $http.post('showInstituteDetails', $scope.instituteId).success(function (response) {
                $scope.instituteInfo = response;
            });
            $scope.instituteSubmit = function () {
                $scope.institute = {};
                $scope.institute = $scope.instituteInfo;
                $scope.institute.instituteId = $scope.instituteId;
                $http.post("updateInstitute", $scope.institute).success(function (response) {
                    qemsAlert.show(response, "instituteManage");
                });
                };
        }])
    .controller('instituteManageAddCtrl', [
        '$scope',
        '$http',
        'qemsAlert',
        function ($scope, $http, qemsAlert) {
            //学院添加
            //默认状态为启用
            $scope.instituteInfo = {};
            $scope.instituteInfo.instituteState = '启用';
            $scope.manageSubmit = function () {
                $scope.institute = {};
                $scope.institute = $scope.instituteInfo;
                $http.post('createInstitute', $scope.institute).success(function (response) {
                    qemsAlert.show(response, "instituteManage");
                });
            };
        }])
    .controller("majorManageCtrl", [
        '$http',
        '$scope',
        '$state',
        '$stateParams',
        'institute',
        '$modal',
        function ($http, $scope, $state, $stateParams, institute, $modal) {
            //通过路由的resolve注入学院信息到控制器中
            $scope.institute = institute.data;
            //根据学院ID查找该学院所有专业
            $scope.instituteId = $stateParams.instituteId;
            $http.post("majorManage/" + $scope.instituteId, {}).success(function (rs) {
                $scope.majorInfo = rs;
            });
            //启用停用切换
            $scope.changeState = function (state, id) {
                $scope.major = {};
                $scope.major.majorState = state;
                $scope.major.majorId = id;
                $http.post("updateMajorState", $scope.major).success(function (rs) {
                    $state.go('majorManage', {}, {reload: true});
                })
            }
            $scope.majorEdit = function (majorName, majorId) {
                $scope.majorParam = {};
                $scope.majorParam.majorName = majorName;
                $scope.majorParam.majorId = majorId;
                $modal
                    .open({
                        templateUrl: 'tpls/basicManage/majorManageEdit.html',
                        controller: 'majorManageEditCtrl',
                        // 传参
                        resolve: {
                            data: function () {
                                return angular.copy($scope.majorParam);
                            }
                        }
                    });
            }
            $scope.majorAdd = function (instituteId) {
                $scope.major = {};
                //新增专业默认为启用状态
                $scope.major.majorState = "启用";
                $scope.major.instituteId = instituteId;
                $modal
                    .open({
                        templateUrl: 'tpls/basicManage/majorManageAdd.html',
                        controller: 'majorManageAddCtrl',
                        // 传参
                        resolve: {
                            data: function () {
                                return angular.copy($scope.major);
                            }
                        }
                    });
            }
        }])
    .controller("majorManageEditCtrl", [
        '$http',
        '$scope',
        'qemsAlert',
        'data',
        '$modalInstance',
        function ($http, $scope, qemsAlert, data, $modalInstance) {
            //编辑专业
            $scope.majorParam = data;
            // 确认按钮
            $scope.ok = function () {
                $modalInstance.close();
                $http.post("majorManageEdit", $scope.majorParam).success(function (rs) {
                    qemsAlert.show(rs, "majorManage");
                });
            };
            // 取消按钮
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        }])
    .controller("majorManageAddCtrl", [
        '$http',
        '$scope',
        'qemsAlert',
        'data',
        '$modalInstance',
        function ($http, $scope, qemsAlert, data, $modalInstance) {
            //新增专业
            $scope.major = data;
            // 确认按钮
            $scope.ok = function () {
                $modalInstance.close();
                $http.post("majorManageAdd", $scope.major).success(function (rs) {
                    qemsAlert.show(rs, "majorManage");
                });
            }
            // 取消按钮
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        }])
    .controller('classManageCtrl', [
        '$scope',
        '$http',
        '$state',
        'userService',
        function ($scope, $http, $state,userService) {
            //班级列表(只显示学院和专业都启用的情况）
            $scope.classManage={};
            //配置分页基本参数
            $scope.paginationConf = {
                currentPage: 1,
                itemsPerPage: 10,
                perPageOptions: [5, 10, 20]
            };
            $scope.search = function () {
                $scope.postData = {
                    currentPage: $scope.paginationConf.currentPage,
                    pageSize: $scope.paginationConf.itemsPerPage,
                    classManage: $scope.classManage
                };
                $http.post('findAllClassByMultiConditionAndPage', $scope.postData).success(function (rs) {
                    $scope.paginationConf.totalItems = rs.pagersInfo.totalElements;
                    $scope.classManages = rs.dataList;
                });
            };
            //清空查询条件
            $scope.reset = function () {
                $scope.classManage = {};
            }

            //启用停用切换
            $scope.changeState = function (state, id) {
                $scope.class = {};
                $scope.class.classState = state;
                $scope.class.classId = id;
                $http.post("updateClassState", $scope.class).success(function () {
                    $state.go('classManage', {}, {reload: true});
                })
            }
            //查询当前管理员所在学院状态以做权限控制
            userService.user().then(function (res) {
                $scope.user = res.data;
            });
            $http.post('showInstituteDetails', $scope.user.instituteId).success(function (response) {
                $scope.instituteInfo = response;
            });
            $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.search);

        }])
    .controller('classManageInfoCtrl', [
        '$scope',
        '$http',
        '$stateParams',
        function ($scope, $http, $stateParams) {
            //班级详情
            $scope.classId = $stateParams.classId;
            $http.post('showClassDetails', $scope.classId).success(function (response) {
                $scope.classInfo = response;
            });
        }])
    .controller('classManageEditCtrl', [
        '$scope',
        '$http',
        '$stateParams',
        'qemsAlert',
        function ($scope, $http, $stateParams, qemsAlert) {
            //班级编辑
            $scope.classId = $stateParams.classId;
            //动态生成年级
            $scope.gradeManages = [];
            $scope.currentYear = (new Date()).getFullYear();
            for (var i = 0; i < 5; i++) {
                $scope.gradeManages.push({grade: $scope.currentYear - i, gradeName: ($scope.currentYear - i) + "级"});
            }
            //学院查询
            $http.post("findAllInstitute").success(function (rs) {
                $scope.instituteManages = rs;
            });
            $http.post('showClassDetails', $scope.classId).success(function (response) {
                $scope.classInfo = response.classManage;
                //查询某学院下的所有专业
                $http.post("enabledMajorManage/" + $scope.classInfo.instituteId, {}).success(function (rs) {
                    $scope.majorManages = rs;
                });
            });

            //查询某学院下的所有专业
            $scope.changeInstitute=function(instituteId){
                $scope.classInfo.majorId=null;
                $http.post("majorManage/" + instituteId, {}).success(function (rs) {
                    $scope.majorManages = rs;
                });
            }
            $scope.classSubmit = function () {
                for(var i=0;i<$scope.majorManages.length;i++){
                    if($scope.majorManages[i].majorId==$scope.classInfo.majorId){
                        $scope.majorName=$scope.majorManages[i].majorName;
                    }
                }
                //班级全称组合
                $scope.classInfo.classFullName=$scope.classInfo.grade+"级"+$scope.majorName+$scope.classInfo.classNumber+"班";
                $scope.class = {};
                $scope.class = $scope.classInfo;
                $scope.class.classId = $scope.classId;
                $http.post("updateClass", $scope.class).success(function (response) {
                    qemsAlert.show(response, "classManage");
                });
            };
        }])
    .controller('classManageAddCtrl', [
        '$scope',
        '$http',
        'qemsAlert',
        function ($scope, $http, qemsAlert) {
            //动态生成年级
            $scope.gradeManages = [];
            $scope.currentYear = (new Date()).getFullYear();
            for (var i = 0; i < 5; i++) {
                $scope.gradeManages.push({grade: $scope.currentYear - i, gradeName: ($scope.currentYear - i) + "级"});
            }
            //启用状态学院查询
            $http.post("findAllInstitute").success(function (rs) {
                $scope.instituteManages = rs;
            });
            //查询启用状态学院下的所有启用状态专业
            $scope.changeInstitute=function(instituteId){
                $http.post("enabledMajorManage/" + instituteId, {}).success(function (rs) {
                    $scope.majorManages = rs;
                });
            }
            $scope.classInfo = {};
            $scope.classInfo.classState = "启用";
            //班级添加
            $scope.classSubmit = function () {
                $scope.class = {};
                for(var i=0;i<$scope.majorManages.length;i++){
                    if($scope.majorManages[i].majorId==$scope.classInfo.majorId){
                        $scope.majorName=$scope.majorManages[i].majorName;
                    }
                }
                //班级全称组合
                $scope.classInfo.classFullName=$scope.classInfo.grade+"级"+$scope.majorName+$scope.classInfo.classNumber+"班";
                $scope.class = $scope.classInfo;
                $http.post('createClass', $scope.class).success(function (response) {
                    qemsAlert.show(response, "classManage");
                });
            };
        }])
    .controller('studentManageCtrl', [
        '$scope',
        '$http',
        '$state',
        'userService',
        function ($scope, $http, $state, userService) {
            //查询当前管理员所在学院状态以做权限控制
            userService.user().then(function (res) {
                $scope.user = res.data;
            });
            $http.post('showInstituteDetails', $scope.user.instituteId).success(function (response) {
                $scope.instituteInfo = response;
            });
            //动态生成年级
            $scope.gradeManages = [];
            $scope.currentYear = (new Date()).getFullYear();
            for (var i = 0; i < 5; i++) {
                $scope.gradeManages.push({grade: $scope.currentYear - i, gradeName: ($scope.currentYear - i) + "级"});
            }
            //查询该学院下的所有启用专业
            $http.post("enabledMajorManage/" + $scope.user.instituteId, {}).success(function (rs) {
                    $scope.majorManages = rs;
                    //查询该专业下的所有班级
                    $scope.changeMajor=function(majorId){
                        $http.post("findAllClassByMajorId",majorId).success(function(res){
                            $scope.classManages=res;
                            //查询该专业该年级下的所有班级
                            $scope.changeGrade=function(grade){
                                $scope.selectedGrade=[];
                                if(grade!=null&&grade!=""){
                                    $scope.selectedGrade=grade;
                                }else{
                                    for(var j=0;j<5;j++){
                                        $scope.selectedGrade.push($scope.currentYear - i);
                                    }
                                }
                            }
                        })
                    }
                });
            //学生列表
            $scope.studentManage = {};
            //配置分页基本参数
            $scope.paginationConf = {
                currentPage: 1,
                itemsPerPage: 10,
                perPageOptions: [5, 10, 20]
            };
            //只能查询自己学院的学生
            $scope.studentManage.instituteId = $scope.user.instituteId;
            $scope.search = function () {
                $scope.postData = {
                    currentPage: $scope.paginationConf.currentPage,
                    pageSize: $scope.paginationConf.itemsPerPage,
                    student: $scope.studentManage,
                };
                $http.post('findAllStudentByMultiConditionAndPage', $scope.postData).success(function (rs) {
                    $scope.paginationConf.totalItems = rs.pagersInfo.totalElements;
                    $scope.studentManages = rs.dataList;
                });
            };
            //清空查询条件
            $scope.reset = function () {
                $scope.studentManage = {};
                $scope.studentManage.instituteId = $scope.user.instituteId;
                $scope.majorManages={};
                $scope.classManages={};
            }

            $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.search);
        }])
    .controller('studentManageInfoCtrl', [
        '$scope',
        '$http',
        '$stateParams',
        function ($scope, $http, $stateParams) {
            //学生详情
            $scope.studentId = $stateParams.studentId;
            $http.post('showStudentDetails', $scope.studentId).success(function (response) {
                $scope.studentInfo = response;
            });
        }])
    .controller('studentManageEditCtrl', [
        '$scope',
        '$http',
        '$stateParams',
        'qemsAlert',
        function ($scope, $http, $stateParams, qemsAlert) {
            //学生编辑
            $scope.studentId = $stateParams.studentId;
            //动态生成年级
            $scope.gradeManages = [];
            $scope.currentYear = (new Date()).getFullYear();
            for (var i = 0; i < 5; i++) {
                $scope.gradeManages.push({grade: $scope.currentYear - i, gradeName: ($scope.currentYear - i) + "级"});
            }
            //启用学院查询
            $http.post("findAllInstitute").success(function (rs) {
                $scope.instituteManages = rs;
            });
            $http.post('showStudentDetails', $scope.studentId).success(function (response) {
                $scope.studentInfo = response.student;
                //查询某学院下的所有专业
                $http.post("majorManage/" + $scope.studentInfo.instituteId, {}).success(function (rs) {
                    $scope.majorManages = rs;
                });
                //查询某专业下的所有班级
                $http.post("findAllClassByMajorId",$scope.studentInfo.majorId).success(function(res){
                    $scope.classManages=res;
                })
            });
            //查询某学院下的所有启用专业
            $scope.changeInstitute = function (instituteId) {
                if(instituteId!=null) {
                    $http.post("enabledMajorManage/" + instituteId, {}).success(function (rs) {
                        $scope.majorManages = rs;
                        //查询该专业下的所有班级
                        $scope.changeMajor = function (majorId) {
                            if(majorId!=null) {
                                $http.post("findAllClassByMajorId", majorId).success(function (res) {
                                    $scope.classManages = res;
                                    //查询该专业该年级下的所有班级
                                    $scope.changeGrade = function (grade) {
                                        $scope.selectedGrade = [];
                                        if (grade != null && grade != "") {
                                            $scope.selectedGrade = grade;
                                        } else {
                                            for (var j = 0; j < 5; j++) {
                                                $scope.selectedGrade.push($scope.currentYear - i);
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
            $scope.studentSubmit = function () {
                $scope.student = $scope.studentInfo;
                $scope.student.studentId = $scope.studentId;
                $http.post("updateStudent", $scope.student).success(function (response) {
                    qemsAlert.show(response, "studentManage");
                });
            };
        }])
    .controller('studentManageAddCtrl', [
        '$scope',
        '$http',
        'qemsAlert',
        function ($scope, $http, qemsAlert) {
            //动态生成年级
            $scope.gradeManages = [];
            $scope.currentYear = (new Date()).getFullYear();
            for (var i = 0; i < 5; i++) {
                $scope.gradeManages.push({grade: $scope.currentYear - i, gradeName: ($scope.currentYear - i) + "级"});
            }
            //启用学院查询
            $http.post("findAllInstitute").success(function (rs) {
                $scope.instituteManages = rs;
            });
            //查询某学院下的所有启用专业
            $scope.changeInstitute = function (instituteId) {
                if(instituteId!=null) {
                    $http.post("enabledMajorManage/" + instituteId, {}).success(function (rs) {
                        $scope.majorManages = rs;
                        //查询该专业下的所有班级
                            $scope.changeMajor = function (majorId) {
                                if (majorId != null) {
                                $http.post("findAllClassByMajorId", majorId).success(function (res) {
                                    $scope.classManages = res;
                                    //查询该专业该年级下的所有班级
                                    $scope.changeGrade = function (grade) {
                                        $scope.selectedGrade = [];
                                        if (grade != null && grade != "") {
                                            $scope.selectedGrade = grade;
                                        } else {
                                            for (var j = 0; j < 5; j++) {
                                                $scope.selectedGrade.push($scope.currentYear - i);
                                            }
                                        }
                                    }
                                })
                            }
                        }
                    });
                }
            }
            //新增性别默认为男
            $scope.studentInfo = {};
            $scope.studentInfo.gender = 0;
            //学生添加
            $scope.studentSubmit = function () {
                $scope.student = {};
                $scope.student = $scope.studentInfo;
                $http.post('createStudent', $scope.student).success(function (response) {
                    qemsAlert.show(response, "studentManage");
                });
            };
        }])
    .controller('mainRuleManageCtrl', [
        '$scope',
        '$http',
        '$state',
        'userService',
        function ($scope, $http, $state, userService) {
            //查询当前管理员所在学院状态以做权限控制
            userService.user().then(function (res) {
                $scope.user = res.data;
            });
            $http.post('showInstituteDetails', $scope.user.instituteId).success(function (response) {
                $scope.instituteInfo = response;
            });
            //细则大类列表
            $scope.mainRuleManage = {};
            //只能查询自己学院的细则大类
            $scope.mainRuleManage.instituteId = $scope.user.instituteId;
            var search = function () {
                var postData = {
                    currentPage: $scope.paginationConf.currentPage,
                    pageSize: $scope.paginationConf.itemsPerPage,
                    mainRule: $scope.mainRuleManage
                };
                $http.post('findAllMainRuleByMultiConditionAndPage', postData).success(function (response) {
                    $scope.paginationConf.totalItems = response.pagersInfo.totalElements;
                    $scope.mainRuleManages = response.dataList;
                });
            };
            $scope.search = search;
            //学院查询
            $http.post("findAllInstitute").success(function (rs) {
                $scope.instituteManages = rs;
            });
            //配置分页基本参数
            $scope.paginationConf = {
                currentPage: 1,
                itemsPerPage: 10,
                perPageOptions: [5, 10, 20]
            };
            $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', search);
            //清空查询条件
            $scope.reset = function () {
                $scope.mainRuleManage = {};
                $scope.mainRuleManage.instituteId = $scope.user.instituteId;
            }
            //启用停用切换
            $scope.changeState = function (state, id) {
                $scope.mainRule = {};
                $scope.mainRule.ruleState = state;
                $scope.mainRule.ruleId = id;
                $http.post("updateMainRuleState", $scope.mainRule).success(function () {
                    $state.go('mainRuleManage', {}, {reload: true});
                })
            }

        }])
    .controller('mainRuleManageInfoCtrl', [
        '$scope',
        '$http',
        '$stateParams',
        function ($scope, $http, $stateParams) {
            //细则大类详情
            $scope.ruleId = $stateParams.ruleId;
            $http.post('showMainRuleDetails', $scope.ruleId).success(function (response) {
                $scope.ruleInfo = response;
            });
        }])
    .controller('mainRuleManageEditCtrl', [
        '$scope',
        '$http',
        '$stateParams',
        '$log',
        'qemsAlert',
        function ($scope, $http, $stateParams, $log, qemsAlert) {
            //细则大类编辑
            //学院查询
            $http.post("findAllInstitute").success(function (rs) {
                $scope.instituteManages = rs;
            });
            $scope.ruleId = $stateParams.ruleId;
            $http.post('showMainRuleDetails', $scope.ruleId).success(function (response) {
                $scope.ruleInfo = response;
            });
            $scope.mainRuleSubmit = function () {
                $scope.rule = {};
                $scope.rule = $scope.ruleInfo.mainRule;
                $scope.rule.ruleId = $scope.ruleId;
                $http.post("updateMainRule", $scope.rule).success(function (response) {
                    qemsAlert.show(response, "mainRuleManage");
                });
            };
        }])
    .controller('mainRuleManageAddCtrl', [
        '$scope',
        '$http',
        'qemsAlert',
        function ($scope, $http, qemsAlert) {
            //细则大类添加
            //学院查询
            $http.post("findAllInstitute").success(function (rs) {
                $scope.instituteManages = rs;
            });
            $scope.ruleInfo = {};
            $scope.ruleInfo.ruleState = '启用';
            //提交
            $scope.mainRuleSubmit = function () {
                $scope.rule = {};
                $scope.rule = $scope.ruleInfo;
                $http.post('createMainRule', $scope.rule).success(function (response) {
                    qemsAlert.show(response, "mainRuleManage");
                });
            };
        }]);
