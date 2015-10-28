'use strict';

angular.module('myApp.book', [])

.directive('bookDirective', function(){
  return {
    restrict: 'E',
    templateUrl: 'app/book/book.html'
  }
})
