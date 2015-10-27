'use strict';

angular.module('myApp.book', [])

.directive('book', function(){
  return {
    restrict: 'E',
    templateUrl: 'app/book/book.html'
  }
})
