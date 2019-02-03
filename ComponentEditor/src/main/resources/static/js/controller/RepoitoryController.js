ComponentApp.controller('RepoitoryController',['$scope','$location','$log','ComponentRepositoryService','ComponentServerService',function($scope,$location,$log,ComponentRepositoryService,ComponentServerService){
		$scope.componentsStorage = ComponentRepositoryService;
		
		
		$scope.goToComponentView=function(){
        	$location.path('/Comp');
        	$scope.componentsStorage.addnewComponent();
    	}
    	
    	$scope.removeComponent = function(x){
    		$scope.componentsStorage.deleteComponent(x);
    	}
    	
    	//-----------------------test area ---------------------
    	
    	
}]
);

