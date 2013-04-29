
/**
 * Declare MainCtrl, this controller does a GET on "/hello" and put the result in scope.
 */
function MainCtrl($scope, $http, ViewServices) {
    $scope.views=ViewServices.all();
}

//MainCtrl.$inject = [ '$scope', '$http' ];

function AdminCtrl($scope, $http, ViewServices) {

    $scope.views=ViewServices.all();

    $http.get("/projects").success(function(data) {
        $scope.projects = data;
    });

    $scope.addProjectKeyToView = function(view,key) {
       //$scope.debug=view;
        if(view.projectKeys == undefined)
        {
            view.projectKeys=[];
        }

        view.projectKeys.push(key);
        ViewServices.update(view);
    };

    $scope.addView = function(name) {
        var view=new Object();
        view.name=name;
        $scope.views.push(view);
        ViewServices.add(view);
    };

    $scope.removeView = function(view) {
        $scope.views.splice(view);
        ViewServices.delete(view);
    };

}

//AdminCtrl.$inject = [ '$scope', '$http','ViewServices' ];


/**
 * Declare the routes.
 * Route /main (#/main in browser) use the controller MainCtrl with template main.html
 */
var app =angular.module('simpleSonarReport', ['ngResource']);

app.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/main', {templateUrl:'partial/main.html', controller:MainCtrl});
    $routeProvider.when('/admin', {templateUrl:'partial/admin.html', controller:AdminCtrl});
    $routeProvider.otherwise({redirectTo: '/main'});
}]);


app.factory("ViewServices", function($resource)
{
    var out;
    out= $resource('/view/:id',
        {
            //Default parameters

        },
        {
            //Actions
            all: {
                method: 'GET',
                isArray: true
            }
            ,
            one: {
                method: 'GET',
                isArray: false
            },
            add: {
                method: 'POST'
            },
            update: {
                method: 'PUT'
                ,params: {id: 0}
            },
            delete: {
                method: 'DELETE'
                ,params: {id: 0}
            }
        });


    return out;
});
