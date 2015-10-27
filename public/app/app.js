'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'myApp.root',
  'myApp.books',
  'myApp.book',
  'myApp.users',
  'myApp.navigation',
  'myApp.version',
  'hrCore',
  'hrSiren'
])

.directive('parentDirective', function(Resource, $compile){
  return {
    restrict: 'E',
    controller: function ($scope, $element, $attrs) {

      $scope.loadResource = function(url){
        Resource.loadData(url).then(function(response){
          var htm = '';
          $scope.data = response.data;

          switch (response.data.class) {
            case "RootResource":
              htm = '<root-directive></root-directive>';
              break;
            case "BooksListResource":
              htm = '<books-directive></books-directive>';
              break;
            case "UsersListResource":
              htm = '<users-directive></users-directive>';
              break;
            default:
              console.log("Sorry, we are out of .");
          }
          var compiled = $compile(htm)($scope);
          $element.html(compiled);
        });
      };

      $scope.loadResourceWithQuery = function(url, fields){
        $scope.loadResource(url + '?' + fields[0].name + '=' + $scope[fields[0].name]);
      };

      $scope.loadResource('/');
    }
  }
})

.factory('Resource', function($http){
  var Resource = {};

  Resource.loadData = function(url){
    return $http.get(url, {headers: { 'Accept': 'application/vnd.siren+json' }});
  }

  return Resource;
});
