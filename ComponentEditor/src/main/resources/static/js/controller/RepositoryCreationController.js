
ComponentApp.controller('RepositoryCreationController',['$scope','$location','$log','ComponentRepositoryService',function($scope,$location,$log,ComponentRepositoryService){
		$scope.repoStorage = ComponentRepositoryService;
		
    	$scope.removeRepository = function(x){
    		$scope.repoStorage.deleteRepository(x);
    	}
    	
    	$scope.editRepository = function(x){
    		$scope.repoStorage.editRepository(x);
    		$location.path('/');
    	}
    	
    	$scope.goToSingleRepositoryView = function(){
        	$location.path('/');
    	}

    	//-----------------------test area ---------------------
}]
);

