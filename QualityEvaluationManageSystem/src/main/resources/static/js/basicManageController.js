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
            itemsPerPage: 5,
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
        '$modal',
        '$location',
        '$log',
        function ($scope, $http, $stateParams, $modal, $location, $log, $modalInstance) {
            //学院编辑
            $scope.instituteId = $stateParams.instituteId;
            $http.post('showInstituteDetails', $scope.instituteId).success(function (response) {
                $scope.instituteInfo = response;
            });
            $scope.instituteSubmit = function () {
                $scope.institute = {};
                $scope.institute = $scope.instituteInfo;
                $scope.institute.instituteId = $scope.instituteId;
                $scope.result = {};
                $http.post("updateInstitute", $scope.institute).success(function (response) {
                    $scope.result.title = "提示消息";
                    $scope.result.msg = response;
                    $scope.open('sm');
                    $scope.$modalInstance = undefined;
                });
                // 弹窗
                $scope.open = function (size) {
                    $scope.modalInstance = $modal.open({
                        templateUrl: 'tpls/common/popupMessage.html',
                        controller: ModalInstanceCtrl,
                        size: size,
                        resolve: {
                            requestResults: function () {
                                return $scope.result;
                            }
                        }
                    });
                    // 成功的回调方法 （可带参数）
                    $scope.modalInstance.result.then(function () {
                        // 跳转到列表页面
                        if ($scope.result.msg == "该学院已存在" || $scope.result.msg == "该学院不存在") {

                        } else {
                            $location.path('/instituteManage');
                        }
                        // 失败的回调方法
                    }, function () {
                        $log.info('Modal dismissed at: ' + new Date());
                    });
                };
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
            var ModalInstanceCtrl = function ($scope, $modalInstance,
                                              requestResults) {
                $scope.results = requestResults;
                // 确认按钮（close()可以带参数）
                $scope.ok = function () {
                    $modalInstance.close();
                };
                // 取消按钮
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            };//弹窗结束
        }])
    .controller('instituteManageAddCtrl', [
        '$scope',
        '$http',
        '$modal',
        '$location',
        '$log',
        function ($scope, $http, $modal, $location, $log, $modalInstance) {
            //学院添加
            $scope.manageSubmit = function () {
                $scope.institute = {};
                $scope.instituteInitState = "启用";
                if ($scope.instituteInfo.instituteState == null) {
                    $scope.instituteInfo.instituteState = '启用';
                }
                $scope.institute = $scope.instituteInfo;
                $scope.result = {};
                $http.post('createInstitute', $scope.institute).success(function (response) {
                    $scope.result.title = "提示消息";
                    $scope.result.msg = response;
                    $scope.open('sm');
                    $scope.$modalInstance = undefined;
                });
                // 弹窗
                $scope.open = function (size) {
                    $scope.modalInstance = $modal.open({
                        templateUrl: 'tpls/common/popupMessage.html',
                        controller: ModalInstanceCtrl,
                        size: size,
                        resolve: {
                            requestResults: function () {
                                return $scope.result;
                            }
                        }
                    });
                    // 成功的回调方法 （可带参数）
                    $scope.modalInstance.result.then(function () {
                        // 跳转到列表页面
                        if ($scope.result.msg == "该学院已存在") {

                        } else {
                            $location.path('/instituteManage');
                        }
                        // 失败的回调方法
                    }, function () {
                        $log.info('Modal dismissed at: ' + new Date());
                    });
                };
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
            var ModalInstanceCtrl = function ($scope, $modalInstance,
                                              requestResults) {
                $scope.results = requestResults;
                // 确认按钮（close()可以带参数）
                $scope.ok = function () {
                    $modalInstance.close();
                };
                // 取消按钮
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            };//弹窗结束
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
                $scope.major.majorState = 0;
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
        '$modalInstance',
        '$modal',
        '$state',
        'data',
        function ($http, $scope, $modalInstance, $modal, $state, data) {
            //编辑专业
            $scope.majorParam = data;
            $scope.result = {};
            $scope.ok = function () {
                // 弹窗
                $scope.open = function (size) {
                    $scope.modalInstance = $modal.open({
                        templateUrl: 'tpls/common/popupMessage.html',
                        controller: ModalInstanceCtrl,
                        size: size,
                        resolve: {
                            requestResults: function () {
                                return $scope.result;
                            }
                        }
                    });
                    // 成功的回调方法 （可带参数）
                    $scope.modalInstance.result.then(function () {
                        if ($scope.result.msg == "修改失败,该专业不存在") {
                        } else {
                            $state.go('majorManage', {}, {reload: true});
                        }
                        // 失败的回调方法
                    }, function () {
                        $log.info('Modal dismissed at: ' + new Date());
                    });
                }
                $http.post("majorManageEdit", $scope.majorParam).success(function (rs) {
                    $scope.result.title = "提示消息";
                    $scope.result.msg = rs;
                    $scope.open('sm');
                    $modalInstance.close();
                });
                var ModalInstanceCtrl = function ($scope, $modalInstance,
                                                  requestResults) {
                    $scope.results = requestResults;
                    // 确认按钮（close()可以带参数）
                    $scope.ok = function () {
                        $modalInstance.close();
                    };
                    // 取消按钮
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                };
            }
            // 取消按钮
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        }])
    .controller("majorManageAddCtrl", [
        '$http',
        '$scope',
        '$modalInstance',
        '$modal',
        '$state',
        'data',
        function ($http, $scope, $modalInstance, $modal, $state, data) {
            //新增专业
            $scope.major = data;
            $scope.result = {};
            $scope.ok = function () {
                // 弹窗
                $scope.open = function (size) {
                    $scope.modalInstance = $modal.open({
                        templateUrl: 'tpls/common/popupMessage.html',
                        controller: ModalInstanceCtrl,
                        size: size,
                        resolve: {
                            requestResults: function () {
                                return $scope.result;
                            }
                        }
                    });
                    // 成功的回调方法 （可带参数）
                    $scope.modalInstance.result.then(function () {
                        $state.go('majorManage', {}, {reload: true});
                        // 失败的回调方法
                    }, function () {
                        $log.info('Modal dismissed at: ' + new Date());
                    });
                }
                $http.post("majorManageAdd", $scope.major).success(function (rs) {
                    $scope.result.title = "提示消息";
                    $scope.result.msg = rs;
                    $scope.open('sm');
                    $modalInstance.close();
                });
                var ModalInstanceCtrl = function ($scope, $modalInstance,
                                                  requestResults) {
                    $scope.results = requestResults;
                    // 确认按钮（close()可以带参数）
                    $scope.ok = function () {
                        $modalInstance.close();
                    };
                    // 取消按钮
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                };
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
            //班级列表
            $scope.classManage={};
            //配置分页基本参数
            $scope.paginationConf = {
                currentPage: 1,
                itemsPerPage: 5,
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
        '$modal',
        '$location',
        '$log',
        function ($scope, $http, $stateParams, $modal, $location, $log, $modalInstance) {
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
                $http.post("majorManage/" + $scope.classInfo.instituteId, {}).success(function (rs) {
                    $scope.majorManages = rs;
                });
            });

            $scope.classInitState = "启用";
            //查询某学院下的所有专业
            $scope.changeInstitute=function(instituteId){
                $scope.classInfo.majorId=null;
                $http.post("majorManage/" + instituteId, {}).success(function (rs) {
                    $scope.majorManages = rs;
                });
            }
            $scope.classSubmit = function () {
                if ($scope.classInfo.classState == null) {
                    $scope.classInfo.classState = '启用';
                }
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
                $scope.result = {};
                $http.post("updateClass", $scope.class).success(function (response) {
                    $scope.result.title = "提示消息";
                    $scope.result.msg = response;
                    $scope.open('sm');
                    $scope.$modalInstance = undefined;
                });
                // 弹窗
                $scope.open = function (size) {
                    $scope.modalInstance = $modal.open({
                        templateUrl: 'tpls/common/popupMessage.html',
                        controller: ModalInstanceCtrl,
                        size: size,
                        resolve: {
                            requestResults: function () {
                                return $scope.result;
                            }
                        }
                    });
                    // 成功的回调方法 （可带参数）
                    $scope.modalInstance.result.then(function () {
                        // 跳转到列表页面
                        if ($scope.result.msg == "该班级已存在") {

                        } else {
                            $location.path('/classManage');
                        }
                        // 失败的回调方法
                    }, function () {
                        $log.info('Modal dismissed at: ' + new Date());
                    });
                };
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
            var ModalInstanceCtrl = function ($scope, $modalInstance,
                                              requestResults) {
                $scope.results = requestResults;
                // 确认按钮（close()可以带参数）
                $scope.ok = function () {
                    $modalInstance.close();
                };
                // 取消按钮
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            };//弹窗结束
        }])
    .controller('classManageAddCtrl', [
        '$scope',
        '$http',
        '$modal',
        '$location',
        '$log',
        function ($scope, $http, $modal, $location, $log, $modalInstance) {
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
            //查询某学院下的所有专业
            $scope.changeInstitute=function(instituteId){
                $http.post("majorManage/" + instituteId, {}).success(function (rs) {
                    $scope.majorManages = rs;
                });
            }
            //班级添加
            $scope.classSubmit = function () {
                $scope.class = {};
                $scope.classInitState = "启用";
                if ($scope.classInfo.classState == null) {
                    $scope.classInfo.classState = '启用';
                }
                for(var i=0;i<$scope.majorManages.length;i++){
                    if($scope.majorManages[i].majorId==$scope.classInfo.majorId){
                        $scope.majorName=$scope.majorManages[i].majorName;
                    }
                }
                //班级全称组合
                $scope.classInfo.classFullName=$scope.classInfo.grade+"级"+$scope.majorName+$scope.classInfo.classNumber+"班";
                $scope.class = $scope.classInfo;
                $scope.result = {};
                $http.post('createClass', $scope.class).success(function (response) {
                    $scope.result.title = "提示消息";
                    $scope.result.msg = response;
                    $scope.open('sm');
                    $scope.$modalInstance = undefined;
                });
                // 弹窗
                $scope.open = function (size) {
                    $scope.modalInstance = $modal.open({
                        templateUrl: 'tpls/common/popupMessage.html',
                        controller: ModalInstanceCtrl,
                        size: size,
                        resolve: {
                            requestResults: function () {
                                return $scope.result;
                            }
                        }
                    });
                    // 成功的回调方法 （可带参数）
                    $scope.modalInstance.result.then(function () {
                        // 跳转到列表页面
                        if ($scope.result.msg == "新增成功") {
                            $location.path('/classManage');
                        }
                        // 失败的回调方法
                    }, function () {
                        $log.info('Modal dismissed at: ' + new Date());
                    });
                };
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
            var ModalInstanceCtrl = function ($scope, $modalInstance,
                                              requestResults) {
                $scope.results = requestResults;
                // 确认按钮（close()可以带参数）
                $scope.ok = function () {
                    $modalInstance.close();
                };
                // 取消按钮
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            };//弹窗结束
        }])
    .controller('studentManageCtrl', [
        '$scope',
        '$http',
        '$state',
        'userService',
        function ($scope, $http, $state, userService) {
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
            //查询某学院下的所有专业
            $scope.changeInstitute=function(instituteId){
                $http.post("majorManage/" + instituteId, {}).success(function (rs) {
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
            }
            //学生列表
            $scope.studentManage = {};
            //配置分页基本参数
            $scope.paginationConf = {
                currentPage: 1,
                itemsPerPage: 5,
                perPageOptions: [5, 10, 20]
            };
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
                $scope.majorManages={};
                $scope.classManages={};
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
        '$modal',
        '$location',
        '$log',
        function ($scope, $http, $stateParams, $modal, $location, $log, $modalInstance) {
            //学生编辑
            $scope.studentId = $stateParams.studentId;
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
            $scope.studentInitGender = 0;
            //查询某学院下的所有专业
            $scope.changeInstitute = function (instituteId) {
                if(instituteId!=null) {
                    $http.post("majorManage/" + instituteId, {}).success(function (rs) {
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
                if ($scope.studentInfo.gender == null) {
                    $scope.studentInfo.gender = 0;
                }
                $scope.student = $scope.studentInfo;
                $scope.student.studentId = $scope.studentId;
                $scope.result = {};
                $http.post("updateStudent", $scope.student).success(function (response) {
                    $scope.result.title = "提示消息";
                    $scope.result.msg = response;
                    $scope.open('sm');
                    $scope.$modalInstance = undefined;
                });
                // 弹窗
                $scope.open = function (size) {
                    $scope.modalInstance = $modal.open({
                        templateUrl: 'tpls/common/popupMessage.html',
                        controller: ModalInstanceCtrl,
                        size: size,
                        resolve: {
                            requestResults: function () {
                                return $scope.result;
                            }
                        }
                    });
                    // 成功的回调方法 （可带参数）
                    $scope.modalInstance.result.then(function () {
                        // 跳转到列表页面
                        if ($scope.result.msg == "修改成功") {
                            $location.path('/studentManage');
                        }
                        // 失败的回调方法
                    }, function () {
                        $log.info('Modal dismissed at: ' + new Date());
                    });
                };
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
            var ModalInstanceCtrl = function ($scope, $modalInstance,
                                              requestResults) {
                $scope.results = requestResults;
                // 确认按钮（close()可以带参数）
                $scope.ok = function () {
                    $modalInstance.close();
                };
                // 取消按钮
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            };//弹窗结束
        }])
    .controller('studentManageAddCtrl', [
        '$scope',
        '$http',
        '$modal',
        '$location',
        '$log',
        function ($scope, $http, $modal, $location, $log, $modalInstance) {
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
            //查询某学院下的所有专业
            $scope.changeInstitute = function (instituteId) {
                if(instituteId!=null) {
                    $http.post("majorManage/" + instituteId, {}).success(function (rs) {
                        $scope.majorManages = rs;
                        //查询该专业下的所有班级
                        if(majorId!=null) {
                            $scope.changeMajor = function (majorId) {
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
            //学生添加
            $scope.studentSubmit = function () {
                $scope.studentInitGender = 0;
                if ($scope.studentInfo.gender == null) {
                    $scope.studentInfo.gender = 0;
                }
                $scope.student = {};
                $scope.student = $scope.studentInfo;
                $scope.result = {};
                $http.post('createStudent', $scope.student).success(function (response) {
                    $scope.result.title = "提示消息";
                    $scope.result.msg = response;
                    $scope.open('sm');
                    $scope.$modalInstance = undefined;
                });
                // 弹窗
                $scope.open = function (size) {
                    $scope.modalInstance = $modal.open({
                        templateUrl: 'tpls/common/popupMessage.html',
                        controller: ModalInstanceCtrl,
                        size: size,
                        resolve: {
                            requestResults: function () {
                                return $scope.result;
                            }
                        }
                    });
                    // 成功的回调方法 （可带参数）
                    $scope.modalInstance.result.then(function () {
                        // 跳转到列表页面
                        if ($scope.result.msg == "新增成功") {
                            $location.path('/studentManage');
                        }
                        // 失败的回调方法
                    }, function () {
                        $log.info('Modal dismissed at: ' + new Date());
                    });
                };
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
            var ModalInstanceCtrl = function ($scope, $modalInstance,
                                              requestResults) {
                $scope.results = requestResults;
                // 确认按钮（close()可以带参数）
                $scope.ok = function () {
                    $modalInstance.close();
                };
                // 取消按钮
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            };//弹窗结束
        }])
    .controller('mainRuleManageCtrl', [
        '$scope',
        '$http',
        '$state',
        'userService',
        function ($scope, $http, $state, userService) {
            //细则大类列表
            $scope.mainRuleManage = {};
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
                itemsPerPage: 5,
                perPageOptions: [5, 10, 20]
            };
            $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', search);
            //清空查询条件
            $scope.reset = function () {
                $scope.mainRuleManage = {};
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
            //查询当前管理员所在学院状态以做权限控制
            userService.user().then(function (res) {
                $scope.user = res.data;
            });
            $http.post('showInstituteDetails', $scope.user.instituteId).success(function (response) {
                $scope.instituteInfo = response;
            });
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
        '$modal',
        '$location',
        '$log',
        function ($scope, $http, $stateParams, $modal, $location, $log, $modalInstance) {
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
                $scope.result = {};
                $http.post("updateMainRule", $scope.rule).success(function (response) {
                    $scope.result.title = "提示消息";
                    $scope.result.msg = response;
                    $scope.open('sm');
                    $scope.$modalInstance = undefined;
                });
                // 弹窗
                $scope.open = function (size) {
                    $scope.modalInstance = $modal.open({
                        templateUrl: 'tpls/common/popupMessage.html',
                        controller: ModalInstanceCtrl,
                        size: size,
                        resolve: {
                            requestResults: function () {
                                return $scope.result;
                            }
                        }
                    });
                    // 成功的回调方法 （可带参数）
                    $scope.modalInstance.result.then(function () {
                        // 跳转到列表页面
                        if ($scope.result.msg == "修改成功") {
                            $location.path('/mainRuleManage');
                        }
                        // 失败的回调方法
                    }, function () {
                        $log.info('Modal dismissed at: ' + new Date());
                    });
                };
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
            var ModalInstanceCtrl = function ($scope, $modalInstance,
                                              requestResults) {
                $scope.results = requestResults;
                // 确认按钮（close()可以带参数）
                $scope.ok = function () {
                    $modalInstance.close();
                };
                // 取消按钮
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            };//弹窗结束
        }])
    .controller('mainRuleManageAddCtrl', [
        '$scope',
        '$http',
        '$modal',
        '$location',
        '$log',
        function ($scope, $http, $modal, $location, $log, $modalInstance) {
            //细则大类添加
            //学院查询
            $http.post("findAllInstitute").success(function (rs) {
                $scope.instituteManages = rs;
            });
            //提交
            $scope.mainRuleSubmit = function () {
                $scope.rule = {};
                $scope.ruleInitState = "启用";
                if ($scope.ruleInfo.ruleState == null) {
                    $scope.ruleInfo.ruleState = '启用';
                }
                $scope.rule = $scope.ruleInfo;
                $scope.result = {};
                $http.post('createMainRule', $scope.rule).success(function (response) {
                    $scope.result.title = "提示消息";
                    $scope.result.msg = response;
                    $scope.open('sm');
                    $scope.$modalInstance = undefined;
                });
                // 弹窗
                $scope.open = function (size) {
                    $scope.modalInstance = $modal.open({
                        templateUrl: 'tpls/common/popupMessage.html',
                        controller: ModalInstanceCtrl,
                        size: size,
                        resolve: {
                            requestResults: function () {
                                return $scope.result;
                            }
                        }
                    });
                    // 成功的回调方法 （可带参数）
                    $scope.modalInstance.result.then(function () {
                        // 跳转到列表页面
                        if ($scope.result.msg == "新增成功") {
                            $location.path('/mainRuleManage');
                        }
                        // 失败的回调方法
                    }, function () {
                        $log.info('Modal dismissed at: ' + new Date());
                    });
                };
            };
            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
            var ModalInstanceCtrl = function ($scope, $modalInstance,
                                              requestResults) {
                $scope.results = requestResults;
                // 确认按钮（close()可以带参数）
                $scope.ok = function () {
                    $modalInstance.close();
                };
                // 取消按钮
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            };//弹窗结束
        }]);
