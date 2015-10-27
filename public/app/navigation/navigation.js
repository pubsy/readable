'use strict';

angular.module('myApp.navigation', [])

.directive('navigation', function(){
  return {
    restrict: 'E',
    templateUrl: 'app/navigation/navigation.html'
  }
})
