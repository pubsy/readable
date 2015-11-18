'use strict';

angular.module('myApp.search', [])

.directive('search', function(){
  return {
    restrict: 'E',
    templateUrl: 'app/search/search.html'
  }
})
