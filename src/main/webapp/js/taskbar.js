var app = angular.module('MinVakt', ['ngMaterial', 'ngRoute', 'ngMessages']);

app.config(function($mdThemingProvider, $routeProvider, $locationProvider) {
    $mdThemingProvider.theme('default').primaryPalette('blue');
    $routeProvider
    .when("/", {
        templateUrl : "partials/main.html"
    }).when("/ansatt", {
        templateUrl:  "partials/employees.html"
    }).when("/innstillinger", {
        templateUrl: "partials/settings.html",
    }).when("/innstillinger/password", {
        templateUrl: "partials/settings_password.html",
        controller: "SettingsCtrl"
    }).when("/innstillinger/email", {
        templateUrl: "partials/settings_email.html",
        controller: "SettingsCtrl"
    }).when("/innstillinger/tlf", {
        templateUrl: "partials/settings_phone.html",
        controller: "SettingsCtrl"
    }).when("/skift", {
        templateUrl: "partials/shiftlist.html"
    }).when("/opptatt", {
        templateUrl: "partials/busy.html"
    }).when("/vaktbytte", {
        templateUrl: "partials/swaplist.html"
    }).when("/kontrollpanel", {
        templateUrl: "partials/controlpanel.html"
    }).when("/timelister_current", {
        templateUrl: "partials/timelists_current.html",
    }).when("/timelister/:id", {
        templateUrl: "partials/timelists.html",
        controller: 'TimelistsCtrl'
    }).when("/kontrollpanel/godkjennevaktbytte", {
        templateUrl: "partials/confirm_swap.html"
    }).when("/feil", {
        templateUrl: "partials/error.html"
    }).otherwise("/feil"
    );
    $locationProvider.html5Mode(false);
});

app.service('employeeService', function($http){
    return $http.get('api/employees/current')
})

app.service('userService', function($http){
    return $http.get('api/users/current')
});

app.service('menuService', function(){
    return {
        title: 'Trondheim Kommune',
        user: {
            icon: 'account_circle'
        },
        sidenav: {
            sections: [{
                actions: [{
                    name: 'Hjem',
                    icon: 'home',
                    link: 'Hjem',
                    url: '#/'
                }, {
                    name: 'Skiftbok',
                    icon: 'book',
                    link: 'Skiftbok',
                    url: '#skift'
                }, {
                    name: 'Timelister',
                    icon: 'assignment',
                    link: 'Timelister',
                    url: '#timelister_current'
                }, {
                    name: 'Vaktbytte',
                    icon: 'swap_horiz',
                    link: 'Vaktbytte',
                    url: '#vaktbytte'
                }, {
                    name: 'Ansatte',
                    icon: 'people',
                    link: 'Ansatte',
                    url: '#ansatt'
                }, {
                    name: 'Opptatt',
                    icon: 'alarm_off',
                    link: 'Opptatt',
                    url: '#opptatt'
                }]
            }, {
                admin: [{
                    name: 'Kontrollpanel',
                    icon: 'developer_board',
                    link: 'Action 4',
                    url: '#kontrollpanel'
                }]
            }, {
                bottom: [{
                    name: 'Innstillinger',
                    icon: 'settings',
                    link: 'Innstillinger',
                    url: '#innstillinger'
                }, {
                    name: 'Logg ut',
                    icon: 'power_settings_new',
                    link: 'Logg ut',
                    url: 'logout.jsp'
                }]
            }]
        },
        settings: {
            user: [{
                name:'Innstillinger',
                options:[{
                    option: 'Endre Passord',
                    icon: 'lock',
                    toast: 'Endre passord',
                    url: '#innstillinger/password'
                }, {
                    option: 'Endre Telefonnummer',
                    icon: 'phone',
                    toast: '',
                    url: '#innstillinger/tlf'
                }, {
                    option: 'Endre E-post',
                    icon: 'email',
                    toast: '',
                    url: '#innstillinger/email'
                }]
            }],
            admin: [{
                name:'Kontrollpanel',
                options:[{
                    option: 'Lag Vakt',
                    icon: 'add_box',
                    toast: 'Endre passord',
                    url: '#innstillinger/password'
                }, {
                    option: 'Godkjenne Vaktbytte',
                    icon: 'playlist_add_check',
                    toast: 'Endre passord',
                    url: '#kontrollpanel/godkjennevaktbytte'
                }]
            }]
        }

    }
});

app.controller('MinVaktCtrl', function($scope, $mdSidenav, $mdToast, $mdDialog ,$http, $routeParams, employeeService, menuService, userService) {

    employeeService.then(function(result){
        $scope.employee = result.data;
    }, function(result){
        $scope.employee = {
            firstname: 'Ola',
            surname: 'Nordmann'

        };
        console.log('Issue loading employee data from api');
    });

    userService.then(function(result){
        $scope.isAdmin = (result.data.role===0)
    }, function(result){
        console.log('Issue loading user data from api');
        $scope.isAdmin = true;
    });

    $scope.data = menuService;

    $scope.toggleSidenav = function(menu) {
        $mdSidenav(menu).toggle();
    };
    $scope.toast = function(message) {
        var toast = $mdToast.simple().content('Du klikket ' + message).position('bottom right');
        $mdToast.show(toast);
    };
    $scope.toastList = function(message) {
        var toast = $mdToast.simple().content('Du klikket ' + message + ' og har velget ' + $scope.selected.length).position('bottom right');
        $mdToast.show(toast);
    };
    $scope.selected = [];
    $scope.toggle = function(item, list) {
        var idx = list.indexOf(item);
        if (idx > -1) list.splice(idx, 1);
        else list.push(item);
    };

    $scope.alert = function(text) {
        alert(text);
    };

    $scope.log = function(text){
        console.log(text);
    }

});

app.controller('TimelistsCtrl', function($scope, $routeParams){
    $scope.id = $routeParams.id;
});

app.controller('SettingsCtrl', function($scope, $http){

    $scope.regex = ".*[0-9].*";

    $scope.changePassword = function(oldpassword, newpassword, newpasswordrepeat){
        console.log("changePassword():");
        console.log(oldpassword);
        console.log(newpassword);
        console.log(newpasswordrepeat);

        if(newpassword != newpasswordrepeat){
            $scope.passwordsMatch = true;
            return false;
        }

        $http({
            method: 'PUT',
            url: 'api/users/current/updatepassword',
            data: {
                oldpassword: oldpassword,
                newpassword: newpassword
            }
        }).then(function success(response){
            console.log(response);
            if(response == 'false'){
                $scope.oldPasswordIncorrect = true;
            }else{
                $scope.passwordChanged = true;
            }
        }, function error(response){
            console.log("PUT password error");
        });
    }

    $scope.changePhonenumber = function(newnumber){
        $http({
            method: 'GET',
            url: 'api/employees/current'
        }).then(function succcess(employee){
            employee = employee.data;
            employee.phone_number = newnumber;
            console.log(employee);
            $http({
                method: 'PUT',
                url: 'api/employees',
                data: employee
            }).then(function success(){
                console.log("Number change success");
            }, function error(){
                console.log("Number change error");
            });
        }, function error(){
            console.log("GET employee error");
        });
    }

    $scope.changeMail = function(newmail){
        $http({
            method: 'GET',
            url: 'api/employees/current'
        }).then(function succcess(employee){
            employee = employee.data;
            employee.email = newmail;
            console.log(employee);
            $http({
                method: 'PUT',
                url: 'api/employees',
                data: employee
            }).then(function success(){
                console.log("Mail change success");
            }, function error(){
                console.log("Mail change error");
            });
        }, function error(){
            console.log("GET employee error");
        });
    }


});