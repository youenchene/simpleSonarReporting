
/**
 * Declare MainCtrl, this controller does a GET on "/hello" and put the result in scope.
 */
function MainCtrl($scope, $http, ViewServices) {

    $http.get("/viewWithDetails").success(function(data) {
        $scope.viewsDetails = data;
    });

}


/**
 * Declare MainCtrl, this controller does a GET on "/hello" and put the result in scope.
 */
function DashCtrl($scope, $http, $cookieStore, ViewServices) {

    $http.get("/view").success(function(data) {
        $scope.views = data;
    });

    $scope.showDash = function() {
        $scope.viewname= $scope.selectedView.name;
        $cookieStore.put('dash_id',$scope.selectedView.id);
        refreshDash($scope.selectedView.id);
    };

    function refreshDash(vid)
    {
        $http.get("/viewWithDetails/"+vid).success(function(data) {
            $scope.viewname = data.viewName;
            $scope.viewlinecoverage = data.lineCoverage;
            $scope.viewLineCoverageTrend = data.lineCoverageTrend;
        });
    }

    var dashid=$cookieStore.get('dash_id');
    if (dashid != undefined)
    {
        refreshDash(dashid);
    }
}


//MainCtrl.$inject = [ '$scope', '$http' ];

function AdminCtrl($scope, $http, ViewServices) {

    Array.prototype.remove = function() {
        var what, a = arguments, L = a.length, ax;
        while (L && this.length) {
            what = a[--L];
            while ((ax = this.indexOf(what)) !== -1) {
                this.splice(ax, 1);
            }
        }
        return this;
    };
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

        //TODO to remove
        $scope.views=ViewServices.all();
    };

    $scope.removeView = function(view) {

        $scope.views.remove(view);
        ViewServices.delete(view);
        //TODO to remove
        $scope.views=ViewServices.all();
    };

}

//AdminCtrl.$inject = [ '$scope', '$http','ViewServices' ];


/**
 * Declare the routes.
 * Route /main (#/main in browser) use the controller MainCtrl with template main.html
 */
var app =angular.module('simpleSonarReport', ['ngResource','ngCookies']);

app.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/main', {templateUrl:'partial/main.html', controller:MainCtrl});
    $routeProvider.when('/dash', {templateUrl:'partial/dash.html', controller:DashCtrl});
    $routeProvider.when('/admin', {templateUrl:'partial/admin.html', controller:AdminCtrl});
    $routeProvider.otherwise({redirectTo: '/main'});
}]);


app.factory("ViewServices", function($resource)
{
    var out;
    out= $resource('/view/:id',
        {

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
                isArray: false,
                params: {id: '@id'}
            },
            add: {
                method: 'POST'
            },
            update: {
                method: 'PUT'
                ,params: {id: '@id'}
            },
            delete: {
                method: 'DELETE'
                ,params: {id: '@id'}
            }
        });


    return out;
});
