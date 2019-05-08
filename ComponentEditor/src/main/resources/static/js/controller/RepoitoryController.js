class Repository{
        	constructor(name,components){
        		this.name = name;
        		this.components = components;
        	}
        }


ComponentApp.controller('RepoitoryController',['$scope','$location','$log','$http','ComponentRepositoryService',function($scope,$location,$log,$http,ComponentRepositoryService){
		
	$scope.componentsStorage = ComponentRepositoryService;
		
		
		$scope.name = ""
		$scope.components= [] 
		
		$scope.goToComponentView=function(){
        	$location.path('/Comp');
        	//$scope.componentsStorage.addnewComponent();
    	}
		
		$scope.goToRepositoryView = function(){
			$scope.addRepository();
			//$location.path('/repos');
        	//$scope.componentsStorage.addnewComponent();
    	}
    	
    	$scope.removeComponent = function(x){
    		$scope.componentsStorage.deleteComponent(x);
    	}
    	
    	
    	$scope.editComponent = function(x){
    		$scope.componentsStorage.editComponent(x);
    		$location.path('/Comp');
    	}
    	
    	
    	$scope.addRepository=function(){
    		
        	var repo = new Repository($scope.name,$scope.components); 
        	
    		var jsonString =  angular.toJson(repo, true);
    		
    		if($scope.componentsStorage.checkRepository(repo)){
    			console.log("POST");
    			$http({
    				method: 'POST',
    				url: 'http://localhost:8080/repos',
    				data: jsonString
    			}).then(
    					function(response){
    						console.log("worked");
    					},
    					function(response){
    						console.log(response);
    						console.log("Error");
    					}		
    	    	);
    			
    		}
    		else{
    			
    			console.log("PUT");
    			$http({
    				method: 'PUT',
    				url: 'http://localhost:8080/repos',
    				data: jsonString 
    			}).then(
    					function(response){
    						console.log("worked");
    					},
    					function(response){
    						console.log(response);
    						console.log("Error");
    					}
    					
    	    	);
    		}
    		if(!$scope.componentsStorage.checkRepository(repo)){
    			$scope.componentsStorage.addRepository(repo); 
    		}
    		else{
    			$scope.componentsStorage.updateRepository(repo);
    		}
    		
    		$scope.resetForm();
            $location.path('/repos');
        }
    	
        $scope.resetForm = function(){
        	$scope.components=[];
        	$scope.name = "";
        }
        
    	
    	//-----------------------test area ---------------------
}]
);

