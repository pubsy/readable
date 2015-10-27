'use strict';

angular.module('myApp.root', [])

.directive('rootDirective', function(){
  return {
    restrict: 'E',
    templateUrl: 'app/root/root.html'
  }
})
