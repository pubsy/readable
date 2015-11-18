'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'myApp.books',
  'myApp.book',
  'myApp.user',
  'myApp.users',
  'myApp.navigation',
  'myApp.search'
])

.directive('parentDirective', function($http, $compile){
  return {
    restrict: 'E',
    controller: function ($scope, $element, $attrs) {

      $scope.navigation = {};
      $scope.data = {};
      $scope.search;

      $scope.init = function(){
        $scope.loadNavigation(function(){
            $scope.loadResource($scope.getHrefByRel($scope.navigation.links, 'Books'));
        });
      }

      $scope.loadResource = function(url, method, formName){
        $scope.loadNavigation(function(){});
        $scope.load(url, method, formName);
      }

      $scope.loadNavigation = function(successCallback){
        $http({
          method: 'GET',
          url: '/',
          headers: {
            'Accept': 'application/vnd.siren+json',
            'Content-Type' : 'application/x-www-form-urlencoded'
          }
        }).then(function(response) {
          $scope.navigation = response.data;
          successCallback();
        }, function errorCallback(response) {
          alert('Error: ' + response);
        });
      }

      $scope.load= function(url, method, formName){

        $scope.search = {};

        if(typeof method == "undefined") {method = 'GET';}
        var data = {};
        if(typeof formName !== "undefined") {
          if(method == 'GET'){
            url += '?' + $('[name="'+ formName +'"]').serialize();
          } else {
            data = $('[name="'+ formName +'"]').serialize();
          }
        }

        $http({
          method: method,
          url: url,
          data: data,
          headers: {
            'Accept': 'application/vnd.siren+json',
            'Content-Type' : 'application/x-www-form-urlencoded'
          }
        }).then(function(response) {
          var htm = '';
          $scope.data = response.data;
          $scope.search = $scope.getActionByRel('search');

          switch (response.data.class) {
            case "NavigationResource":
              break;
            case "BooksListResource":
              htm = '<books-directive></books-directive>';
              break;
            case "BookResource":
              htm = '<book-directive></book-directive>';
              break;
            case "UsersListResource":
              htm = '<users-directive></users-directive>';
              break;
            case "UserResource":
              htm = '<user-directive></user-directive>';
              break;
            default:
              console.log("Sorry, we are out of .");
          }
          var compiled = $compile(htm)($scope);
          $element.html(compiled);
        }, function errorCallback(response) {
            alert('Error: ' + response);
        });
      };

      $scope.getHrefByRel = function(links, rel){
        for(var link of links){
          for(var currRel of link.rel){
            if(currRel == rel){
              return link.href;
            }
          }
        }
        return '';
      }

      $scope.getActionByRel = function(rel){
        if($scope.data.actions == null){
          return null;
        }
        for(var action of $scope.data.actions){
          for(var currRel of action.rel){
            if(currRel == rel){
              return action;
            }
          }
        }
        return null;
      }

      $scope.isActionPresent = function(rel){
        return $scope.getActionByRel(actions, rel) == null;
      }

      $scope.init();
    }
  }
});
