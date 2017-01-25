var app = angular.module('MinVakt', ['ngMaterial', 'ngRoute']);

app.config(function($mdThemingProvider, $routeProvider, $locationProvider) {
    $mdThemingProvider.theme('default').primaryPalette('blue');
    $routeProvider
    .when("/", {
        templateUrl : "partials/main.html"
    }).when("/test", {
        templateUrl: "partials/test.html"
    }).when("/ansatt", {
        templateUrl:  "partials/employees.html"
    }).when("/innstillinger", {
        templateUrl: "partials/settings.html"
    }).when("/innstillinger/password", {
        templateUrl: "partials/settings_password.html"
    }).when("/skift", {
        templateUrl: "partials/shiftlist.html"
    }).otherwise(
        "/"
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
                    url: '#test'
                }, {
                    name: 'Vaktbytte',
                    icon: 'swap_horiz',
                    link: 'Vaktbytte'
                }]
            }, {
                admin: [{
                    name: 'Kontrollpanel',
                    icon: 'developer_board',
                    link: 'Action 4'
                }, {
                    name: 'Ansatte',
                    icon: 'people',
                    link: 'Ansatte',
                    url: '#ansatt'
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
        content: {
            lists: [{
                name: 'List 1',
                menu: {
                    name: 'Menu 1',
                    icon: 'settings',
                    width: '4',
                    actions: [{
                        name: 'Action 1',
                        message: 'Action 1',
                        completed: true,
                        error: true
                    }]
                },
                items: [{
                    name: 'Item 1',
                    description: 'Description 1',
                    link: 'Item 1'
                }, {
                    name: 'Item 2',
                    description: 'Description 2',
                    link: 'Item 2'
                }, {
                    name: 'Item 2',
                    description: 'Description 2',
                    link: 'Item 2'
                }, {
                    name: 'Item 2',
                    description: 'Description 2',
                    link: 'Item 2'
                }, {
                    name: 'Item 2',
                    description: 'Description 2',
                    link: 'Item 2'
                }, {
                    name: 'Item 2',
                    description: 'Description 2',
                    link: 'Item 2'
                }, {
                    name: 'Item 2',
                    description: 'Description 2',
                    link: 'Item 2'
                }, {
                    name: 'Item 2',
                    description: 'Description 2',
                    link: 'Item 2'
                }, {
                    name: 'Item 2',
                    description: 'Description 2',
                    link: 'Item 2'
                }, {
                    name: 'Item 3',
                    description: 'Description 3',
                    link: 'Item 3'
                }]
            }]
        },
        settings: {
            options:{
                option: 'Endre passord',
                icon: 'face',
                toast: '',
                url: ''
            }
        }

    }
});

app.controller('MinVaktCtrl', function($scope, $mdSidenav, $mdToast, $http, employeeService, menuService, userService) {

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
        var toast = $mdToast.simple().content('You clicked ' + message).position('bottom right');
        $mdToast.show(toast);
    };
    $scope.toastList = function(message) {
        var toast = $mdToast.simple().content('You clicked ' + message + ' having selected ' + $scope.selected.length + ' item(s)').position('bottom right');
        $mdToast.show(toast);
    };
    $scope.selected = [];
    $scope.toggle = function(item, list) {
        var idx = list.indexOf(item);
        if (idx > -1) list.splice(idx, 1);
        else list.push(item);
    };
});