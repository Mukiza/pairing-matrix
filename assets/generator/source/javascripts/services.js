'use strict';

/* Services */

// Demonstrate how to register services
// In this case it is a simple value service.
angular.module('pairingMatrix.services', ['ngResource']).
  factory('PairingMatrix', function($resource){
    return $resource('pairing-matrix.json', {}, {
      query: {method:'GET', params:{_id:'all'}, isArray:true}
    });
  });

