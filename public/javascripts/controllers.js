'use strict';

/* Controllers */

function PairingMatrixController($scope, $routeParams, $http, PairingMatrix) {
  $scope.pairingMatrix = PairingMatrix.get();
  $scope.newPersonName = "";

  var getPairCount = function(p1, p2) {
    if (p1 > p2) {
      var x = p1;
      p1 = p2;
      p2 = x;
    }
    
    p1 = $scope.pairingMatrix.pairCounts[p1];
    if(p1) {
      return p1[p2] || 0;
    } else {
      return 0;
    }
  }

  $scope.addPerson = function() {
    var newPerson = {name: $scope.newPersonName}
    $scope.pairingMatrix.people.push(newPerson);
    // do sortby
    $scope.pairingMatrix.people = _.sortBy($scope.pairingMatrix.people, function(p) {
      return p.name.toLowerCase();
    });
    $http.post('/people', newPerson);
    $scope.newPersonName = "";
  };

  $scope.cell = function(row, col) {
    if(row >= col) {
      return "x";
    } else {
      return getPairCount(row, col);
    }
  }

  $scope.increasePairCount = function(p1, p2) {
    $http.post('/pair-counts', {p1:p1, p2:p2});
    if(!$scope.pairingMatrix.pairCounts[p1]) {
      $scope.pairingMatrix.pairCounts[p1] = {};
    }
    if(!$scope.pairingMatrix.pairCounts[p1][p2]) {
      $scope.pairingMatrix.pairCounts[p1][p2] = 0;
    }
    $scope.pairingMatrix.pairCounts[p1][p2] = $scope.pairingMatrix.pairCounts[p1][p2] + 1;
  }

  $scope.totalPairCount = function(p1) {
    return _.reduce(
      _.map($scope.pairingMatrix.people, function(p){ return p.name }),
      function(total, p2) {
      return total + ((p1 == p2) ? 0 : getPairCount(p1, p2));
    }, 0);
  }

  $scope.uniquePairCount = function(p1) {
    return _.reduce(
      _.map($scope.pairingMatrix.people, function(p){ return p.name }),
      function(total, p2) {
      return total + ((p1 == p2) ? 0 : (getPairCount(p1, p2) && 1));
    }, 0);
  }
}
;
