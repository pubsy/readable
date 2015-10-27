'use strict';

angular.module('myApp.users', [])

.directive('usersDirective', function(){
  return {
    restrict: 'E',
    templateUrl: 'app/users/users.html'
  }
})
