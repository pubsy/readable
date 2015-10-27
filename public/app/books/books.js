'use strict';

angular.module('myApp.books', [])

.directive('booksDirective', function(){
  return {
    restrict: 'E',
    templateUrl: 'app/books/books.html'
  }
})
