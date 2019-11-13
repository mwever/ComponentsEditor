describe('ComponentCreationController', function() {
  beforeEach(module('app'));

  var $controller, $rootScope;

  beforeEach(inject(function(_$controller_, _$rootScope_){
    // The injector unwraps the underscores (_) from around the parameter names when matching
    $controller = _$controller_;
    $rootScope = _$rootScope_;
  }));

  describe('$scope.isNumber', function() {
    it('Test if the DefaultDomain of a prameter is of type NumericParameterDomain', function() {
      var $scope = $rootScope.$new();
      var controller = $controller('ComponentCreationController', { $scope: $scope });
      $scope.parameterDummy = new Parameter("dummy",new NumericParameterDomain(1,5,true,2));
      expect($scope.isNumber()).toEqual(true);
    });
  });
});