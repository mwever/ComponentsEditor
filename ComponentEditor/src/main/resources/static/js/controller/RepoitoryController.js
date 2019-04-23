ComponentApp.controller('RepoitoryController',['$scope','$location','$log','ComponentRepositoryService',function($scope,$location,$log,ComponentRepositoryService,ComponentServerService){
		$scope.componentsStorage = ComponentRepositoryService;
		
		
		$scope.goToComponentView=function(){
        	$location.path('/Comp');
        	//$scope.componentsStorage.addnewComponent();
    	}
    	
    	$scope.removeComponent = function(x){
    		$scope.componentsStorage.deleteComponent(x);
    	}
    	
    	
    	$scope.editComponent = function(x){
    		$scope.componentsStorage.editComponent(x);
    		$location.path('/Comp');
    	}
    	//-----------------------test area ---------------------
}]
);

