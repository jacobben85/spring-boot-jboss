var search = angular.module('search', []);

searchResults = function($scope, $http) {
    $scope.events = [];
    $scope.count = 0;
    $scope.time = "";
    $scope.maxScore = "";

    $scope.search = function() {
        $http.get('/rest/partSearch/?q=' + $scope.searchKey).success(function(data) {
            try {
                if (data.count > 0) {
                    $scope.events = data.results;
                    $scope.count = data.count;
                    $scope.time = data.time;
                    $scope.maxScore = data.maxScore;
                } else {
                    $scope.events = [];
                    $scope.count = 0;
                    $scope.time = "0ms";
                    $scope.maxScore = "";
                }
            } catch(err) {
                console.log(err);
            }
        })
    }
};
search.controller("searchResults", searchResults);
