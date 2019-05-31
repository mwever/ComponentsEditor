class Repository{
        	constructor(name,components){
        		this.name = name;
        		this.components = components;
        	}
        }


ComponentApp.controller('RepoitoryController',['$scope','$location','$log','$http','ComponentRepositoryService',function($scope,$location,$log,$http,ComponentRepositoryService){
		
		$scope.componentsStorage = ComponentRepositoryService;
		
		
		
		$scope.goToComponentView=function(){
        	$location.path('/Comp');
        	// $scope.componentsStorage.addnewComponent();
    	}
		
		$scope.goToRepositoryView = function(){
			$scope.addRepository();
			
			// $location.path('/repos');
        	// $scope.componentsStorage.addnewComponent();
    	}
    	
    	$scope.removeComponent = function(x){
    		$scope.componentsStorage.deleteComponent(x);
    	}
    	
    	
    	$scope.editComponent = function(x){
    		$scope.componentsStorage.editComponent(x);
    		$location.path('/Comp');
    	}
    	
    	
    	$scope.addRepository=function(){
    		
        	var repo = new Repository($scope.componentsStorage.name,$scope.componentsStorage.componentArray); 
        	
    		var jsonString =  angular.toJson(repo, true);
    		
    		if($scope.componentsStorage.checkRepository(repo)){
    			console.log("POST");
    			$http({
    				method: 'POST',
    				url: '/api/repo',
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
    				url: '/api/repo',
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
    		$scope.componentsStorage.editModeRepo =false;
    		$location.path('/');
        }
    	
        $scope.resetForm = function(){
        	$scope.componentsStorage.componentArray=[];
        	$scope.componentsStorage.name = "";
        	$scope.componentsStorage.toLoadRepo= null;
        }
        
        $scope.loadCheck = function(){
        	if($scope.componentsStorage.getToLoadRepo()){
        		$scope.componentsStorage.repositoryToEdit = $scope.componentsStorage.getToLoadRepo();
        		console.log($scope.componentsStorage.toLoadRepo);
        		$scope.componentsStorage.name = $scope.componentsStorage.repositoryToEdit.name;
        		$scope.componentsStorage.componentArray= $scope.componentsStorage.repositoryToEdit.components;
        		//$scope.componentsStorage.original = angular.copy($scope.componentsStorage.toLoad);
        	}	
        }
        
        $scope.inEditMode = function(){
        	return $scope.componentsStorage.editModeRepo == false
        }
        
        $scope.inNormalMode = function(){
        	return $scope.componentsStorage.editModeRepo == true
        }
    	
        $scope.cancel = function(){
        	$scope.componentsStorage.componentArray = [];
        	//$scope.components = $scope.componentsStorage.originalRepo.components;
        	if($scope.componentsStorage.editModeRepo){
        		$scope.componentsStorage.editModeRepo = false;
        		
        		$scope.componentsStorage.updateRepository($scope.componentsStorage.original);
        		//$scope.componentsStorage.repositoryArray.push($scope.componentsStorage.originalRepo);
        		console.log("in Edit Mode and to Load benutzt "+$scope.componentsStorage.original)
        	}
        	
        	$scope.componentsStorage.componentToEdit = null;
        	$scope.componentsStorage.editMode = false;
        	$scope.componentsStorage.originalRepo = null;
        	$scope.resetForm();
        	$location.path('/');
        }
    	// -----------------------test area ---------------------
}]
);

