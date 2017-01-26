var app = angular.module('MinVakt', ['ngMaterial', 'ngRoute']);

app.config(function($mdThemingProvider, $routeProvider, $locationProvider) {
    $mdThemingProvider.theme('default').primaryPalette('blue');
    $routeProvider
    .when("/", {
        templateUrl : "partials/main.html"
    }).when("/ansatt", {
        templateUrl:  "partials/employees.html"
    }).when("/innstillinger", {
        templateUrl: "partials/settings.html"
    }).when("/innstillinger/password", {
        templateUrl: "partials/settings_password.html"
    }).when("/innstillinger/email", {
        templateUrl: "partials/settings_email.html"
    }).when("/innstillinger/tlf", {
        templateUrl: "partials/settings_phone.html"
    }).when("/skift", {
        templateUrl: "partials/shiftlist.html"
    }).when("/kontrollpanel", {
        templateUrl: "partials/controlpanel.html"
    }).when("/timelister_current", {
        templateUrl: "partials/timelists_current.html"
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
                    url: '#/'
                }, {
                    name: 'Ansatte',
                    icon: 'people',
                    link: 'Ansatte',
                    url: '#ansatt'
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
});

app.controller('TimelistsCtrl', function($scope, $routeParams){
    $scope.id = $routeParams.id;
});
