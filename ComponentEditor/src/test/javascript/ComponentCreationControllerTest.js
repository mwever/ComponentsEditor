describe('ComponentCreationController', function(){ //describe your object type
  var myctrl;
  var $rootScope ,$controller;


  beforeEach(module('ngRoute'));
  beforeEach(module('ngFileSaver'));
  beforeEach(module('ngFileUpload'));
  beforeEach(module('ComponentCreationApp')); //load module

  beforeEach(inject(function(_$controller_,_$rootScope_) {

    //$rootScope = $injector.get('$rootScope');
    $controller = _$controller_;
    //$scope = $rootScope.$new();
    $rootScope = _$rootScope_;

    }));

    //beforeEach(inject(function($controller){ //instantiate controller using $controller service
    //  myctrl = $controller('ComponentCreationController');
     //}));

  describe('ComponentCreationController',function(){ //describe your app name
     
      it('Sould return ture if handed over parameter has parameter domain of type NumericParameterDomain', function(){  //write tests
          var $scope = $rootScope.$new();
          var controller = $controller('ComponentCreationController', { $scope: $scope });
          expect($scope.isNumber(new Parameter("test", new NumericParameterDomain(1,5,true,2)))).toBeTruthy(); //pass
      });

      it('Sould return ture if handed over parameter has parameter domain of type CategoricalParameterDomain', function(){  //write tests
        var $scope = $rootScope.$new();
        var controller = $controller('ComponentCreationController', { $scope: $scope });
        expect($scope.isCat(new Parameter("test", new CategoricalParameterDomain(["A","B","C"],"A")))).toBeTruthy(); //pass
    });

    it('Sould return ture if handed over parameter has parameter domain of type BooleanParameterDomain', function(){  //write tests
      var $scope = $rootScope.$new();
      var controller = $controller('ComponentCreationController', { $scope: $scope });
      expect($scope.isBool(new Parameter("test", new BooleanParameterDomain()))).toBeTruthy(); //pass
  });
      
  });
});

// describe('$scope.isNumber', function() {
//   it('Test if the DefaultDomain of a prameter is of type NumericParameterDomain', function() {
//     console.log('Hallo Helena');
//   });

// });